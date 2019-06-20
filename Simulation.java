import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulation {
    
    private double totalForce = 0;

    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    class Display extends JComponent {
        Vector2D predictedLandingSpot = new Vector2D(-2557051, -303503);
        public void paintComponent (Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int scale = 16000;
            for (SimulationBody body: bodies) {
                Vector2D position = body.getPosition();
                int size = Math.max(10, (int)body.getDiameter() / scale);
                int x = (int) position.x/ scale + 400 - size/2;
                int y = (int) position.y/ scale + 400 - size/2;
                g2.fillOval(x, y, size, size);
            }
            int predictedX = (int) predictedLandingSpot.x/scale + 396;
            int predictedY = (int) predictedLandingSpot.y/scale + 406;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(20));
            char[] c = {'X'};
            Font font = new Font("Courier", Font.BOLD,20);
            g2.setFont(font);
            g2.drawChars(c, 0, 1, predictedX, predictedY);
            //g2.fillOval(predictedX, predictedY, 8, 8);
            Vector2D windVelocity = realProbe.getWindSpeed().getCurrentWindVelocity();
            double windSpeed = Math.sqrt(windVelocity.x*windVelocity.x + windVelocity.y*windVelocity.y);
            String windString = "Current wind speed: \n" + (int) windSpeed + " m/s.";
            //String windString2 = "Wind direction: ";
            Vector2D currentVelocity = realProbe.getVelocity();
            double currentSpeed = Math.sqrt(currentVelocity.x*currentVelocity.x + currentVelocity.y*currentVelocity.y);
            String speedString = "Current speed of the lander: " + (int) currentSpeed + " m/s.";
            Vector2D currentPosition = realProbe.getPosition();
            double currentX = currentPosition.x;
            double currentY = currentPosition.y;
            String positionString = "Current position: X = " + (int) currentX + ", Y = " + (int) currentY;
            String landedString = "The probe has landed!";

            g2.setColor(Color.BLACK);

            g2.drawString(windString, 800, 100);
            g2.drawString(speedString, 800, 150);
            g2.drawString(positionString, 800, 200);
            if (hasLanded) {
                g2.drawString(landedString, 800, 300);
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
    SimulationBody realProbe;
    boolean hasLanded = false;
    public Simulation (ControllerInterface controller, SimulationBody titan, SimulationBody probe) {
        this.controller = controller;
        this.titan = titan;
        bodies.add(titan);
        this.realProbe = probe;
        bodies.add(probe);
    }
    public void run () {

        // GUI
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Vector2D windVelocity = realProbe.getWindSpeed().getCurrentWindVelocity();


        frame.add(display  );
        frame.setSize(1400, 1000);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


        //boolean hasLanded = false;
        int printCounter = 0;
        for (int i=0; i<60000; i++) {
            printCounter++;
            Vector2D deltaV = controller.updateAndGetDeltaV(this);
            realProbe.changeVelocityWithMainThrusters(deltaV);
            int currentForceMagnitude = (int) controller.convertDeltaVToForceMagnitude(deltaV);
            //if (printCounter%200==0) {
                //System.out.println(/*"Current thruster force magnitude: \n" + */ currentForceMagnitude);
            //}

//            double deltaVMagnitude = Math.sqrt(deltaV.x*deltaV.x + deltaV.y+deltaV.y);
//            System.out.println("Current deltaVMagnitude: \n" + deltaVMagnitude);
            realProbe.updatePositionAndVelocity(1, titan);
            totalForce = totalForce + controller.getForceUsed();
            windVelocity = realProbe.getWindSpeed().getCurrentWindVelocity();
            //System.out.println("Current wind velocity: " + windVelocity.toString());
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
                System.out.println("Total force produced : " + totalForce);
                System.out.println("Current distance: \n" + newDistance);
                System.out.println("Current position: \n" + realProbe.getPosition().toString());
                Vector2D currentVelocity = realProbe.getVelocity();
                System.out.println("Current velocity: \n" + currentVelocity.toString());
                double currentSpeed = Math.sqrt(currentVelocity.x*currentVelocity.x + currentVelocity.y*currentVelocity.y);
                //System.out.println("Current speed: \n" + currentSpeed);
                if (Math.abs(currentVelocity.x)<=7 && Math.abs(currentVelocity.y)<=7) {
                    //System.out.println("Safe landing!");
                }
                break;
            }
        }
        if (!hasLanded) {
            System.out.println("Did not manage to land within 30 s.");
        }

    }
    public SimulationBody getRealProbe () {
        return realProbe;
    }

}

