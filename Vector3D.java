public class Vector3D {

    private double x;
    private double y;
    private double z;
    private double length;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = this.getLength();
    }

    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.length = this.getLength();
    }

    public Vector3D addVector(Vector3D v) {
        return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3D addConstant(double d) {
        return new Vector3D(this.x + d, this.y + d, this.z + d);
    }

    public Vector3D substractVector(Vector3D v) {
        return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3D substractConstant(double d) {
        return new Vector3D(this.x - d, this.y - d, this.z - d);
    }

    public Vector3D multiplyVector(Vector3D v) {
        return new Vector3D(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    public Vector3D multiplyConstant(double d) {
        return new Vector3D(this.x * d, this.y * d, this.z * d);
    }

    public Vector3D divideVector(Vector3D v) {
        return new Vector3D(this.x / v.x, this.y / v.y, this.z / v.z);
    }

    public Vector3D divideConstant(double d) {
        return new Vector3D(this.x / d, this.y / d, this.z / d);
    }

    /**
     * this method normalizes a vector
     * @return returns a normalized vector
     */
    public Vector3D normalizeVector() {
        this.length = this.getLength();
        return new Vector3D(this.x / this.length, this.y / this.length, this.z / this.length);
    }

    @Override  
    public String toString() {
        return "x : " + this.getX() + " y :" + this.getY() + " z :" + this.getZ();
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
     * @return the length
     */
    public double getLength() {
        this.setLength(Math.sqrt(x * x + y * y + z * z));
        return this.length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }

    public Vector3D clone() {
        return new Vector3D(this.x, this.y, this.z);
    }
}