/**
 * This class is used to simulate Titan and the spacecraft orbiting Titan (in isolation from the rest of the solar system).
 * Titan is presumed as fixed at the origin (zero-velocity), while the spacecraft is orbiting with an initial velocity.
 * The trajectory can be calculated with an open-loop controller that takes the force of the thrusters as a parameter.
 * (For now the thrusters are only used to slow down the orbital velocity but later can be updated to ensure safe landing.)
 * The current model only models the acceleration and NOT the attitude of the probe (using torque).
 */

public class Body {
    public static final double G = 6.674E-11; // unit: m3⋅kg−1⋅s−2 (gravitational constant)
    private Vector2D position; // Cartesian coordinates as m values.
    private Vector2D velocity; // Cartesian coordinates as m/s.
    public double suicideBurn = 10*1000*1000;
    public double thrusterForceMagnitude;
    double massInKg;
    double diameter; // In meters.
    double probeDragCoefficient = 0.42; // TODO: This value is only for testing - correct value still needs to be researched.
    public static int simulationTime = 0;
    WindSpeed windSpeed = new WindSpeed(1);

    public Body (Vector2D initialPosition, Vector2D initialVelocity, double mass, double diameter) {
        position = initialPosition;
        velocity = initialVelocity;
        massInKg = mass;
        this.diameter = diameter;
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
        The objective of method changeVelocityWithMainThrusters is to update velocity (without taking another time step).
        For now the spacecraft is presumed to be correctly oriented to either increase or reduce the speed.
     */
    public void useMainThrusters (double magnitude) {
        thrusterForceMagnitude = magnitude;
    }
    public void useSuicideBurn (double distance) {
        double burn = Math.pow((suicideBurn/distance), 2);
        thrusterForceMagnitude = thrusterForceMagnitude+burn;
    }
    public void changeVelocityWithMainThrusters(Vector2D deltaV) {
        velocity = sumOf(velocity, deltaV);
    }
    public void updatePositionAndVelocity (int time, Body attractingBody) {
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
    public Vector2D getAcceleration (Body attractingBody) {
        // F=ma => a=F/m
        Vector2D gravitationalForce = getForceAsVector(attractingBody);
        Vector2D drag = windSpeed.getDrag(position);
        Vector2D netForce = sumOf(gravitationalForce, drag);
        Vector2D acceleration = netForce.dividedBy(this.massInKg); // Force is divided by the mass of the accelerating body
        return acceleration;
    }
    public Vector2D getForceAsVector (Body attractingBody) {
        double magnitude = computeForceMagnitude(attractingBody)-thrusterForceMagnitude; //TODO: test thrusterForceMagnitude
        Vector2D direction = computeForceDirection(attractingBody);
        Vector2D forceVector = direction.multipliedBy(magnitude);
        return forceVector;
    }
    /*
    Magnitude of the force as a scalar value
     */
    public double computeForceMagnitude (Body attractingBody) {
        double distance = getDistanceFrom(attractingBody);
        double forceMagnitude = -(G*this.massInKg*attractingBody.massInKg)/Math.pow(distance,2);
        return forceMagnitude;
    }
    public double getDistanceFrom (Body attractingBody) {
        Vector2D distanceVector = differenceOf(this.getPosition(), attractingBody.getPosition());
        double distance = distanceVector.getEuclideanLength();
        return distance;
    }
    /*
    Direction of the force as a unit vector
     */
    public Vector2D computeForceDirection (Body attractingBody) {
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
}
