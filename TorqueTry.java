import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TorqueTry{
    private double force;
    private double angularVelocity;
    private double angularAcceleration;
    private double position;
    private double angle;
    private SimulationBody object;
    //distance are in meters
    private final double radius =6;

    TorqueTry(SimulationBody object){
        this.object=object;
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


    //reference for the power of the thruster
    public void leftThrust()
    {
        oldAngle=angle;
        angle += Math.PI / 180;

        //rotation around the barycenter
        spaceCraft.transform(new Affine(new Rotate(Spacecraft.leftThrust, x, y)));

    }
    public void useLeftThrusters (){
        oldAngle=angle;
        angle += Math.PI / 180;
        spaceCraft.transform(new Affine(new Rotate(object.leftThrust, x, y)));
    }
    public void useRightThrusters (){
        oldAngle=angle;
        angle -= Math.PI / 180;
        object.transform(new Affine(new Rotate(TorqueTry(object).rightThrust, x, y)));
    }
    public void leftThrustAction()
    {
        double a = object.getForce ().getEuclideanLength ()/object.getMassInKg ();
        double vel=a;
        double radius = vel;
        double dX = Math.sin(-(angle + (Math.PI/2)))*radius;
        double dY = Math.cos(angle + Math.PI/2)*radius;
        velX += dX;
        velY += dY;
        delaV=velX + velY;
    }
    public void rightThrustAction()
    {
        double a = object.getForce ().getEuclideanLength ()/object.getMassInKg ();
        double vel=a;
        double radius = vel;
        double dX = Math.sin((angle + (Math.PI/2)))*radius;
        double dY = Math.cos(angle + Math.PI/2)*radius;
        velX += dX;
        velY += dY;
    }

    public void mainThrust()
    {
        double a = object.getForce ().getEuclideanLength ()/object.getMassInKg ();
        double vel=a;
        double radius = vel;
        double dX = Math.sin(-angle)*radius;
        double dY = Math.cos(angle)*radius;
        velX += dX;
        velY += dY;
    }
}
