import java.util.ArrayList;

public class SolarSystem {

    private ArrayList<SpaceObject> objectList;
    private Leapfrog leapfrog = new Leapfrog(1);

    private SpaceObject sun = new SpaceObject(0D,0D,0D,0D,0D,0D, 1.989E30);
    private SpaceObject mercury = new SpaceObject(-2.105262111032039E+10, -6.640663808353403E+10, -3.492446023382954E+09,  3.665298706393840E+04, -1.228983810111077E+04, -4.368172898981951E+03, 3.301E+23);
    private SpaceObject venus = new SpaceObject(-1.075055502695123E+11, -3.366520720591562E+09,  6.159219802771119E+09,  8.891598046362434E+02, -3.515920774124290E+04, -5.318594054684045E+02, 4.867E+24);
    private SpaceObject earth = new SpaceObject(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+05, -2.983983333368269E+04, -5.207633918704476E+03,  6.169062303484907E-02, 5.972E+24);
    private SpaceObject moon = new SpaceObject(-2.552857888050620E+10,  1.446860363961675E+11,  3.593933517466486E+07, -2.927904627038706E+04, -6.007566180814270E+03, -1.577640655646029E00, 7.349E+22);
    private SpaceObject mars = new SpaceObject(2.079950549908331E+11, -3.143009561106971E+09, -5.178781160069674E+09,  1.295003532851602E+03,  2.629442067068712E+04,  5.190097267545717E+02, 6.417E+23);
    private SpaceObject jupiter = new SpaceObject(5.989091595026654E+11,  4.391225931434094E+11, -1.523254615467653E+10, -7.901937631606453E+03,  1.116317697592017E+04,  1.306729060953327E+02, 1.899E+27);
    private SpaceObject saturn = new SpaceObject(9.587063371332250E+11,  9.825652108702583E+11, -5.522065686935234E+10, -7.428885681642827E+03,  6.738814233429374E+03,  1.776643556375199E+02, 5.685E+26);
    private SpaceObject titan = new SpaceObject( 9.579293831681823E+11,  9.834677749678675E+11, -5.561032276665545E+10, -1.170810292990584E+04,  3.955109586303735E+03,  2.034356139087789E+03, 13455.3E+19);

    //private Rocket rocket = new Rocket(0,0,0,0,0,0,0);

    private double gravitationalConstant = 6.67408E-11; //m^3/kg*s^2
    // All positions and velocities are initialized with data from 1 January 2000, NASA's Horizon web

    public SolarSystem() {
        objectList = new ArrayList();
        objectList.add(sun);
        objectList.add(mercury);
        objectList.add(venus);
        objectList.add(earth);
        objectList.add(moon);
        objectList.add(mars);
        objectList.add(jupiter);
        objectList.add(saturn);
        objectList.add(titan);

    }

    public void updateSolarSystem(double TIME_STEP) {
        for(SpaceObject spaceObject : objectList) {
            spaceObject.updateForces(objectList);
            leapfrog.fullUpdate(spaceObject);
        }
    }

    public ArrayList<SpaceObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(ArrayList<SpaceObject> objectList) {
        this.objectList = objectList;
    }

}