import java.lang.Math;
import java.util.ArrayList;

public class WindSpeedImplentationEx{
    //Date starting on January 1,2000
    public static void main(String[] args){
        final double MISSION_DURATION=86400;
        final double TIME_STEP=1;//In seconds
        //Need to use position data of Titan, but relative to Saturn for simplicity.
        Planet titan= new Planet(-7.769539650426797E+08,9.025640976089063E+08,-3.896658973030882E+08,-4.279217248263016E+03,-2.783704647125639E+03,1.856691783450268E+03,1.342E23,0);
        //Saturn is the center of this celestial system.
        Planet saturn = new Planet(0D,0D,0D,0D,0D,0D, 5.68E26,695);

        //Same as the solar system construct.
        ArrayList<SpaceObject> titanSystem= new ArrayList<SpaceObject>();
        titanSystem.add(saturn);
        titanSystem.add(titan);

        //Wind
        WindSpeed titanWindSpeed= new WindSpeed(TIME_STEP);
        //Height is in km. This is the beginning of the outer atmosphere of Titan.
        double height=1400;
        titanWindSpeed.setAtmosphericDensity(height);
        //Position and velocity are in m/s for celestial bodies only.
        titanWindSpeed.pressureGradientForce(-7.769539650426797E+08,9.025640976089063E+08,-4.279217248263016E+03,-2.783704647125639E+03);
        //Initial condition for the wind model.
        double[] check= titanWindSpeed.windSpeed(0,0,0); //pressure(Pa), latitude(degrees)

        //Updates wind model.
        for (int i = 0; i<MISSION_DURATION; i++) {
            if(height>0){
                for (SpaceObject spaceObject : titanSystem) {
                    spaceObject.updateForce(titanSystem);
                    spaceObject.updateAcceleration();
                    spaceObject.updateVelocity(TIME_STEP);
                    spaceObject.updatePosition(TIME_STEP);

                }
                double distanceTravelled = 1;// In km
                height = height - distanceTravelled;
                titanWindSpeed.setAtmosphericDensity(height);
                titanWindSpeed.pressureGradientForce(titanSystem.get(1).getPosition().getX(), titanSystem.get(1).getPosition().getY(), titanSystem.get(1).getVelocity().getX(), titanSystem.get(1).getVelocity().getY());
                double[] pressure = titanWindSpeed.getPressure();
                double[] check2 = titanWindSpeed.windSpeed(pressure[0], pressure[1], 0);
                //Cross-sectional area relates to the surface area of a Sphere. Units m^2
                double crossSectionalArea=4*Math.PI*Math.pow(6,2);
                //Angle of approach should be in radians.
                double angleOfApproach=Math.PI/4;
                double[]forces=titanWindSpeed.getDrag(crossSectionalArea,angleOfApproach);
                //System.out.println(check2[0] + " " + check2[1]);

                if(i%2==0) {
                    //To get the drag and lift. Drag is forces[0] and forces[1] is the lift.
                    //It is in this conditional because the original result was too noisy to be usable.
                    //Will try to fix this as well.
                    System.out.println(forces[0] + " " + forces[1]);
                }
            }
        }
    }
}