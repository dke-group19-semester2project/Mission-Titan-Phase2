public class Leapfrog {
    
    private Vector3D position;
    private Vector3D velocity;
    private Vector3D velocityHalf;
    private double timeStep;

    public Leapfrog(double timeStep) {
        this.timeStep = timeStep;
    }

    public void fullUpdate(SpaceObject object) {

        object.updateAcceleration();

        velocityHalf = object.getVelocity().addVector(object.getAcceleration().multiplyConstant(timeStep).divideConstant(2)); // v(1/2) = v + a*dt/2

        position = object.getPosition().addVector(velocityHalf.multiplyConstant(timeStep)); // x(1/2) = x + v(1/2)*dt
        object.setPosition(position);

        object.updateAcceleration();

        velocity = velocityHalf.addVector(object.getAcceleration().multiplyConstant(timeStep).divideConstant(2)); // v = v(1/2) + a*dt/2
        object.setVelocity(velocity);
    }
}