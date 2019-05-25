import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TorqueTry extends JComponent {
    private double force;
    private double angularVelocity;
    private double angularAcceleration;
    private double position;
    private double angle;
    //distance are in meters
    private final double radius =6;
    public static void main(String[]args){
        JFrame frame = new JFrame ();
        TorqueTry movingSpaceCraft = new TorqueTry ();
        class CalculateListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                double rate = Double.parseDouble(rateField.getText());
                account.deposit((account.getBalance()*rate/100));
                textArea.append(account.getBalance() + "\n");
        }
        }
        ActionListener listener = new CalculateListener();
        frame.add(movingSpaceCraft);
        frame.setSize ( 800,800 );
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void paintComponent (Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        // Construct a rectangle and draw it
        Rectangle box = new Rectangle(5, 10, 20, 30);
        box.(395, 395);
        g2.draw(box);

    }
    /*
    public double updatedAngle (double force,double angle){
        if (thrusters==true){
            angularAcceleration=
            angularVelocity=angularVelocity + angularAcceleration*t;
            System.out.println ( angularVelocity );
        }
        else {
            angularAcceleration = 0;
        }
        SpaceCraft.transform(new Affine (new Rotate (s.planetaryObjects.get(22).leftThrust, xModule, yModule)));

        //using acceleration and position angle
        //updating velocity and position
        this.angle = angle + (getTorque (force)/(force*spaceCraft.distance));
    }
    public double getTorque (double force){
        double torque;
        return torque = force*radius*Math.sin ( angle );
    }*/


    //reference for the power of the thrusters
    public void leftThrust()
    {
        oldAngle=angle;
        angle += Math.PI / 180;

        //rotation around the barycenter
        spaceCraft.transform(new Affine(new Rotate(Spacecraft.leftThrust, x, y)));

    }
    public void useLeftThrusters (double force){
        oldAngle=angle;
        angle += Math.PI / 180;
        spaceCraft.transform(new Affine(new Rotate(Spacecraft.leftThrust, x, y)));
    }
    public void useLeftThrusters (double force){
        oldAngle=angle;
        angle -= Math.PI / 180;
        spaceCraft.transform(new Affine(new Rotate(Spacecraft.rightThrust, x, y)));
    }
    public void leftThrustAction()
    {
        double a = //value of the engine
        double vel=a* //a timestep;
        double distance = vel*// a timestep;
        double dX = Math.sin(-(angle + (Math.PI/2)))*distance;
        double dY = Math.cos(angle + Math.PI/2)*distance;
        velX += dX/ //a timestep;
        velY += dY/ //a timestep;
    }
    public void rightThrustAction()
    {
        double a = //value of the engine
        double vel=a* //a timestep;
        double distance = vel*// a timestep;
        double dX = Math.sin((angle + (Math.PI/2)))*distance;
        double dY = Math.cos(angle + Math.PI/2)*distance;
        velX += dX/ //a timestep;
        velY += dY/ //a timestep;
    }

    public void mainThrust()
    {
        double a = //value of the engine
        double vel=a* //a timestep;
        double distance = vel* //a timestep;
        double dX = Math.sin(-angle)*distance;
        double dY = Math.cos(angle)*distance;
        velX += dX/ //a timestep;
        velY += dY/ //a timestep;
    }
}
