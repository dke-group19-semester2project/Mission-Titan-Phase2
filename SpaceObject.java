import java.util.List;

public interface SpaceObject {
    Vector3D getPosition();
    Vector3D getVelocity();
    Vector3D getAcceleration();
    Vector3D getForce();
    double getMass();
    void updateForce(List<SpaceObject> o);
    void updateAcceleration();
    void updateVelocity(double t);
    void updatePosition(double t);
}