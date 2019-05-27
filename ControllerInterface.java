public interface ControllerInterface {
    public Vector2D update (int timeStep, Simulation sim);
    public Vector2D updateAndGetDeltaV (Simulation sim);
    public Vector2D convertDeltaVToForce (Vector2D deltaV);
    public double convertDeltaVToForceMagnitude (Vector2D deltaV);
}
