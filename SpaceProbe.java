/**
 * This class represents a rocket in particular and i a part of the Spaceobject class
 */
public class SpaceProbe extends SpaceObject {
    private double minDist = Double.MAX_VALUE;
    private final double MAX_SPEED = 365*10E6;

    private Vector3D minPos;
    private Vector3D goalMinPos;

    public SpaceProbe(double px, double py, double pz, double vx, double vy, double vz, double mass) {
        super(px, py, pz, vx, vy, vz, mass);
        super.attractive = false;
    }

    public SpaceProbe(Vector3D pos, Vector3D vel, double mass) {
        super(pos, vel, mass);
        super.attractive = false;
    }

    /**
     * This method calculates the minimum distance towards a Spaceobject used to get the shortest distance within a trip
     * @param goal represents the Spaceobject towards which the minimum distance is calculated
     * @return returns the minimum distance towards the goal
     */
    public double calcMinDist(SpaceObject goal) {
        double d = (goal.getPosition().substractVector(this.getPosition())).getLength();
        if (d < minDist) {
            minDist = d;
            minPos = this.getPosition().clone();
            goalMinPos = goal.getPosition().clone();
        }
        return getMinDist();
    }

    public double getMinDist() {
        return minDist;
    }

    public void setMinDist(double minDist) {
        this.minDist = minDist;
    }

    @Override
    public SpaceProbe clone() {
        SpaceProbe clone = new SpaceProbe(this.getPosition().clone(),this.getVelocity().clone(), this.getMass());
        clone.setMinDist(this.getMinDist());
        return clone;
    }

    /**
     * This method modifies the clone of this object
     * @param x modification parameter to the speed of the spaceprobe
     * @param y modification parameter to the speed of the spaceprobe
     * @param z modification parameter to the speed of the spaceprobe
     * @return returns a deep copy of the object but with a modification to the speed
     */
    public SpaceProbe cloneChange(double x, double y, double z) {
        SpaceProbe tmp = null;
        tmp = this.clone();
        tmp.setVelocity(new Vector3D(this.getVelocity().clone().getX() + x,
                this.getVelocity().clone().getY() + y, this.getVelocity().clone().getZ() + z));
        return tmp;
    }

    public String toString() {
        return "Probe : inVel : " + this.getStartVel() + " speed : " + this.getStartVel().getLength() + " min dist : " + minDist;
    }

    public void reset() {
        this.minDist = Double.MAX_VALUE;
        super.reset();
    }

/*
    @Override
    /**
     * this method blocks the speed from going over a certain speed cap

    public void setVelocity(Vector3D velocity){
        if (velocity.getLength() < MAX_SPEED){
            super.setVelocity(velocity);
        }
    }*/

}