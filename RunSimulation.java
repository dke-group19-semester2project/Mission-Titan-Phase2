public class RunSimulation {
    public static void main(String[] args) {
        double titanRadius = 2575*1000;
        double startingDistance = titanRadius+800*1000;
        double probeMass = 5000;
        SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));

        SimulationBody probe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));

        Simulation sim = new Simulation(new OLController(), titan, probe);
        sim.run();
    }
}
