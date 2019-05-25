import java.util.ArrayList;

public class OLController implements ControllerInterface {
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;
    static double probeMass = 5000;
    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
    SimulationBody internalProbe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
    double constantMultiplier = 22500.25; // This is the value which worked in internal simulation testing without drag - change once drag is added.
    double inverseDistance = 1/ internalProbe.getDistanceFrom(titan);
    double deltaVMultiplier = Math.pow(constantMultiplier*inverseDistance,2);
    Vector2D deltaV = internalProbe.getVelocity().multipliedBy(-deltaVMultiplier);
    double landingBurnFactor = 0.001; // This and below values worked in internal simulation without drag
    double maxLandingBurnFactor = 0.5;
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
        if (currentDistance<titanRadius+10*1000) {
            deltaV = internalProbe.getVelocity().multipliedBy(-landingBurnFactor);
            landingBurnFactor = (landingBurnFactor+maxLandingBurnFactor)/2;
        }
        internalProbe.changeVelocityWithMainThrusters(deltaV);
        internalProbe.updatePositionAndVelocity(1, titan);
    }
    public Vector2D updateAndGetDeltaV() {
        update(1);
        // Might be worth calling the method update here, so that when the external simulation calls updateAndGetDeltaV, the internal sim and deltaV will update.
        return deltaV;
    }
    public ArrayList<SimulationBody> getBodies () {
        return bodies;
    }
    public SimulationBody getInternalProbe() {
        return internalProbe;
    }

}

