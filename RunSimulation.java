public class RunSimulation {
    public static final double G = 6.674E-11; // unit: m3⋅kg−1⋅s−2 (gravitational constant)
    static double titanRadius = 2575*1000;
    static double startDistance = titanRadius+1500*1000;
    static double probeMass = 5000;
    static final double titanMass = 1.3452E23;
    private static double orbitalSpeed = Math.sqrt((G*titanMass)/startDistance);
    public static void main(String[] args) {


        for (int i = 0; i<1000; i++) {
            displayInternalSimulation();
            //displayClosedLoopSimulation();
           //displayOpenLoopSimulation();
            //displayLaunchInternalSimulation();
        }
    }
    public static void displayInternalSimulation () {
        final double titanRadius = 2575*1000;
        //final double startingDistance = titanRadius+1500*1000;
        final double probeMass = 5000;
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        SimulationBody probe = new SimulationBody(new Vector2D(startDistance,0), new Vector2D(0, orbitalSpeed/*1600*/), 5000, 1, new WindSpeed(1));
        Simulation internalSim = new Simulation(new OLController(), titan, probe);
        internalSim.run();
    }
    public static void displayOpenLoopSimulation () {
        WindSpeedInterface stochasticWind = new WindSpeedStochastic(1);
        ((WindSpeedStochastic) stochasticWind).setRandomParameter(0.5);
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0),
                                        1.3452E23, 2*titanRadius,
                                        new WindSpeedStochastic(1));
        SimulationBody realProbe = new SimulationBody(new Vector2D(startDistance,0), new Vector2D(0, orbitalSpeed),
                                        5000, 1, stochasticWind);
        Simulation openLoopSim = new Simulation(new OLController(), titan, realProbe);
        openLoopSim.run();
    }
    public static void displayClosedLoopSimulation () {
        WindSpeedInterface stochasticWind = new WindSpeedStochastic(1);
        ((WindSpeedStochastic) stochasticWind).setRandomParameter(0.2);
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0),
                1.3452E23, 2*titanRadius,
                new WindSpeedStochastic(1));
        SimulationBody realProbe = new SimulationBody(new Vector2D(startDistance,0), new Vector2D(0, 1600),
                5000, 1, stochasticWind);
        Simulation closedLoopSim = new Simulation(new FeedbackController(), titan, realProbe);
        closedLoopSim.run();
    }
    public static void displayLaunchInternalSimulation () {
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        SimulationBody probe = new SimulationBody(new Vector2D(-2447818.907860217,799008.2797965868), new Vector2D(0, 0), 5000, 1, new WindSpeed(1));
        Simulation internalSim = new Simulation(new OLController(), titan, probe);
        internalSim.run();
    }
}
