public class Vector2D {
    double x;
    double y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getEuclideanLength () {
        double length = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
        return length;
    }
    public Vector2D dividedBy (double denominator) {
        double unitX = x/denominator;
        double unitY = y/denominator;
        Vector2D unitVector = new Vector2D(unitX, unitY);
        return unitVector;
    }
    public Vector2D multipliedBy (double multiplier) {
        double newX = x*multiplier;
        double newY = y*multiplier;
        Vector2D product = new Vector2D(newX, newY);
        return product;
    }
    public String toString () {
        String newString = "X = " + x + "   Y = " + y;
        return newString;
    }
    
    public Vector2D addVector(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D addConstant(double d) {
        return new Vector2D(this.x + d, this.y + d);
    }

    public Vector2D substractVector(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    public Vector2D substractConstant(double d) {
        return new Vector2D(this.x - d, this.y - d);
    }

    public Vector2D multiplyVector(Vector2D v) {
        return new Vector2D(this.x * v.x, this.y * v.y);
    }

    public Vector2D multiplyConstant(double d) {
        return new Vector2D(this.x * d, this.y * d);
    }

    public Vector2D divideVector(Vector2D v) {
        return new Vector2D(this.x / v.x, this.y / v.y);
    }

    public Vector2D divideConstant(double d) {
        return new Vector2D(this.x / d, this.y / d);
    }
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
}
