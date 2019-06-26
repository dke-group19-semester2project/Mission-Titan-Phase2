/**
 * This class is used to simulate Titan and the spacecraft orbiting Titan (in isolation from the rest of the solar system).
 * Titan is presumed as fixed at the origin (zero-velocity), while the spacecraft is orbiting with an initial velocity.
 * The current model only models the acceleration and NOT the attitude of the internalProbe (using torque).
 */

public class SimulationBody{
    public static final double G = 6.674E-11; // unit: m3⋅kg−1⋅s−2 (gravitational constant)
    private Vector2D position; // Cartesian coordinates as m values.
    private Vector2D velocity; // Cartesian coordinates as m/s.
    double massInKg;
    double diameter; // In meters.
    private Vector2D thrust = new Vector2D(0,0);
    public Vector2D currentDrag = new Vector2D(0, 0);
    private Vector2D netForce;
    public int simulationTime = 0;
    public WindSpeedInterface windSpeed;

    public SimulationBody(Vector2D initialPosition, Vector2D initialVelocity, double mass, double diameter, WindSpeedInterface windSpeed) {
        position = initialPosition;
        velocity = initialVelocity;
        massInKg = mass;
        this.diameter = diameter;
        this.windSpeed = windSpeed;
    }
    public double getDiameter () {
        return diameter;
    }
    public Vector2D getPosition () {
        return position;
    }
    public Vector2D getVelocity () {
        return velocity;
    }
    public double getSpeed () {
        double speed = Math.sqrt(Math.pow(velocity.x, 2)+Math.pow(velocity.y, 2));
        return speed;
    }
    /*
        The objective of method changeMainThrust is to update velocity (without taking another time step).
        For now the spacecraft is presumed to be correctly oriented to either increase or reduce the speed.
     */
    public void changeMainThrust(Vector2D newThrust) {
        //velocity = sumOf(velocity, deltaV);
        thrust = newThrust;
    }
    public void updatePositionAndVelocity (int time, SimulationBody attractingBody) {
        simulationTime = simulationTime+time;
        Vector2D acceleration = getAcceleration(attractingBody);
        Vector2D changeInVelocity = getChangeInVelocity(acceleration, time);
        Vector2D updatedVelocity = sumOf(this.getVelocity(), changeInVelocity);
        this.velocity = updatedVelocity;
        Vector2D changeInPosition = getChangeInPosition(updatedVelocity, time);
        Vector2D updatedPosition = sumOf(this.getPosition(), changeInPosition);
        this.position = updatedPosition;
    }
    public Vector2D getChangeInVelocity (Vector2D acceleration, int time) {
        return acceleration.multipliedBy(time);
    }
    public Vector2D getChangeInPosition (Vector2D velocity, int time) {
        return velocity.multipliedBy(time);
    }
    
    public Vector2D getAcceleration (SimulationBody attractingBody) {
        // F=ma => a=F/m
        Vector2D gravitationalForce = getForceAsVector(attractingBody);
        //Vector2D drag = new Vector2D(0,0);//
        Vector2D drag = windSpeed.updateModelAndGetDrag(position,velocity);
        currentDrag = drag;
        if (simulationTime>=5860) {
            //System.out.println("Wind velocity: " + windSpeed.getCurrentWindVelocity().toString());
            System.out.println("Wind force: " + drag.toString());
        }
        Vector2D gravityPlusDrag = sumOf(gravitationalForce, drag);
        if (simulationTime>=5850) {
            System.out.println("Net force: " + gravityPlusDrag.toString());
        }
        System.out.println("Thrust = " + thrust.toString());
        netForce = sumOf(gravityPlusDrag, thrust);
        Vector2D acceleration = netForce.dividedBy(this.massInKg); // Force is divided by the mass of the accelerating body
        return acceleration;
    }
    public Vector2D getForceAsVector (SimulationBody attractingBody) {
        double magnitude = computeForceMagnitude(attractingBody);
        Vector2D direction = computeForceDirection(attractingBody);
        Vector2D forceVector = direction.multipliedBy(magnitude);
        return forceVector;
    }
    /*
    Magnitude of the force as a scalar value
     */
    public double computeForceMagnitude (SimulationBody attractingBody) {
        double distance = getDistanceFrom(attractingBody);
        double forceMagnitude = -(G*this.massInKg*attractingBody.massInKg)/Math.pow(distance,2);
        return forceMagnitude;
    }
    public double getDistanceFrom (SimulationBody attractingBody) {
        Vector2D distanceVector = differenceOf(this.getPosition(), attractingBody.getPosition());
        double distance = distanceVector.getEuclideanLength();
        return distance;
    }
    /*
    Direction of the force as a unit vector
     */
    public Vector2D computeForceDirection (SimulationBody attractingBody) {
        Vector2D distanceVector = differenceOf(this.getPosition(), attractingBody.getPosition());
        double distance = distanceVector.getEuclideanLength();
        Vector2D unitVector = distanceVector.dividedBy(distance);
        return unitVector;
    }
    public Vector2D sumOf(Vector2D v, Vector2D u) {
        double newX = v.x + u.x;
        double newY = v.y + u.y;
        Vector2D sum = new Vector2D(newX, newY);
        return sum;
    }
    public Vector2D differenceOf(Vector2D v, Vector2D u) {
        double newX = v.x-u.x;
        double newY = v.y-u.y;
        Vector2D difference = new Vector2D(newX, newY);
        return difference;
    }
//     public Vector2D getAccelerationValue (){
//        return acceleration;
//    }
//
//    public Vector2D getForce() {
//        return forceVector;
//    }

    public double getMassInKg() {
        return massInKg;
    }
    public WindSpeedInterface getWindSpeed () {
        return windSpeed;
    }
}
