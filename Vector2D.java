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
}