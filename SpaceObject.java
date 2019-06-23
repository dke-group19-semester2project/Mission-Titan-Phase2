import java.util.List;

public interface SpaceObject {
    Vector getPosition();
    Vector getVelocity();
    Vector getAcceleration();
    Vector getForce();
    double getMass();
    void updateForce(List<SpaceObject> o);
    void updateAcceleration();
    void updateVelocity(double t);
    void updatePosition(double t);
}