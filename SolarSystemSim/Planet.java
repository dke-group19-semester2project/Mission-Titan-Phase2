import java.util.List;
import javafx.scene.shape.Sphere;



public class Planet extends Sphere implements SpaceObject {

    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Vector force;
    private double mass;
    final private double gravitationalConstant = 6.67408E-11; //m^3/kg*s^2

    public Planet(double px, double py, double pz, double vx, double vy, double vz, double mass, double radius) {
        position = new Vector(px, py, pz);
        velocity = new Vector(vx, vy, vz);
        this.mass = mass;
        setRadius(radius);
    }

    public void updateForce(List<SpaceObject> objectList) {     //force = G*M1*M2/r^2
        Vector forces = new Vector(0, 0, 0);
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
    public Vector getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * @return the velocity
     */
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the acceleration
     */
    public Vector getAcceleration() {
        return acceleration;
    }

    /**
     * @param acceleration the acceleration to set
     */
    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * @return the force
     */
    public Vector getForce() {
        return force;
    }

    /**
     * @param force the force to set
     */
    public void setForce(Vector force) {
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