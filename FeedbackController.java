import java.util.ArrayList;

public class FeedbackController implements ControllerInterface {

    private Vector2D FBProbeVelocity;
    private Vector2D OLProbeVelocity;
    private Vector2D WindForce = new Vector2D(0,0);
    private Vector2D deltaVFeedback;
    private double forceUsed = 0;
    
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;
    static double probeMass = 5000;
    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    
    SimulationBody titan = new SimulationBody(new Vector2D(0, 0), new Vector2D(0,0),  1.3452E23, 2*titanRadius, new WindSpeed(1));
    SimulationBody internalProbe = new SimulationBody(new Vector2D(startingDistance, 0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));

    private double constantMultiplier = 22500.25;//10000;//22500.25; // This is the value which worked in internal simulation testing without drag - change once drag is added.
    private double inverseDistance = 1/ internalProbe.getDistanceFrom(titan);
    private double deltaVMultiplier = Math.pow(constantMultiplier*inverseDistance,2);
    private Vector2D deltaV = internalProbe.getVelocity().multipliedBy(-deltaVMultiplier);
    private double landingBurnFactor = 0.0001; // This and below values worked in internal simulation without drag
    private double maxLandingBurnFactor = 0.2;

    public FeedbackController() {
        bodies.add(titan);
        bodies.add(internalProbe);
    }

    public Vector2D update(int timeStep, Simulation sim) {
        
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
        
        FBProbeVelocity = sim.getRealProbe().getVelocity();
        OLProbeVelocity = internalProbe.getVelocity();
        deltaVFeedback = FBProbeVelocity.substractVector(OLProbeVelocity).multiplyConstant(-1);
        forceUsed = convertDeltaVToForceMagnitude(deltaVFeedback);
        
        return deltaVFeedback;
    }

    private double computeCurrentSpeed () {
        Vector2D currentVelocity = internalProbe.getVelocity();
        double speed = Math.sqrt(currentVelocity.x*currentVelocity.x + currentVelocity.y*currentVelocity.y);
        return speed;
    }

    public Vector2D updateAndGetDeltaV(Simulation sim) {
        Vector2D  deltaVUpdated = update(1, sim);
        return deltaVUpdated;
    }
    public Vector2D convertDeltaVToForce (Vector2D deltaV) {
        double xForce = deltaVFeedback.x*probeMass;
        double yForce = deltaVFeedback.y*probeMass;
        return new Vector2D(xForce, yForce);
    }
    public double convertDeltaVToForceMagnitude (Vector2D deltaV) {
        Vector2D force = convertDeltaVToForce(deltaV);
        double forceMagnitude = Math.sqrt(force.x*force.x + force.y*force.y);
        return forceMagnitude;
    }
    public double getForceUsed() {
        return forceUsed;
    }
}
