public class Vector {

    private double x;
    private double y;
    private double z;
    private double distance;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = Math.sqrt(x*x + y*y + z*z);
    }

    public Vector addVector(Vector vector) {
        Vector result = new Vector(0, 0, 0);
        result.x = this.x + vector.x;
        result.y = this.y + vector.y;
        result.z = this.z + vector.z;
        return result;
    }

    public Vector multiplyVector(Vector vector) {
        Vector result = new Vector(0,0,0);
        result.x = this.x*vector.x;
        result.y = this.y*vector.y;
        result.z = this.z*vector.z;
        return result;
    }

    public Vector multiply(double c) {
        Vector result = new Vector(0,0,0);
        result.x = this.x*c;
        result.y = this.y*c;
        result.z = this.z*c;
        return result;
    }

    public Vector normalizeVector() {
        Vector result = new Vector(0,0,0);
        this.distance = this.getDistance();
        result.x = this.x/this.distance;
        result.y = this.y/this.distance;
        result.z = this.z/this.distance;
        return result;
    }

    public Vector substractVector(Vector vector) {
        Vector result = new Vector(0,0,0);
        result.x = this.x-vector.x;
        result.y = this.y-vector.y;
        result.z = this.z-vector.z;
        return result;
    }

    @Override
    public String toString() {
        return this.getX() + " " + this.getY() + " " + this.getZ();
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
}