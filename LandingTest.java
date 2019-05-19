import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class LandingTest extends JComponent{
    public static final double G = 6.674E-11; // unit: m3⋅kg−1⋅s−2 (gravitational constant)
    private static double minDistance;
    private static int minDistanceTime;
    private static ArrayList<Body> bodies = new ArrayList<Body>();
    static LandingTest display;
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;
    static double probeMass = 5000;
    static double orbitalSpeed = Math.sqrt((G*probeMass)/startingDistance);
    public static void main(String[] args) {
        // Set-up
        Body titan = new Body(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius);
        bodies.add(titan);
        Body probe = new Body(new Vector2D(8000000,0), new Vector2D(0, 100), 5000, 1);
        bodies.add(probe);

        JFrame frame = new JFrame();
        display = new LandingTest();
        frame.add(display);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Vector2D deltaV = probe.getVelocity().multipliedBy(0.5);
        //testLandingDeltaV(deltaV, 1);
        //solveDeltaV(1);
        solveMultiplierAsFunctionOfDistance(1);
        //solveThrusterMagnitudeAsFunctionOfDistance(1);


    }
    public static void solveThrusterMagnitudeAsFunctionOfDistance (int delayBetweenThrusterUse) {
        Double maxMultiplier = 161*1000*1000.0; // TODO
        Double minMultiplier= 10*1000*1000.0; //TODO
        double midMultiplier = (maxMultiplier+minMultiplier)/2;
        boolean safeLanding = testLanding(1, () -> {
            double distance = bodies.get(1).getDistanceFrom(bodies.get(0));
            Double inverseDistance = 1/distance;
            return inverseDistance*midMultiplier;
        });
        while (!safeLanding) {
            minMultiplier = midMultiplier;
            Double doubleMidMultiplier = (maxMultiplier+minMultiplier)/2;
            safeLanding = testLanding(1, () -> {
                double distance = bodies.get(1).getDistanceFrom(bodies.get(0));
                Double inverseDistance = 1/distance;
                return Math.pow((inverseDistance*doubleMidMultiplier), 2);
            });
        }
    }
    public static boolean testLanding(int delayBetweenThrusterUse, Supplier<Double> thrusterForceMagnitude) {
        boolean hasLanded = false;
        Body titan = bodies.get(0);
        Body probe = new Body(new Vector2D(startingDistance,0), new Vector2D(0, 1700), 5000, 1);
        bodies.set(1, probe);
        double thrusterMagnitude = thrusterForceMagnitude.get();

        for (int i=0; i<10000; i++) {
            if (i % delayBetweenThrusterUse == 0) {
                probe.useMainThrusters(thrusterMagnitude);
                double distance = probe.getDistanceFrom(titan);
                if (distance<(titanRadius+100*1000)) {
                    probe.useSuicideBurn(distance);
                }
            }
            probe.updatePositionAndVelocity(1, titan);
            //System.out.println("Current velocity: " +probe.getVelocity().toString());
            System.out.println("Gravitational force: " + probe.computeForceMagnitude(titan));
            System.out.println("Thruster magnitude: " + probe.thrusterForceMagnitude);
            if (i % 100 == 0) {
                display.repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            double newDistance = probe.getDistanceFrom(titan);
            //System.out.println("Current distance: " + newDistance);
            //System.out.println("Current velocity: " + probe.getVelocity().toString());
            if (newDistance<=titanRadius) {
                hasLanded = true;
                System.out.println("The probe has landed.");
                System.out.println("Current distance: Probe\n" + newDistance);
                System.out.println("Current velocity: Probe\n" + probe.getVelocity().toString());
                break;
            }
        }
        if (!hasLanded) {
            System.out.println("Did not manage to land. ");
        }

        // TODO: add code to test for safe landing speed
        return hasLanded;

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

/*
    solveDeltaV is a method intended to solve for a CONSTANT multiplier for the DeltaV which would lead to landing safely.
 */
    public static void solveDeltaV (int delayBetweenThrusterUse) {
        // DeltaV is understood in this context as purely the change in velocity.
        // Want to find the constant deltaV vector which will be applied to the probe every time the thruster is used.
        // For now the presumption is that thrusters are applied constantly, i.e. with every update of position.
        // DeltaV will be defined so that it is collinear with the velocity vector.
        // For decreasing velocity, DeltaV equals -a*velocity, with 0<a<1. For increasing velocity, DeltaV equals a*velocity.
        // If this approach does not work: try approaching deltaV through its (second) derivative instead.
        // Starting bracket for DeltaV: [0, 1] (find a value between no change to velocity and stopping the spacecraft completely).
        double minDeltaVMultiplier = 0.0;
        double maxDeltaVMultiplier = 0.5;
        double bracketMidPoint = (minDeltaVMultiplier+maxDeltaVMultiplier)/2;
        Double doubleBracketMidPoint = bracketMidPoint;
        Vector2D landingVelocity = testLandingDeltaV(() -> { return doubleBracketMidPoint; }, delayBetweenThrusterUse); // Landed safely with 0.25,
        int counter = 1;
        while (Math.abs(landingVelocity.x)>7 || Math.abs(landingVelocity.y)>7) {
            counter++;
            minDeltaVMultiplier = bracketMidPoint; // Testing with max instead of min may give smaller acceptable values
            bracketMidPoint = (minDeltaVMultiplier+maxDeltaVMultiplier)/2;
            System.out.println("Attempt " + counter);
            System.out.println("DeltaVMultiplier: " + bracketMidPoint);
            landingVelocity = testLandingDeltaV(() -> { return doubleBracketMidPoint; }, delayBetweenThrusterUse);
        }
    }
    public static Vector2D testLandingDeltaV(Supplier<Double> deltaVFunction, int delayBetweenThrusterUse) {
        double landingBurnFactor = 0.15;
        double maxLandingBurnFactor = 0.5;
        boolean hasLanded = false;
        Body titan = bodies.get(0);
        Body probe = new Body(new Vector2D(startingDistance,0), new Vector2D(0, 1700), 5000, 1);
        bodies.set(1, probe);
        Vector2D probeStartPosition = probe.getPosition();
        Vector2D titanPosition = titan.getPosition();
        Vector2D deltaV = probe.getVelocity().multipliedBy(-deltaVFunction.get());

        // Run upates and visualise with delay of 50 ms between updates
        for (int i=0; i<30000; i++) {
            if (i%delayBetweenThrusterUse==0) {
                double currentDistance = probe.getDistanceFrom(titan);
                if (currentDistance<titanRadius+10*1000) {
                    deltaV = probe.getVelocity().multipliedBy(-landingBurnFactor);
                    landingBurnFactor = (landingBurnFactor+maxLandingBurnFactor)/2;
                    probe.changeVelocityWithMainThrusters(deltaV);
                } else {
                    probe.changeVelocityWithMainThrusters(deltaV);
                    deltaV = probe.getVelocity().multipliedBy(-deltaVFunction.get());
                }

            }
            probe.updatePositionAndVelocity(1, titan);
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
            //System.out.println("Current velocity: " + probe.getVelocity().toString());
            if (newDistance<=titanRadius) {
                hasLanded = true;
                System.out.println("The probe has landed.");
                System.out.println("Current distance: Probe\n" + newDistance);
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

        for (Body body: bodies) {
            Vector2D position = body.getPosition();
            int scale = 16000;
            int size = Math.max(10, (int)body.getDiameter() / scale);
            int x = (int) position.x/ scale + 400 - size/2;
            int y = (int) position.y/ scale + 400 - size/2;
            g2.fillOval(x, y, size, size);
        }

    }
    class DisplayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
