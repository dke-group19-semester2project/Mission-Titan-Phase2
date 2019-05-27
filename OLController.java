import java.util.ArrayList;

public class OLController implements ControllerInterface {
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;
    static double probeMass = 5000;
    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
    SimulationBody internalProbe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
    double constantMultiplier = 22500.25;//10000;//22500.25; // This is the value which worked in internal simulation testing without drag - change once drag is added.
    double inverseDistance = 1/ internalProbe.getDistanceFrom(titan);
    double deltaVMultiplier = Math.pow(constantMultiplier*inverseDistance,2);
    Vector2D deltaV = internalProbe.getVelocity().multipliedBy(-deltaVMultiplier);
    double landingBurnFactor = 0.0001; // This and below values worked in internal simulation without drag
    double maxLandingBurnFactor = 0.2;
    public OLController(){
        //titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        bodies.add(titan);
        //internalProbe = new SimulationBody(new Vector2D(8000000,0), new Vector2D(0, 100), 5000, 1, new WindSpeed(1));
        bodies.add(internalProbe);

    }
    public void update (int timeStep) {
        // Adjust deltaV and apply it to the velocity in internal simulation, update the internal simulation body/ies by the time step
        double currentDistance = internalProbe.getDistanceFrom(titan);
        inverseDistance = 1/ internalProbe.getDistanceFrom(titan);
        deltaVMultiplier = Math.pow(constantMultiplier*inverseDistance,2);
        deltaV = internalProbe.getVelocity().multipliedBy(-deltaVMultiplier);
        double currentSpeed = computeCurrentSpeed();
        if (currentSpeed<10) {
            deltaV = internalProbe.getVelocity().multipliedBy(-(0.5));
        }
         else if (currentDistance<titanRadius+10*1000 || currentSpeed< 1000) {
            deltaV = internalProbe.getVelocity().multipliedBy(-(deltaVMultiplier+landingBurnFactor));
            landingBurnFactor = (landingBurnFactor+maxLandingBurnFactor)/2;
        }
        internalProbe.changeVelocityWithMainThrusters(deltaV);
        internalProbe.updatePositionAndVelocity(1, titan);
    }
    private double computeCurrentSpeed () {
        Vector2D currentVelocity = internalProbe.getVelocity();
        double speed = Math.sqrt(currentVelocity.x*currentVelocity.x + currentVelocity.y*currentVelocity.y);
        return speed;
    }
    public Vector2D updateAndGetDeltaV(Simulation realSim) { // Simulation realSim is not needed by the OL controller. However, the feedback one needs it.
        update(1);
        // Might be worth calling the method update here, so that when the external simulation calls updateAndGetDeltaV, the internal sim and deltaV will update.
        return deltaV;
    }
    public Vector2D convertDeltaVToForce (Vector2D deltaV) {
        // F = m*a/t = m*deltaV (since t=1)
        double xForce = deltaV.x*probeMass;
        double yForce = deltaV.y*probeMass;
        return new Vector2D(xForce, yForce);
    }
    public double convertDeltaVToForceMagnitude (Vector2D deltaV) {
        Vector2D force = convertDeltaVToForce(deltaV);
        double forceMagnitude = Math.sqrt(force.x*force.x + force.y*force.y);
        return forceMagnitude;
    }
    public ArrayList<SimulationBody> getBodies () {
        return bodies;
    }
    public SimulationBody getInternalProbe() {
        return internalProbe;
    }

}

