public class RunSimulation {
    public static void main(String[] args) {
        displayInternalSimulation();
        //displayOpenLoopSimulation();

    }
    public static void displayInternalSimulation () {
        final double titanRadius = 2575*1000;
        final double startingDistance = titanRadius+800*1000;
        final double probeMass = 5000;
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        SimulationBody probe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
        Simulation internalSim = new Simulation(new OLController(), titan, probe);
        internalSim.run();
    }
    public static void displayOpenLoopSimulation () {
        final double titanRadius = 2575*1000;
        final double startingDistance = titanRadius+800*1000;
        final double probeMass = 5000;
        WindSpeedInterface stochasticWind = new WindSpeedStochastic(1);
        ((WindSpeedStochastic) stochasticWind).setRandomParameter(0);
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeedStochastic(1));
        SimulationBody realProbe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, stochasticWind);
        Simulation openLoopSim = new Simulation(new OLController(), titan, realProbe);
        openLoopSim.run();
    }
    public static void displayClosedLoopSimulation () {
        /*
        TODO: Implement once feedback controller is available
         */
    }
}
