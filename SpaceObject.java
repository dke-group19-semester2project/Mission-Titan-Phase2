import java.util.ArrayList;

public class SpaceObject {

    private Vector3D position;
    private Vector3D velocity;
    private Vector3D acceleration;
    private Vector3D force;
    private double mass;
    final private double G = 6.67408E-11; //  m^3/(kg*s^2)

    public SpaceObject(double px, double py, double pz, double vx, double vy, double vz, double mass) {
        this.position = new Vector3D(px, py, pz);
        this.velocity = new Vector3D(vx, vy, vz);
        this.mass = mass;
    }

    public void updateForces(ArrayList<SpaceObject> objectArray) {
        Vector3D forces = new Vector3D();
        for (SpaceObject spaceObject : objectArray) {
            if (this != spaceObject) {
                double force = (G*this.getMass()*spaceObject.getMass())/(Math.pow(spaceObject.getPosition().substractVector(this.getPosition()).getLength(), 2));
                forces = forces.addVector(spaceObject.getPosition().substractVector(this.getPosition()).normalizeVector().multiplyConstant(force));
            }
        }
        this.setForces(forces);
    }

    public void updateAcceleration() {  //acceleration = force/mass     (a=F/m)
        this.setAcceleration(this.force.divideConstant(this.getMass()));
    }

    public void updateVelocity(double time) {      //velocity = velocity + accelration*time
        this.setVelocity(this.getVelocity().addVector(this.getAcceleration().multiplyConstant(time)));
    }

    public void updatePosition(double time) {      //position = position + velocity*time
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

    /**
     * @param mass the mass to set
     */
    public void setMass(double mass) {
        this.mass = mass;
    }
}
