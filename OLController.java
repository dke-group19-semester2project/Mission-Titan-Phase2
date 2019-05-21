import java.util.ArrayList;

public class OLController implements ControllerInterface {
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;
    static double probeMass = 5000;
    private static ArrayList<SimulationBody> bodies = new ArrayList<SimulationBody>();
    SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
    SimulationBody probe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
    double constantMultiplier = 22500.25; // This is the value which worked in internal simulation testing without drag - change once drag is added.
    double inverseDistance = 1/probe.getDistanceFrom(titan);
    double deltaVMultiplier = Math.pow(constantMultiplier*inverseDistance,2);
    Vector2D deltaV = probe.getVelocity().multipliedBy(-deltaVMultiplier);
    double landingBurnFactor = 0.001; // This and below values worked in internal simulation without drag
    double maxLandingBurnFactor = 0.5;
    public OLController(){
        //titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        bodies.add(titan);
        //probe = new SimulationBody(new Vector2D(8000000,0), new Vector2D(0, 100), 5000, 1, new WindSpeed(1));
        bodies.add(probe);

    }
    public void update (int timeStep) {
        // Adjust deltaV and apply it to the velocity in internal simulation, update the internal simulation body/ies by the time step
        double currentDistance = probe.getDistanceFrom(titan);
        if (currentDistance<titanRadius+10*1000) {
            deltaV = probe.getVelocity().multipliedBy(-landingBurnFactor);
            landingBurnFactor = (landingBurnFactor+maxLandingBurnFactor)/2;
        }
        probe.changeVelocityWithMainThrusters(deltaV);
        probe.updatePositionAndVelocity(1, titan);
    }
    public Vector2D getDeltaV () {
        update(1);
        // Might be worth calling the method update here, so that when the external simulation calls getDeltaV, the internal sim and deltaV will update.
        return deltaV;
    }
    public ArrayList<SimulationBody> getBodies () {
        return bodies;
    }
    public SimulationBody probe () {
        return probe;
    }

}

