import java.util.ArrayList;
public class WindSpeedStochastic implements WindSpeedInterface {
    private double massSaturn=5.68E26;//(Units: kg)
    private double massTitan=1.342E23;// (Units: kg)
    private double atmosphereDensity;//Atmospheric density of the planet on which you want to calculate the wind speed. (Units: kg/m^3)
    private double xResultingPressure;//x component of the atmospheric pressure(Units: Pa)
    private double yResultingPressure;//y component of the atmospheric pressure(Units: Pa)
    private double angularVelocity=4.56E-6;//Angular velocity of Titan (Rotation of the target body around its axis.)(Units: s^-1)
    private double latitude;//Latitude on the target body.(Units: Degrees)
    private double xPressureGradientForce; //x component of the Pressure Gradient Force (Units: N)
    private double yPressureGradientForce; //y component of the Pressure Gradient Force(Units: N)
    private double xWindVelocityCF;//x-Component of wind velocity above 800m (Units: m/s)
    private double yWindVelocityCF;//y-Component of wind velocity above 800m (Units: m/s)
    private double gravitationalConstant = 6.67408E-11; //m^3/kg*s^2
    private double atmosphereHeight=1500E3;//Units: m
    private double titanRadius=2.56E6;//Units: m
    private double atmosphericMass;//Units: kg
    private double conversionFactor=10E3; //Converts m/s to km/s for windspeed.
    private double timeStep;//How many seconds per calculation.
    //Need to use position data of Titan, but relative to Saturn for simplicity.
    private Planet titan= new Planet(-7.769539650426797E+08,9.025640976089063E+08,-3.896658973030882E+08,-4.279217248263016E+03,-2.783704647125639E+03,1.856691783450268E+03,1.342E23,0);
    //Saturn is the center of this celestial system.
    private Planet saturn = new Planet(0D,0D,0D,0D,0D,0D, 5.68E26,695);
    private ArrayList<SpaceObject> titanSystem= new ArrayList<SpaceObject>();
    private final double ATMOSPHERE_HEIGHT=1400;
   
    WindSpeedStochastic(double timeStep){
        this.timeStep=timeStep;
        titanSystem.add(saturn);
        titanSystem.add(titan);
        setAtmosphericDensity(ATMOSPHERE_HEIGHT);
        //Position information of Titan and Saturn on January 1, 2000
        pressureGradientForce(-7.769539650426797E+08,9.025640976089063E+08,-4.279217248263016E+03,-2.783704647125639E+03);
    }

    public void setAtmosphericDensity(double height){
       atmosphereDensity=0.01+(height-128)*(-0.0004347826+(height-105)*(0.000019486+(height-77)*(-0.0000017686+(height-61)*(0.000000041+(height-51)*(-5.0588235E-11+(height-43)*(2.9033613E-11+(height-37)*(-1.4199379E-11+(height-33)*(-9.3512492E-13+(height-17)*-2.7954473E-14))))))));
    }

    public void pressureGradientForce(double xPositionTargetBody, double yPositionTargetBody, double xVelocityTargetBody, double yVelocityTargetBody){
        xPressureGradientForce=((massTitan *Math.pow(xVelocityTargetBody,2))/xPositionTargetBody)+(gravitationalConstant*massSaturn* massTitan)/(Math.pow(xPositionTargetBody,2));
        yPressureGradientForce=((massTitan *Math.pow(yVelocityTargetBody,2))/yPositionTargetBody)+(gravitationalConstant*massSaturn* massTitan)/(Math.pow(yPositionTargetBody,2));
    }

    public void setAtmosphericMass(){
        //Unsure if this is used. Wait until I implement the thermal profile, if I get there.
        double volumeOfTargetBody=(4/3)*Math.PI*Math.pow(titanRadius,3);
        double volumeOfAtmosphere=(4/3)*Math.PI*Math.pow(titanRadius +atmosphereHeight,3);
        atmosphericMass=atmosphereDensity*(volumeOfAtmosphere-volumeOfTargetBody);
    }

    public double getAtmosphereDensity(){
        return atmosphereDensity;
    }

    public void windSpeed(double xStartingPressure, double yStartingPressure, double latitude){
        Vector2D windVelocity= new Vector2D(0,0);

        xResultingPressure=(xPressureGradientForce/(4*Math.PI*Math.pow(titanRadius,2)))-xStartingPressure;
        double xPressureDifference=xStartingPressure-xResultingPressure;
        xWindVelocityCF=(-1/atmosphereDensity)*(xPressureDifference/(2*Math.PI* titanRadius))*timeStep;
        if(latitude!=0) {
            xWindVelocityCF=(xWindVelocityCF + Math.random()*0.2*xWindVelocityCF)/ (-2 * angularVelocity * Math.sin(latitude*Math.PI/180));
        }else{
            xWindVelocityCF=xWindVelocityCF+ Math.random()*0.2*xWindVelocityCF;
        }
       
        yResultingPressure=(yPressureGradientForce/(4*Math.PI*Math.pow(titanRadius,2)))-yStartingPressure;
        double yPressureDifference=yStartingPressure-yResultingPressure;
        yWindVelocityCF=(-1/atmosphereDensity)*(yPressureDifference/(2*Math.PI* titanRadius))*timeStep;
        if(latitude!=0) {
            yWindVelocityCF=(yWindVelocityCF +Math.random()*0.2*xWindVelocityCF)/ (-2 * angularVelocity * Math.sin(latitude*Math.PI/180));
        }else{
            yWindVelocityCF= yWindVelocityCF+Math.random()*0.2*xWindVelocityCF;
        }

    }

    public Vector2D getPressure(){
        Vector2D currentPressure= new Vector2D(xResultingPressure,yResultingPressure);
        return currentPressure;
    }

    public Vector2D updateModelAndGetDrag(Vector2D positionOfCraft,Vector2D velocityOfCraft){
        //Cross-sectional area relates to the area of a circle. Units m^2
        double altitude=Math.sqrt(Math.pow(positionOfCraft.getX(),2)+Math.pow(positionOfCraft.getY(),2));
        double height=(altitude-titanRadius)/10E3;
        if(height<=ATMOSPHERE_HEIGHT && height>=0){
            for (SpaceObject spaceObject : titanSystem) {
                spaceObject.updateForce(titanSystem);
                spaceObject.updateAcceleration();
                spaceObject.updateVelocity(timeStep);
                spaceObject.updatePosition(timeStep);

            }
            setAtmosphericDensity(height);
            pressureGradientForce(titanSystem.get(1).getPosition().getX(), titanSystem.get(1).getPosition().getY(), titanSystem.get(1).getVelocity().getX(), titanSystem.get(1).getVelocity().getY());
            windSpeed(xResultingPressure, yResultingPressure, 0);
            double crossSectionalArea=Math.PI*Math.pow(6,2);
            double angleOfApproach=Math.atan2(positionOfCraft.getY(), positionOfCraft.getX());
            double coefficentDrag=0.2;//Titan's atmosphere has a reynold's number of 10^7. Cd of a sphere at 10^7 is 0.2. 
            Vector2D force= new Vector2D((atmosphereDensity*Math.pow(xWindVelocityCF,2)*crossSectionalArea*Math.cos(angleOfApproach)*0.5*coefficentDrag),(atmosphereDensity*Math.pow(yWindVelocityCF,2)*crossSectionalArea*Math.sin(angleOfApproach)*0.5*coefficentDrag));
        return force;
        }else{
            Vector2D force=new Vector2D(0,0);
            return force;
        }
    }        
}
