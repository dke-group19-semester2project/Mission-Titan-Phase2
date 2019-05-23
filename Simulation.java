import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        }
    }
    // updates the "real" position and velocity of the spacecraft on the basis of: gravity, wind velocity, and deltaV from the Controller
    // looks very similar to the TestInternalSimulation, with methods for getting the gravitational force, updating current velocity & position etc
    // display the frame in the main method
    Display display = new Display();
    static double titanRadius = 2575*1000;
    ControllerInterface controller;
    SimulationBody titan;
    SimulationBody probe;
    public Simulation (ControllerInterface controller, SimulationBody titan, SimulationBody probe) {
        this.controller = controller;
        this.titan = titan;
        bodies.add(titan);
        this.probe = probe;
        bodies.add(probe);
    }
    public void run () {

        // GUI
        JFrame frame = new JFrame();
        frame.add(display);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Test 'external' simulation (which for now is actually the internal one)
        // TODO: Finish the for-loop below so that it does not mess with the deltaV calculations and is able to show a couple of rounds in orbit.
//
//        for (int i=0; i<60000; i++) {
//            probe.updatePositionAndVelocity(1, titan);
//            if(i % 100 == 0) {
//                display.repaint();
//                try {
//                    TimeUnit.MILLISECONDS.sleep(20);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        boolean hasLanded = false;
        for (int i=0; i<60000; i++) {
            Vector2D deltaV = controller.getDeltaV();
            probe.changeVelocityWithMainThrusters(deltaV);
            //System.out.println("Current deltaV: " + deltaV);
            probe.updatePositionAndVelocity(1, titan);
            if(i % 100 == 0) {
                display.repaint();
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
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
                System.out.println("Current position: Probe\n" + probe.getPosition().toString());
                System.out.println("Current velocity: Probe\n" + probe.getVelocity().toString());
                break;
            }
        }
    }
}

