import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    class Display extends JComponent {
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
            // TODO: Add a red X to the predicted landing location
            // TODO: Add a label displaying the wind velocity as an arrow and as x & y coordinates
            Vector2D windVelocity = realProbe.getWindSpeed().getCurrentWindVelocity();
            JLabel windLabel = new JLabel(windVelocity.toString());

        }
    }
    // updates the "real" position and velocity of the spacecraft on the basis of: gravity, wind velocity, and deltaV from the Controller
    // looks very similar to the TestInternalSimulation, with methods for getting the gravitational force, updating current velocity & position etc
    // display the frame in the main method
    Display display = new Display();
    static double titanRadius = 2575*1000;
    ControllerInterface controller;
    SimulationBody titan;
    SimulationBody realProbe;
    public Simulation (ControllerInterface controller, SimulationBody titan, SimulationBody probe) {
        this.controller = controller;
        this.titan = titan;
        bodies.add(titan);
        this.realProbe = probe;
        bodies.add(probe);
    }
    public void run () throws FileNotFoundException {

        // GUI
        JFrame frame = new JFrame();
        frame.add(display);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // TODO: Finish the for-loop below so that it does not mess with the deltaV calculations and is able to show a couple of rounds in orbit.
//
//        for (int i=0; i<60000; i++) {
//            realProbe.updatePositionAndVelocity(1, titan);
//            if(i % 100 == 0) {
//                display.repaint();
//                try {
//                    TimeUnit.MILLISECONDS.sleep(20);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        String csvInternal = "internalVelocities.csv";
        String csvExternal = "externalVelocities.csv";
        StringBuilder internalData = new StringBuilder();
        StringBuilder externalData = new StringBuilder();
        PrintWriter internalVelocitiesToCSV = new PrintWriter(new File(csvInternal));
        PrintWriter externalVelocitiesToCSV = new PrintWriter(new File(csvExternal));
        final String QUOTATION = "\"";
        final String COMMA_SEPARATOR = ",";
        final String NEW_LINE = "\n";
        //SimulationBody internalProbe = O

        boolean hasLanded = false;
        for (int i=0; i<60000; i++) {
            externalData.append(QUOTATION + "Current position:" + realProbe.getPosition().toString() + QUOTATION + COMMA_SEPARATOR + NEW_LINE);
            Vector2D deltaV = controller.updateAndGetDeltaV();
            realProbe.changeVelocityWithMainThrusters(deltaV);
            //System.out.println("Current deltaV: " + deltaV);
            realProbe.updatePositionAndVelocity(1, titan);
            Vector2D windVelocity = realProbe.getWindSpeed().getCurrentWindVelocity();
            System.out.println("Current wind velocity: " + windVelocity.toString());
            if(i % 100 == 0) {
                display.repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            double newDistance = realProbe.getDistanceFrom(titan);
            //System.out.println("Current distance: " + newDistance);
            //System.out.println("Current velocity: " + realProbe.getVelocity().toString());
            if (newDistance<=titanRadius) {
                hasLanded = true;
                System.out.println("The realProbe has landed.");
                System.out.println("Current distance: Probe\n" + newDistance);
                System.out.println("Current position: Probe\n" + realProbe.getPosition().toString());
                System.out.println("Current velocity: Probe\n" + realProbe.getVelocity().toString());
                break;
            }
        }

    }
    public SimulationBody getRealProbe () {
        return realProbe;
    }

}

