import java.util.ArrayList;

/**
 * SpaceObject class represents an object in space like a star, planet, moon, rocket etc
 */
public class SpaceObject {

    private Vector3D position;
    private Vector3D velocity;
    private Vector3D acceleration;
    private Vector3D force;
    private double mass;
    final private double G = 6.67408E-11; // m^3/(kg*s^2)
    private double radius;
    public boolean attractive = true;

    public Vector3D getStartVel() {
        return startVel;
    }

    public Vector3D getStartPos() {
        return startPos;
    }

    private Vector3D startVel;
    private Vector3D startPos;

    public SpaceObject(double px, double py, double pz, double vx, double vy, double vz, double mass) {
        this.position = new Vector3D(px, py, pz);
        this.startPos = this.position.clone();
        this.velocity = new Vector3D(vx, vy, vz);
        this.startVel = this.velocity.clone();
        this.mass = mass;
    }

    public SpaceObject(double px, double py, double pz, double vx, double vy, double vz, double mass, double radius) {
        this.position = new Vector3D(px, py, pz);
        this.startPos = this.position.clone();
        this.velocity = new Vector3D(vx, vy, vz);
        this.startVel = this.velocity.clone();
        this.mass = mass;
        this.radius = radius;
    }

    public SpaceObject(Vector3D pos, Vector3D vel, double mass) {
        this.position = pos;
        this.startPos = this.position.clone();
        this.velocity = vel;
        this.startVel = this.velocity.clone();
        this.mass = mass;
    }

    /**
     * This method is used for computing the forces from the different objects in the solarSystem onto each other effecting their position
     * @param objectArray represents all the objects in the solarsystem
     */
    public void updateForces(ArrayList<SpaceObject> objectArray) {
        Vector3D forces = new Vector3D();
        for (SpaceObject spaceObject : objectArray) {
            if (this != spaceObject && spaceObject.attractive) {
                double force = (G * this.getMass() * spaceObject.getMass())
                        / (Math.pow(spaceObject.getPosition().substractVector(this.getPosition()).getLength(), 2));
                forces = forces.addVector(spaceObject.getPosition().substractVector(this.getPosition())
                        .normalizeVector().multiplyConstant(force));
            }
        }
        this.setForces(forces);
    }

    /**
     * This method updates the acceleration of the Spaceobject
     */
    public void updateAcceleration() { // acceleration = force/mass (a=F/m)
        this.setAcceleration(this.force.divideConstant(this.getMass()));
    }

    /**
     * This method updates the velocity of the Spaceobject
     * @param time timestamp used to update the velocity
     */
    public void updateVelocity(double time) { // velocity = velocity + acceleration*time
        this.setVelocity(this.getVelocity().addVector(this.getAcceleration().multiplyConstant(time)));
    }

    /**
     * This method updates the position of the Spaceobject
     * @param time timestamp used to update the velocity
     */
    public void updatePosition(double time) { // position = position + velocity*time
        this.setPosition(this.getPosition().addVector(this.getVelocity().multiplyConstant(time)));
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
     * @return the forces
     */
    public Vector3D getForces() {
        return force;
    }

    /**
     * @param forces the forces to set
     */
    public void setForces(Vector3D forces) {
        this.force = forces;
    }

    /**
     * @return the mass
     */
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Clones this Spaceobject
     * @return returns a deep copy of this Spaceobject
     */
    public SpaceObject clone() {
        return new SpaceObject(position.clone(), velocity.clone(), mass);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * resets this Spaceobject to the starting position
     */
    public void reset() {
        this.position = startPos.clone();
        this.velocity = startVel.clone();
        this.acceleration = new Vector3D();
    }
}
