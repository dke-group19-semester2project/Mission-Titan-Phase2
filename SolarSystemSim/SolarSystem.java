import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
//Each index corresponds to a planetary body.
//Days corresponds to one Earth day.

public class SolarSystem {
    final double TIME_STEP = 1;
    final double YEAR_TO_SECONDS = 365*86400;
    List<SpaceObject> listOfObjects;
    double[] mercuryPos= new double[316];
    List<Vector> venusPos=  new ArrayList<Vector>();
    List<Vector> earthPos=  new ArrayList<Vector>();
    List<Vector> marsPos= new ArrayList<Vector>();
    List<Vector> jupiterPos= new ArrayList<Vector>();
    List<Vector> saturnPos= new ArrayList<Vector>();
    SpaceObject sun = new Star(0D,0D,0D,0D,0D,0D, 1.989E30);
    SpaceObject mercury = new Planet(-2.105262111032039E+10, -6.640663808353403E+10, -3.492446023382954E+09,  3.665298706393840E+04, -1.228983810111077E+04, -4.368172898981951E+03, 3.301E+23);
    SpaceObject venus = new Planet(-1.075055502695123E+11, -3.366520720591562E+09,  6.159219802771119E+09,  8.891598046362434E+02, -3.515920774124290E+04, -5.318594054684045E+02, 4.867E+24);
    SpaceObject earth = new Planet(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+05, -2.983983333368269E+04, -5.207633918704476E+03,  6.169062303484907E-02, 5.972E+24);
    SpaceObject moon = new Moon(-2.552857888050620E+10,  1.446860363961675E+11,  3.593933517466486E+07, -2.927904627038706E+04, -6.007566180814270E+03, -1.577640655646029E00, 7.349E+22);
    SpaceObject mars = new Planet(2.079950549908331E+11, -3.143009561106971E+09, -5.178781160069674E+09,  1.295003532851602E+03,  2.629442067068712E+04,  5.190097267545717E+02, 6.417E+23);
    SpaceObject jupiter = new Planet(5.989091595026654E+11,  4.391225931434094E+11, -1.523254615467653E+10, -7.901937631606453E+03,  1.116317697592017E+04,  1.306729060953327E+02, 1.899E+27);
    SpaceObject saturn = new Planet(9.587063371332250E+11,  9.825652108702583E+11, -5.522065686935234E+10, -7.428885681642827E+03,  6.738814233429374E+03,  1.776643556375199E+02, 5.685E+26);
    SpaceObject titan = new Moon( 9.579293831681823E+11,  9.834677749678675E+11, -5.561032276665545E+10, -1.170810292990584E+04,  3.955109586303735E+03,  2.034356139087789E+03, 13455.3E+19);
    double gravitationalConstant = 6.67408E-11; //m^3/kg*s^2
    // All positions and velocities are initialized with data from 1 January 2000, NASA's Horizon web
    
    public SolarSystem() {
        listOfObjects = new ArrayList<SpaceObject>();
        listOfObjects.add(sun);
        listOfObjects.add(mercury);
        listOfObjects.add(venus);
        listOfObjects.add(earth);
        listOfObjects.add(moon);
        listOfObjects.add(mars);
        listOfObjects.add(jupiter);
        listOfObjects.add(saturn);
        listOfObjects.add(titan);
    }

    public void updateSolarSystem() {
        int index=0;
        for (int i = 0; i<YEAR_TO_SECONDS; i++) {
            for (SpaceObject spaceObject : listOfObjects) {
                spaceObject.updateForce(listOfObjects);
                spaceObject.updateAcceleration();
                spaceObject.updateVelocity(TIME_STEP);
                spaceObject.updatePosition(TIME_STEP);
            }

                /*if (i % 100000 == 0) {
                    mercuryPos[index]=listOfObjects.get(1).getPosition().getX()/(10E+6);
                    System.out.println(mercuryPos[index]);
                    venusPos.add(listOfObjects.get(2).getPosition());
                    earthPos.add(listOfObjects.get(3).getPosition());
                    marsPos.add(listOfObjects.get(4).getPosition());
                    jupiterPos.add(listOfObjects.get(5).getPosition());
                    saturnPos.add(listOfObjects.get(6).getPosition());
               moonXPos[index]=listOfObjects.get(4).getPosition().getX()/(10E+6);
               moonYPos[index]=listOfObjects.get(4).getPosition().getY()/(10E+6);
               moonZPos[index]=listOfObjects.get(4).getPosition().getZ()/(10E+6);
                    index++;
                }*/
        }
        System.out.println(listOfObjects.get(1).getPosition().getX());

    }


    public double getMercuryPosition(int index){
        return mercuryPos[index];
    }


    public static void main(String args[]) {
        SolarSystem solarSystem = new SolarSystem();
        solarSystem.updateSolarSystem();
        //System.out.println(solarSystem.getMercuryPosition(1));
        System.out.println(solarSystem.listOfObjects.get(5).getPosition().getY());
        //System.out.println(solarSystem.listOfObjects.get(5).getPosition().getZ());
    }
}
