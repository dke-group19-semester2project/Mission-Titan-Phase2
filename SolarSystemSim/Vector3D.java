public class Vector3D {

    private double x;
    private double y;
    private double z;
    private double distance;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3D addVector(Vector3D vector3D) {
        Vector3D result = new Vector3D(0, 0, 0);
        result.x = this.x + vector3D.x;
        result.y = this.y + vector3D.y;
        result.z = this.z + vector3D.z;
        return result;
    }
    public Vector3D addConstant(double d) {
        return new Vector3D(this.x + d, this.y + d, this.z + d);
    }

    public Vector3D multiplyVector(Vector3D vector3D) {
        Vector3D result = new Vector3D(0,0,0);
        result.x = this.x* vector3D.x;
        result.y = this.y* vector3D.y;
        result.z = this.z* vector3D.z;
        return result;
    }

    public Vector3D multiplyConstant(double c) {
        Vector3D result = new Vector3D(0,0,0);
        result.x = this.x*c;
        result.y = this.y*c;
        result.z = this.z*c;
        return result;
    }

    public Vector3D normalizeVector() {
        Vector3D result = new Vector3D(0,0,0);
        this.distance = this.getLength();
        result.x = this.x/this.distance;
        result.y = this.y/this.distance;
        result.z = this.z/this.distance;
        return result;
    }

    public Vector3D substractVector(Vector3D vector3D) {
        Vector3D result = new Vector3D(0,0,0);
        result.x = this.x- vector3D.x;
        result.y = this.y- vector3D.y;
        result.z = this.z- vector3D.z;
        return result;
    }
    public Vector3D substractConstant(double d) {
        return new Vector3D(this.x - d, this.y - d, this.z - d);
    }
    public Vector3D divideVector(Vector3D v) {
        return new Vector3D(this.x / v.x, this.y / v.y, this.z / v.z);
    }

    public Vector3D divideConstant(double d) {
        return new Vector3D(this.x / d, this.y / d, this.z / d);
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
     * 
     * @return the vecotr
     */
    public Vector3D getPosition(){
        return new Vector3D(x, y, z);
    }
    /**
     * @return the distance
     */
    public double getLength() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
