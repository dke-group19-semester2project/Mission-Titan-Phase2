public interface ControllerInterface {
    public void update (int timeStep);
    public Vector2D updateAndGetDeltaV();
    public Vector2D convertDeltaVToForce (Vector2D deltaV);
    public double convertDeltaVToForceMagnitude (Vector2D deltaV);
}
