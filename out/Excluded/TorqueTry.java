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
    private double angle=0;
    private double oldAngle;
    private TestBody object;
    private double thrust;
    static double titanRadius = 2575*1000;
    //distance are in meters
    private final double radius=6;

    TorqueTry(TestBody object){
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


    //reference for the power of the thrusters
   /*private float calculateTorque() {
        var distToCOM = shape.localCOM.mul( -1.0);
        return distToCOM.x * thrustDir.y - radius * thrustDir.x;
    }*/
    public void setThrust(double thrust)
    {
        this.thrust=thrust;
    }
    public Vector2D netThrust(){
        if(this.useLeftThrusters () && this.useRightThrusters ()){
            return this.leftThrustAction ().add (this.rightThrustAction ());
        }
    }

    public void useLeftThrusters (){
        oldAngle=angle;
    }
    public void useRightThrusters (){
        oldAngle=angle;
    }
    public double leftThrustAction()
    {
        double a = object.getForce().getEuclideanLength()/object.getMassInKg();
        double angle=Math.sqrt(a/radius);
        return angle*(180/Math.PI);
    }
    public double rightThrustAction()
    {
        double a = object.getForce().getEuclideanLength()/object.getMassInKg();
        double angle=Math.sqrt(a/radius);
        return angle*(180/Math.PI);   
    }
    public double mainThrust()
    {
        double a = object.getForce().getEuclideanLength()/object.getMassInKg();
        double angle=Math.sqrt(a/radius);
        return angle*(180/Math.PI); 
    }
}
