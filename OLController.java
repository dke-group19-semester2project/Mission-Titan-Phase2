import java.util.ArrayList;

public class OLController implements ControllerInterface {
    public static final double G = 6.674E-11; // unit: m3⋅kg−1⋅s−2 (gravitational constant)
    static double titanRadius = 2575*1000;
    static double startDistanceInternalSim = titanRadius+1500*1000;
    static double probeMass = 5000;
    static final double titanMass = 1.3452E23;
    static final double standardGravitationalParameter = G*titanMass;
    private double orbitalSpeed = Math.sqrt((standardGravitationalParameter)/startDistanceInternalSim);
    private double forceUsed = 0;
    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
    SimulationBody internalProbe = new SimulationBody(new Vector2D(startDistanceInternalSim,0), new Vector2D(0, orbitalSpeed/*1600*/), 5000, 1, new WindSpeed(1));
    double constantMultiplier = 22500.25;//10000;//22500.25; // This is the value which worked in internal simulation testing without drag - change once drag is added.
    double inverseDistance = 1/ internalProbe.getDistanceFrom(titan);
    double deltaVMultiplier = Math.pow(constantMultiplier*inverseDistance,2);
    Vector2D deltaV = internalProbe.getVelocity().multipliedBy(-deltaVMultiplier);
    double landingBurnFactor = 0.0001; // This and below values worked in internal simulation without drag
    double maxLandingBurnFactor = 0.2;
    boolean hohmannImpulseWasApplied = false;
    public int simulationTime = 0;
    public OLController(){
        //titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        bodies.add(titan);
        //internalProbe = new SimulationBody(new Vector2D(8000000,0), new Vector2D(0, 100), 5000, 1, new WindSpeed(1));
        bodies.add(internalProbe);

    }
    public Vector2D updateWithContinuousThrust(int timeStep, Simulation sim) {
        // Adjust deltaV and apply it to the velocity in internal simulation, updateWithContinuousThrust the internal simulation body/ies by the time step
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
         Vector2D thrust = convertDeltaVToForce(deltaV);
        internalProbe.changeMainThrust(thrust);
        internalProbe.updatePositionAndVelocity(1, titan);
        forceUsed = convertDeltaVToForceMagnitude(deltaV);
        return thrust;
    }
    /*
    Hohmann transfer update should only return non-zero values at the beginning of the landing simulation and right before impact.
     */
    public Vector2D updateWithHohmannTransfer () {
        simulationTime++;
        // TODO: Implement method so that it applies one big impulse in the beginning for transfer orbit, and suicide burn at the end
        double currentDistance = internalProbe.getDistanceFrom(titan);
        double currentSpeed = computeCurrentSpeed();
        Vector2D deltaV;
        if (!hohmannImpulseWasApplied) {
            double hohmannDeltaV = -178.1; // Based on the calculation of Hohmann Transfer. This parameter alone is sufficient with no drag.
            deltaV = new Vector2D(0, hohmannDeltaV-44); // -44 was found with binary search and is used to counter drag.
            hohmannImpulseWasApplied = true;
        } else if (internalProbe.simulationTime>=5860 || currentDistance<titanRadius+50 || currentSpeed< 100) {
            Vector2D windForce = internalProbe.currentDrag;
            //Vector2D windResistDeltaV = convertForceToDeltaV(windForce).multipliedBy(-2);
            System.out.println("Current velocity: " + internalProbe.getVelocity().toString());
            double deltaY = Math.abs(internalProbe.getVelocity().getY()-2);
            double deltaX = Math.abs(internalProbe.getVelocity().getX()-2);
            deltaV = new Vector2D(deltaX, deltaY);//.substractVector(windResistDeltaV);
        } else {
            deltaV = new Vector2D(0,0);
        }
        Vector2D thrust = convertDeltaVToForce(deltaV);
        internalProbe.changeMainThrust(thrust);
        internalProbe.updatePositionAndVelocity(1, titan);
        forceUsed = convertDeltaVToForceMagnitude(deltaV);
        return thrust;
    }
//    public Vector2D updateHohmannLaunch () {
//        if (!hohmannImpulseWasApplied) {
//            double doubleDeltaV = 2700;
//            deltaV = new Vector2D(doubleDeltaV, 0);
//            hohmannImpulseWasApplied = true;
//        } else {
//            deltaV = new Vector2D(0,0);
//        }
//        internalProbe.changeMainThrust(deltaV);
//        internalProbe.updatePositionAndVelocity(1, titan);
//        forceUsed = convertDeltaVToForceMagnitude(deltaV);
//        return deltaV;
//    }

    private double computeCurrentSpeed () {
        Vector2D currentVelocity = internalProbe.getVelocity();
        double speed = Math.sqrt(currentVelocity.x*currentVelocity.x + currentVelocity.y*currentVelocity.y);
        return speed;
    }
    public Vector2D updateAndGetDeltaV(Simulation realSim) { // Simulation realSim is not needed by the OL controller. However, the feedback one needs it.
        //Vector2D deltaVupdated = updateWithHohmannTransfer();
        Vector2D deltaVupdated = updateWithContinuousThrust(1, realSim);
        //Vector2D deltaVupdated = updateHohmannLaunch();
        return deltaVupdated;
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
    public Vector2D convertForceToDeltaV (Vector2D force) {
        // deltaV = F/m.
        Vector2D deltaV = force.dividedBy(probeMass);
        return deltaV;
    }
    public ArrayList<SimulationBody> getBodies () {
        return bodies;
    }
    public SimulationBody getInternalProbe() {
        return internalProbe;
    }
    public double getForceUsed() {
        return forceUsed;
    }

}

