import java.util.List;

import javafx.scene.shape.Sphere;



public class Planet extends Sphere implements SpaceObject {

    private Vector3D position;
    private Vector3D velocity;
    private Vector3D acceleration;
    private Vector3D force;
    private double mass;
    final private double gravitationalConstant = 6.67408E-11; //m^3/kg*s^2

    public Planet(double px, double py, double pz, double vx, double vy, double vz, double mass, double radius) {
        position = new Vector3D(px, py, pz);
        velocity = new Vector3D(vx, vy, vz);
        this.mass = mass;
        setRadius(radius);
    }

    public void updateForce(List<SpaceObject> objectList) {     //force = G*M1*M2/r^2
        Vector3D forces = new Vector3D(0, 0, 0);
        for (SpaceObject object : objectList) {
            if (this != object) {
                double force = (gravitationalConstant*this.getMass()*object.getMass())/(Math.pow(object.getPosition().substractVector(this.getPosition()).getDistance(), 2));
                forces = forces.addVector(object.getPosition().substractVector(this.getPosition()).normalizeVector().multiply(force));
            }
        }
        this.setForce(forces);
    }

    public void updateAcceleration() {  //acceleration = force/mass     (a=F/m)
        this.setAcceleration(this.force.multiply(1/this.getMass()));
    }

    public void updateVelocity(double time) {      //velocity = velocity + accelration*time
        this.setVelocity(this.getVelocity().addVector(this.getAcceleration().multiply(time)));
    }

    public void updatePosition(double time) {      //position = position + velocity*time
        this.setPosition(this.getPosition().addVector(this.getVelocity().multiply(time)));
    }

    /**
     * @return the position
     */
    public Vector3D getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3D position) {
        this.position = position;
    }

    /**
     * @return the velocity
     */
    public Vector3D getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the acceleration
     */
    public Vector3D getAcceleration() {
        return acceleration;
    }

    /**
     * @param acceleration the acceleration to set
     */
    public void setAcceleration(Vector3D acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * @return the force
     */
    public Vector3D getForce() {
        return force;
    }

    /**
     * @param force the force to set
     */
    public void setForce(Vector3D force) {
        this.force = force;
    }

    /**
     * @return the mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * @param mass the mass to set
     */
    public void setMass(double mass) {
        this.mass = mass;
    }
}