import java.util.ArrayList;
import java.util.List;

public class FeedbackController2 implements ControllerInterface {

    private Vector2D FBProbeVelocity;
    private Vector2D OLProbeVelocity;
    private Vector2D FBProbePosition;
    private Vector2D OLProbePosition;
    private Vector2D WindForce = new Vector2D(0,0);
    private Vector2D deltaPFeedback;
    private Vector2D deltaVFeedback;
    private double totalForce = 0;
    private double distanceTraveled = 0;
    private double forceUsed = 0;
    private List<Vector2D> errors = new ArrayList<Vector2D>();

    private boolean velocityCheck = true;
    
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

    public FeedbackController2() {
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

        if (velocityCheck == true) {                                // execute when adjusting for velocity
            FBProbeVelocity = sim.getRealProbe().getVelocity();
            OLProbeVelocity = internalProbe.getVelocity();
            deltaVFeedback = FBProbeVelocity.substractVector(OLProbeVelocity).multiplyConstant(-1);
            errors.add(deltaVFeedback);
            Vector2D integralTerm = integrateTrapezoid(errors);
            //Vector2D derivativeTerm = threePointBackwardDifference(errors);
            Vector2D totalError = deltaVFeedback.addVector(integralTerm);//.addVector(derivativeTerm);


            totalForce = totalForce + convertDeltaVToForceMagnitude(totalError); // at every step the amount of force generated is added to the total force
            forceUsed = convertDeltaVToForceMagnitude(totalError); 
            return totalError;
        }

        else {                                                      // execute when adjusting for position
            FBProbePosition = sim.getRealProbe().getPosition();
            OLProbePosition = internalProbe.getPosition();
            deltaPFeedback = FBProbePosition.substractVector(OLProbePosition).multiplyConstant(-1);
            errors.add(deltaPFeedback);
            Vector2D integralTerm = integrateTrapezoid(errors);
            //Vector2D derivativeTerm = threePointBackwardDifference(errors);
            Vector2D totalError = deltaPFeedback.addVector(integralTerm.multiplyConstant(0.5)); //.addVector(derivativeTerm);
            return totalError;
        }
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
        double xForce = deltaV.x*probeMass;
        double yForce = deltaV.y*probeMass;
        return new Vector2D(xForce, yForce);
    }
    public double convertDeltaVToForceMagnitude (Vector2D deltaV) {
        Vector2D force = convertDeltaVToForce(deltaV);
        double forceMagnitude = Math.sqrt(force.x*force.x + force.y*force.y);
        return forceMagnitude;
    }

    public double getTotalForce() {
        return totalForce;
    }

    public double getForceUsed() {
        return forceUsed;
    }

    public Vector2D integrateTrapezoid(List<Vector2D> allErrors) {
        Vector2D result = new Vector2D(0, 0);
        Vector2D combination = new Vector2D(0, 0);
        if (allErrors.size() == 1) return new Vector2D(0, 0);
        for (int i = 0; i < allErrors.size(); i++) {
            if (i == 0 || i == allErrors.size()-1) {
                combination = combination.addVector(allErrors.get(i).multiplyConstant(0.5));
            } else {
                combination = combination.addVector(allErrors.get(i));
            }   
        }
        result = combination.divideConstant(allErrors.size()-1);
        return result;
    }

    public Vector2D threePointBackwardDifference(List<Vector2D> allErrors) {
        if (allErrors.size() < 3) return new Vector2D(0, 0);
        Vector2D result = new Vector2D(0, 0);
        Vector2D combination = new Vector2D(0, 0);

        Vector2D last = allErrors.get(allErrors.size()-1);
        Vector2D lastMinusH = allErrors.get(allErrors.size()-2);
        Vector2D lastMinus2H = allErrors.get(allErrors.size()-3);

        combination = last.multiplyConstant(3).substractVector(lastMinusH.multiplyConstant(4)).addVector(lastMinus2H);
        result = combination.divideConstant(2);
        return result;
    }
}
