public class WindSpeed{
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
    /*Must have it output in km/s.
    Height must also be in km.*/
   
    WindSpeed(double timeStep){
        this.timeStep=timeStep;
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

    public double[] windSpeed(double xStartingPressure, double yStartingPressure, int latitude){
        double[] windVelocity= new double[2];

        xResultingPressure=(xPressureGradientForce/(4*Math.PI*Math.pow(titanRadius,2)))-xStartingPressure;
        double xPressureDifference=xStartingPressure-xResultingPressure;
        xWindVelocityCF=(-1/atmosphereDensity)*(xPressureDifference/(2*Math.PI* titanRadius))*timeStep;
        if(latitude!=0) {
            windVelocity[0] = (xWindVelocityCF / (-2 * angularVelocity * Math.sin(latitude*Math.PI/180)));
        }else{
            windVelocity[0]=xWindVelocityCF;
        }
       
        yResultingPressure=(yPressureGradientForce/(4*Math.PI*Math.pow(titanRadius,2)))-yStartingPressure;
        double yPressureDifference=yStartingPressure-yResultingPressure;
        yWindVelocityCF=(-1/atmosphereDensity)*(yPressureDifference/(2*Math.PI* titanRadius))*timeStep;
        if(latitude!=0) {
            windVelocity[1] = (yWindVelocityCF / (-2 * angularVelocity * Math.sin(latitude*Math.PI/180)));
        }else{
            windVelocity[1]=yWindVelocityCF;
        }
        return windVelocity;
    }

    public double[] getPressure(){
        double[] currentPressure= new double[2];
        currentPressure[0]=xResultingPressure;
        currentPressure[1]=yResultingPressure;
        return currentPressure;
    }

    public double[] getDrag(double crossSectionalArea, double angleOfApproach){
        double[] force= getPressure();
        force[0]=xResultingPressure*crossSectionalArea*Math.cos(angleOfApproach);
        force[1]=yResultingPressure*Math.sin(angleOfApproach)*crossSectionalArea;
        return force;

    }
}
