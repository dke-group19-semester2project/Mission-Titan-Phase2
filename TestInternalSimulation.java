import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class TestInternalSimulation extends JComponent{
    //public static final double G = 6.674E-11; // unit: m3⋅kg−1⋅s−2 (gravitational constant)
    private static double minDistance;
    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    static TestInternalSimulation display;
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;
    static double probeMass = 5000;
    //static double orbitalSpeed = Math.sqrt((G*probeMass)/(startingDistance-titanRadius));
    public static void main(String[] args) {
        // Set-up
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        bodies.add(titan);
//        SimulationBody internalProbe = new SimulationBody(new Vector2D(8000000,0), new Vector2D(0, 100), 5000, 12, new WindSpeed(1));
//        bodies.add(internalProbe);

        JFrame frame = new JFrame();
        display = new TestInternalSimulation();
        frame.add(display);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Vector2D deltaV = internalProbe.getVelocity().multipliedBy(0.5);
        //testLandingDeltaV(deltaV, 1);
        //solveDeltaV(1);
        solveMultiplierAsFunctionOfDistance(1); /* This currently works as a combination of the two velocity methods. */
        //solveThrusterMagnitudeAsFunctionOfDistance(1);


    }

    public static void solveMultiplierAsFunctionOfDistance (int delayBetweenThrusterUse) {
        double maxMultiplier = 30*1000;
        double minMultiplier = 1;
        double midMultiplier = (maxMultiplier+minMultiplier)/2;
//        Vector2D landingVelocity = testLandingDeltaV(() -> {
//            double distance = bodies.get(1).getDistanceFrom(bodies.get(0));
//            Double inverseDistance = 1/distance;
//            return inverseDistance;
//        }, delayBetweenThrusterUse);
        int counter = 1;
        Vector2D landingVelocity = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        while (Math.abs(landingVelocity.x)>7 || Math.abs(landingVelocity.y)>7) {
            counter++;
            minMultiplier = midMultiplier;
            midMultiplier = (maxMultiplier+minMultiplier)/2;
            Double doubleMidMultiplier = 10. * 1000; //midMultiplier;
            System.out.println("Attempt " + counter);
            System.out.println("ConstantMultiplier: " + midMultiplier);
            landingVelocity = testLandingDeltaV(() -> {
                double distance = bodies.get(1).getDistanceFrom(bodies.get(0));
                Double inverseDistance = 1/distance;
                Double deltaVMultiplier = Math.pow(doubleMidMultiplier*inverseDistance,2);
                //System.out.println("DeltaVMultiplier: " + deltaVMultiplier);
                return deltaVMultiplier;
            }, delayBetweenThrusterUse);
        }
    }

    public static Vector2D testLandingDeltaV(Supplier<Double> deltaVFunction, int delayBetweenThrusterUse) {
        double landingBurnFactor = 0.001;
        double maxLandingBurnFactor = 0.5;
        boolean hasLanded = false;
        SimulationBody titan = bodies.get(0);
        SimulationBody probe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
        bodies.add(probe);
        //System.out.println("Starting position: " + internalProbe.getPosition().toString());
        //System.out.println("Starting velocity: " + internalProbe.getVelocity().toString());
        Vector2D probeStartPosition = probe.getPosition();
        Vector2D titanPosition = titan.getPosition();
        Vector2D deltaV = probe.getVelocity().multipliedBy(-deltaVFunction.get());

        // Run upates and visualise with delay of 50 ms between updates
        for (int i=0; i<30000; i++) {
            if (i%delayBetweenThrusterUse==0) {
                double currentDistance = probe.getDistanceFrom(titan);
                if (currentDistance<titanRadius+10*1000) {
                    //System.out.println("Current velocity before landing burn: " + internalProbe.getVelocity().toString());
                    deltaV = probe.getVelocity().multipliedBy(-landingBurnFactor);
                    landingBurnFactor = (landingBurnFactor+maxLandingBurnFactor)/2;
                    //System.out.println("LandingBurnFactor: " + landingBurnFactor);
                    probe.changeVelocityWithMainThrusters(deltaV);
                } else {
                    probe.changeVelocityWithMainThrusters(deltaV);
                    deltaV = probe.getVelocity().multipliedBy(-deltaVFunction.get());
                }

            }
            probe.updatePositionAndVelocity(1, titan);
            System.out.println("Current position: " + probe.getPosition().toString());
            System.out.println("Current velocity: " + probe.getVelocity().toString());
            if(i % 100 == 0) {
                display.repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            double newDistance = probe.getDistanceFrom(titan);
            //System.out.println("Current distance: " + newDistance);
            //System.out.println("Current velocity: " + internalProbe.getVelocity().toString());
            if (newDistance<=titanRadius) {
                hasLanded = true;
                System.out.println("The internalProbe has landed.");
               // System.out.println("Current distance: Probe\n" + newDistance);
                //System.out.println("Current position: Probe\n" + internalProbe.getPosition().toString());
                System.out.println("Current velocity: Probe\n" + probe.getVelocity().toString());
                break;
            }
        }
        if (!hasLanded) {
            System.out.println("Did not manage to land. ");
        }

        //System.exit(1);
        return probe.getVelocity();
    }
    @Override
    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        for (SimulationBody body: bodies) {
            Vector2D position = body.getPosition();
            int scale = 16000;
            int size = Math.max(10, (int)body.getDiameter() / scale);
            int x = (int) position.x/ scale + 400 - size/2;
            int y = (int) position.y/ scale + 400 - size/2;
            g2.fillOval(x, y, size, size);
        }

    }

}
