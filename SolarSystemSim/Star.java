import java.util.List;
import javafx.scene.shape.Sphere;

public class Star extends Sphere implements SpaceObject {
    
    Vector position;
    Vector velocity;
    Vector acceleration;
    Vector force;
    double mass;
    final private double gravitationalConstant = 6.6740831E-11; //m^3/kg*s^2

    public Star(double px, double py, double pz, double vx, double vy, double vz, double mass, double radius) {
        position = new Vector(px, py, pz);
        velocity = new Vector(vx, vy, vz);
        this.mass= mass;
        setRadius(radius);
    }
    
    public void updateForce(List<SpaceObject> objectList) {     //force = G*M1*M2/r^2
      
    }

    public void updateAcceleration() {  //acceleration = force/mass     (a=F/m)
        
    }

    public void updateVelocity(double time) {      //velocity = velocity + accelration*time
        
    }

    public void updatePosition(double time) {      //position = position + velocity*time
        
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
