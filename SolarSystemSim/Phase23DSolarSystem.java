import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;

//Scaled to 10E3

public class Phase23DSolarSystem extends Application {

    private static final int WIDTH=1400;
    private static final int HEIGHT=800;
    private int index1=0;
    private double scalingFactorOrbit=10E+6;
    private double gravitationalConstant = 6.67408E-11; //m^3/kg*s^2


    public void start(Stage primaryStage) {

        //Camera
       Camera camera= new PerspectiveCamera();
        camera.translateXProperty().set(-WIDTH/2);
        camera.translateYProperty().set(50000);
        camera.translateZProperty().set(-250000);
        camera.setFarClip(500000000);
        //Test ScaleX
        //Must scale Earth and Moon to 63 and 17 to see the change
        /*camera.translateXProperty().set(-3000);
        camera.translateYProperty().set(15000);
        camera.translateZProperty().set(-3000);
        camera.setFarClip(500000000);*/

        //Solar System
        BorderPane root= new BorderPane();
        //Sun
        Star sun = new Star(0D,0D,0D,0D,0D,0D, 1.989E30,695);
        Image suny= new Image("2k_sun.jpg");

        //Source: https://www.solarsystemscope.com/textures/
        PhongMaterial sunSkin= new PhongMaterial();
        sunSkin.setDiffuseMap(suny);
        sun.setMaterial(sunSkin);
        //Mercury. Add some sort of marker! It's too small.
        Planet mercury = new Planet(-2.105262111032039E+10, -6.640663808353403E+10, -3.492446023382954E+09,  3.665298706393840E+04, -1.228983810111077E+04, -4.368172898981951E+03, 3.301E+23,700);
        mercury.setTranslateX(-2.105262111032039E+3);
        mercury.setTranslateY(-6.640663808353403E+3);
        mercury.setTranslateZ(-3.492446023382954E+2);

        //Source: https://www.solarsystemscope.com/textures/
        Image mercuryy= new Image("2k_mercury.jpg");
        PhongMaterial mercurySkin= new PhongMaterial();
        mercurySkin.setDiffuseMap(mercuryy);
        mercury.setMaterial(mercurySkin);

        //Venus
        Planet venus = new Planet(-1.075055502695123E+11, -3.366520720591562E+09,  6.159219802771119E+09,  8.891598046362434E+02, -3.515920774124290E+04, -5.318594054684045E+02, 4.867E+24,700);
        venus.setTranslateX(-1.075055502695123E+4);
        venus.setTranslateY(-3.366520720591562E+2);
        venus.setTranslateZ(6.159219802771119E+2);

        //Source: https://www.solarsystemscope.com/textures/
        Image venusy= new Image("2k_venus_surface.jpg");
        PhongMaterial venusSkin= new PhongMaterial();
        venusSkin.setDiffuseMap(venusy);
        venus.setMaterial(venusSkin);

        //Earth
        Planet earth = new Planet(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+05, -2.983983333368269E+04, -5.207633918704476E+03,  6.169062303484907E-02, 5.972E+24, 64);
        earth.setTranslateX(-2.521092863852298E+3);
        earth.setTranslateY(1.449279195712076E+4);
        earth.setTranslateZ(-6.164888475164771E-02);

        Point3D earthAxis= new Point3D(1,0,1);
        earth.setRotationAxis(earthAxis);
        earth.setRotate(103);

        Image earthy= new Image("world.topo.200412.3x5400x2700.jpg");
        //Source: https://visibleearth.nasa.gov/view.php?id=73909
        PhongMaterial earthSkin= new PhongMaterial();
        earthSkin.setDiffuseMap(earthy);
        earth.setMaterial(earthSkin);

        //Moon
        Planet moon = new Planet(-2.552857888050620E+10,  1.446860363961675E+11,  3.593933517466486E+07, -2.927904627038706E+04, -6.007566180814270E+03, -1.577640655646029E00, 7.349E+22,17);
        moon.setTranslateX(-2.552857888050620E+3);
        moon.setTranslateY(1.446860363961675E+4);
        moon.setTranslateZ(3.593933517466486);

        Image moony= new Image("2k_moon.jpg");
        //Source: https://www.solarsystemscope.com/textures/
        PhongMaterial moonSkin= new PhongMaterial();
        moonSkin.setDiffuseMap(moony);
        moon.setMaterial(moonSkin);

        //Probe:PLACEHOLDER
        Planet probe= new Planet(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+05,100000,100000,100000,20000,600);
        earth.setTranslateX(-2.521092863852298E+3);
        earth.setTranslateY(1.449279195712076E+4);
        earth.setTranslateZ(-6.164888475164771E-02);

        //Mars
        Planet mars = new Planet(2.079950549908331E+11, -3.143009561106971E+09, -5.178781160069674E+09,  1.295003532851602E+03,  2.629442067068712E+04,  5.190097267545717E+02, 6.417E+23,700);
        mars.setTranslateX(2.079950549908331E+4);
        mars.setTranslateY(-3.143009561106971E+2);
        mars.setTranslateZ(-5.178781160069674E+2);

        //Source: https://www.solarsystemscope.com/textures/
        Image marsy= new Image("2k_mars.jpg");
        PhongMaterial marsSkin= new PhongMaterial();
        marsSkin.setDiffuseMap(marsy);
        mars.setMaterial(marsSkin);

        //Mars Moon: Phobos
        Planet phobos= new Planet(2.080001728304922E+11,-3.135629529907857E+09,-5.180946414782474E+09,-2.461795389080246E+02,2.758970765510455E+04,1.326588322007167E+03,1.06E+16,350);
        phobos.setTranslateX(2.080001728304922E+04);
        phobos.setTranslateY(-3.135629529907857E+02);
        phobos.setTranslateZ(-5.180946414782474E+02);

        //Source: https://nasa3d.arc.nasa.gov/images
        Image phobosy= new Image("mar1kuu2.jpg");
        PhongMaterial phobosSkin= new PhongMaterial();
        phobosSkin.setDiffuseMap(phobosy);
        phobos.setMaterial(phobosSkin);

        //Mars Moon: Demios
        Planet demios= new Planet( 2.079758459552988E+11,-3.134519020859756E+09 ,-5.168323663883276E+09, 8.310732201509531E+02,2.503674676157639E+04,  6.872885887768234E+02,2.4E+15,350);
        demios.setTranslateX(2.079758459552988E+04);
        demios.setTranslateY(-3.134519020859756E+02);
        demios.setTranslateZ(-5.168323663883276E+02);

        //Source: https://www.solarsystemscope.com/textures/
        Image demiosy= new Image("2k_ceres_fictional.jpg");
        PhongMaterial demiosSkin= new PhongMaterial();
        demiosSkin.setDiffuseMap(demiosy);
        demios.setMaterial(demiosSkin);

        //Jupiter
        Planet jupiter = new Planet(5.989091595026654E+11,  4.391225931434094E+11, -1.523254615467653E+10, -7.901937631606453E+03,  1.116317697592017E+04,  1.306729060953327E+02, 1.899E+27,700);
        jupiter.setTranslateX(5.989091595026654E+4);
        jupiter.setTranslateY(4.391225931434094E+4);
        jupiter.setTranslateZ(-1.523254615467653E+03);

        //Source: https://www.solarsystemscope.com/textures/
        Image jupitery= new Image("2k_jupiter.jpg");
        PhongMaterial jupiterSkin= new PhongMaterial();
        jupiterSkin.setDiffuseMap(jupitery);
        jupiter.setMaterial(jupiterSkin);

        //Jupiter Moon: Io
        Planet io= new Planet(5.989539518409647E+11,4.387023144998344E+11,-1.524679648427427E+10, 9.283361803632189E+03,1.303819812527103E+04,4.583205514926929E+02,8.932E+22,350);
        io.setTranslateX(5.989539518409647E+04);
        io.setTranslateY(4.387023144998344E+04);
        io.setTranslateZ(-1.524679648427427E+03);

        //Source: https://www.space.com/14977-jupiter-moon-io-global-map-photos.html
        Image ioy= new Image("noyPQQbBJyozEwVpULzUvM-970-80.jpg");
        PhongMaterial ioSkin= new PhongMaterial();
        ioSkin.setDiffuseMap(ioy);
        io.setMaterial(ioSkin);

        //Jupiter Moon: Europa
        Planet europa= new Planet(5.982770778300034E+11,4.393402643657560E+11,-1.523558910378432E+10,-1.227402322885824E+04,-1.912604058757177E+03,-2.952416114807850E+02,4.8E+22,350);
        europa.setTranslateX(5.982770778300034E+04);
        europa.setTranslateY(4.393402643657560E+04);
        europa.setTranslateZ(-1.523558910378432E+03);

        //Source: http://www.planetary.org/blogs/guest-blogs/2013/0305-brown-sea-salt.html
        Image europay = new Image("20130305_mikebrown01_europa_f537.jpg");
        PhongMaterial europaSkin= new PhongMaterial();
        europaSkin.setDiffuseMap(europay);
        europa.setMaterial(europaSkin);

        //Jupiter Moon: Ganymede
        Planet ganymede=new Planet(5.978734287587731E+11,4.388509469893177E+11,-1.525419290729403E+10,-5.123069954141830E+03,6.549868238873174E+02,-2.270989208416142E+02,1.4819E+23,350);
        ganymede.setTranslateX(5.978734287587731E+04);
        ganymede.setTranslateY(4.388509469893177E+04);
        ganymede.setTranslateZ(-1.525419290729403E+03);

        //Source: https://commons.wikimedia.org/wiki/File:Map_of_Ganymede_by_Bj%C3%B6rn_J%C3%B3nsson.jpg
        Image ganymedey= new Image("Map_of_Ganymede_by_Björn_Jónsson.jpg");
        PhongMaterial ganymedeSkin= new PhongMaterial();
        ganymedeSkin.setDiffuseMap(ganymedey);
        ganymede.setMaterial(ganymedeSkin);

        //Jupiter Moon: Callisto
        Planet callisto= new Planet(5.995751543436089E+11,4.408784590989536E+11,-1.516642941711968E+10,-1.556521929613705E+04,1.413266575371035E+04,1.262439229830568E+02,1.0759E+23,350);
        callisto.setTranslateX(5.995751543436089E+04);
        callisto.setTranslateY(4.408784590989536E+04);
        callisto.setTranslateZ(-1.516642941711968E+03);

        //Source: https://bjj.mmedia.is/data/callisto/index.html
        Image callistoy= new Image("callisto.jpg");
        PhongMaterial callistoSkin= new PhongMaterial();
        callistoSkin.setDiffuseMap(callistoy);
        callisto.setMaterial(callistoSkin);

        //Saturn
        Planet saturn = new Planet(9.587063371332250E+11,  9.825652108702583E+11, -5.522065686935234E+10, -7.428885681642827E+03,  6.738814233429374E+03,  1.776643556375199E+02, 5.685E+26,700);
        saturn.setTranslateX(9.587063371332250E+4);
        saturn.setTranslateY(9.825652108702583E+4);
        saturn.setTranslateZ(-5.522065686935234E+3);

        //Source: https://www.solarsystemscope.com/textures/
        Image saturny= new Image("2k_saturn.jpg");
        PhongMaterial saturnSkin= new PhongMaterial();
        saturnSkin.setDiffuseMap(saturny);
        saturn.setMaterial(saturnSkin);

        //Saturn Moon:Titan
        Planet titan = new Planet( 9.579293831681823E+11,  9.834677749678675E+11, -5.561032276665545E+10, -1.170810292990584E+04,  3.955109586303735E+03,  2.034356139087789E+03, 13455.3E+19,350);
        titan.setTranslateX(9.579293831681823E+4);
        titan.setTranslateY(9.834677749678675E+4);
        titan.setTranslateZ(-5.561032276665545E+3);

        //Source: https://nasa3d.arc.nasa.gov/images
        Image titany= new Image("sat6fss1.jpg");
        PhongMaterial titanSkin= new PhongMaterial();
        titanSkin.setDiffuseMap(titany);
        titan.setMaterial(titanSkin);

        //Saturn Moon: Mimas
        Planet mimas= new Planet(9.585933797467707E+11,9.827031937056004E+11,-5.528229528100616E+10,-1.858271656433577E+04,-5.864309674953683E+02,4.659784882381858E+03,3.73E+19,350);
        mimas.setTranslateX(9.585933797467707E+04);
        mimas.setTranslateY(9.827031937056004E+04);
        mimas.setTranslateZ(-5.528229528100616E+03);

        //Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18437
        Image mimasy= new Image("PIA18437_hires.jpg");
        PhongMaterial mimasSkin= new PhongMaterial();
        mimasSkin.setDiffuseMap(mimasy);
        mimas.setMaterial(mimasSkin);
        
        //Saturn Moon: Enceladus
        Planet enceladus= new Planet(9.584686368989240E+11,9.825698015650455E+11,-5.519999340655005E+10,-7.087891656970907E+03,-4.432543978580975E+03,5.997973410718807E+03,1.076E+20,350);
        enceladus.setTranslateX(9.584686368989240E+04);
        enceladus.setTranslateY(9.825698015650455E+04);
        enceladus.setTranslateZ(-5.519999340655005E+03);

        //Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18435
        Image enceladusy= new Image("PIA18435_hires.jpg");
        PhongMaterial enceladusSkin= new PhongMaterial();
        enceladusSkin.setDiffuseMap(enceladusy);
        enceladus.setMaterial(enceladusSkin);

        //Saturn Moon: Tethys
        Planet tethys= new Planet(9.584896767756197E+11,9.823969914493581E+11,-5.511282890265793E+10,1.884353823857955E+02,-1.075740574740226E+03,3.294335699239238E+03,6.130E+20,350);
        tethys.setTranslateX(9.584896767756197E+04);
        tethys.setTranslateY(9.823969914493581E+04);
        tethys.setTranslateZ(-5.511282890265793E+03);

        //Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18439
        Image tethysy= new Image("PIA18439_hires.jpg");
        PhongMaterial tethysSkin= new PhongMaterial();
        tethysSkin.setDiffuseMap(tethysy);
        tethys.setMaterial(tethysSkin);

        //Saturn Moon: Dione
        Planet dione= new Planet(9.585276353579862E+11, 9.822780179748093E+11,-5.505286854253536E+10,1.370108959468099E+03,2.186732770372938E+03,1.704947776738479E+03,1.097E+21,350);
        dione.setTranslateX(9.585276353579862E+04);
        dione.setTranslateY(9.822780179748093E+04);
        dione.setTranslateZ(-5.505286854253536E+03);

        //Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18434
        Image dioney= new Image("PIA18434_hires.jpg");
        PhongMaterial dioneSkin= new PhongMaterial();
        dioneSkin.setDiffuseMap(dioney);
        dione.setMaterial(dioneSkin);


        //Saturn Moon: Rhea
        Planet rhea= new Planet(9.582858745650558E+11,9.828629109039947E+11,-5.533445794600141E+10,-1.249378043406604E+04,9.548491287020374E+02,3.746988655327530E+03,2.29E+21,350);
        rhea.setTranslateX(9.582858745650558E+04);
        rhea.setTranslateY(9.828629109039947E+04);
        rhea.setTranslateZ(-5.533445794600141E+03);

        //Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18438
        Image rheay= new Image("PIA18434_hires.jpg");
        PhongMaterial rheaSkin= new PhongMaterial();
        rheaSkin.setDiffuseMap(rheay);
        rhea.setMaterial(rheaSkin);

        //Saturn Moon: Phoebe
        Planet phoebe= new Planet(9.470030184077681E+11,9.797584943042043E+11,-5.388629873572934E+10,-8.111087864852331E+03,8.429736036831875E+03,2.813327671729886E+02,8.3E+18,350);
        phoebe.setTranslateX(9.470030184077681E+04);
        phoebe.setTranslateY(9.797584943042043E+04);
        phoebe.setTranslateZ(-5.388629873572934E+03);

        //Source:https://www.solarsystemscope.com/textures/
        Image phoeby= new Image("2k_makemake_fictional.jpg");
        PhongMaterial phoebeSkin= new PhongMaterial();
        phoebeSkin.setDiffuseMap(phoeby);
        phoebe.setMaterial(phoebeSkin);

        //Add Probe


        //Adding Background Image
        Group planets= new Group();
        planets.getChildren().add(sun);
        planets.getChildren().add(mercury);
        planets.getChildren().add(venus);
        planets.getChildren().add(earth);
        planets.getChildren().add(moon);
        planets.getChildren().add(mars);
        planets.getChildren().add(jupiter);
        planets.getChildren().add(saturn);
        planets.getChildren().add(titan);
        planets.getChildren().add(phobos);
        planets.getChildren().add(demios);
        planets.getChildren().add(io);
        planets.getChildren().add(europa);
        planets.getChildren().add(ganymede);
        planets.getChildren().add(callisto);
        planets.getChildren().add(mimas);
        planets.getChildren().add(enceladus);
        planets.getChildren().add(tethys);
        planets.getChildren().add(dione);
        planets.getChildren().add(rhea);
        planets.getChildren().add(phoebe);
        planets.getChildren().add(probe);
        SubScene solarSystem=new SubScene(planets,WIDTH,HEIGHT);
        solarSystem.setCamera(camera);
        solarSystem.setFill(Color.BLACK);
        root.setCenter(solarSystem);

        //Creating a list of celestial bodies
        ArrayList<SpaceObject> listOfObjects = new ArrayList<SpaceObject>();
        listOfObjects.add(sun);
        listOfObjects.add(mercury);
        listOfObjects.add(venus);
        listOfObjects.add(earth);
        listOfObjects.add(mars);
        listOfObjects.add(jupiter);
        listOfObjects.add(saturn);
        listOfObjects.add(moon);
        listOfObjects.add(titan);
        listOfObjects.add(phobos);
        listOfObjects.add(demios);
        listOfObjects.add(io);
        listOfObjects.add(europa);
        listOfObjects.add(ganymede);
        listOfObjects.add(callisto);
        listOfObjects.add(mimas);
        listOfObjects.add(enceladus);
        listOfObjects.add(tethys);
        listOfObjects.add(dione);
        listOfObjects.add(rhea);
        listOfObjects.add(phoebe);


        //Creating the scene
        Scene scene=new Scene(root,WIDTH,HEIGHT);
        primaryStage.setTitle("Mission to Titan");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Mathematical Model
        final double YEAR_TO_SECONDS = 90*86400;
        final double TIME_STEP=1;
        List<Vector> mercuryPos= new ArrayList<Vector>();
        List<Vector> venusPos=  new ArrayList<Vector>();
        List<Vector> earthPos=  new ArrayList<Vector>();
        List<Vector> marsPos= new ArrayList<Vector>();
        List<Vector> jupiterPos= new ArrayList<Vector>();
        List<Vector> saturnPos= new ArrayList<Vector>();
        List<Vector> moonPos= new ArrayList<Vector>();
        List<Vector> titanPos= new ArrayList<Vector>();
        List<Vector> phobosPos= new ArrayList<Vector>();
        List<Vector> demiosPos= new ArrayList<Vector>();
        List<Vector> ioPos= new ArrayList<Vector>();
        List<Vector> europaPos= new ArrayList<Vector>();
        List<Vector> ganymedePos= new ArrayList<Vector>();
        List<Vector> callistoPos=new ArrayList<Vector>();
        List<Vector> mimasPos= new ArrayList<Vector>();
        List<Vector> enceladusPos= new ArrayList<Vector>();
        List<Vector> tethysPos= new ArrayList<Vector>();
        List<Vector> dionePos= new ArrayList<Vector>();
        List<Vector> rheaPos= new ArrayList<Vector>();
        List<Vector> phoebePos= new ArrayList<Vector>();

        for (int i = 0; i<YEAR_TO_SECONDS; i++) {
            for (SpaceObject spaceObject : listOfObjects) {
                spaceObject.updateForce(listOfObjects);
                spaceObject.updateAcceleration();
                spaceObject.updateVelocity(TIME_STEP);
                spaceObject.updatePosition(TIME_STEP);

            }
            if(i%100000==0){
                mercuryPos.add(listOfObjects.get(1).getPosition());
                venusPos.add(listOfObjects.get(2).getPosition());
                earthPos.add(listOfObjects.get(3).getPosition());
                marsPos.add(listOfObjects.get(4).getPosition());
                jupiterPos.add(listOfObjects.get(5).getPosition());
                saturnPos.add(listOfObjects.get(6).getPosition());
                moonPos.add(listOfObjects.get(7).getPosition());
                titanPos.add(listOfObjects.get(8).getPosition());
                phobosPos.add(listOfObjects.get(9).getPosition());
                demiosPos.add(listOfObjects.get(10).getPosition());
                ioPos.add(listOfObjects.get(11).getPosition());
                europaPos.add(listOfObjects.get(12).getPosition());
                ganymedePos.add(listOfObjects.get(13).getPosition());
                callistoPos.add(listOfObjects.get(14).getPosition());
                mimasPos.add(listOfObjects.get(15).getPosition());
                enceladusPos.add(listOfObjects.get(16).getPosition());
                tethysPos.add(listOfObjects.get(17).getPosition());
                dionePos.add(listOfObjects.get(18).getPosition());
                rheaPos.add(listOfObjects.get(19).getPosition());
                phoebePos.add(listOfObjects.get(20).getPosition());
            }
        }

        //User Controls
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,event2 ->{
            switch(event2.getCode()){
                case A:
                    if(index1<mercuryPos.size()-1 && index1>0) {
                        mercury.translateXProperty().set(mercuryPos.get(index1).getX()/(scalingFactorOrbit));
                        mercury.translateYProperty().set(mercuryPos.get(index1).getY()/(scalingFactorOrbit));
                        mercury.translateZProperty().set(mercuryPos.get(index1).getZ()/(scalingFactorOrbit));
                        venus.translateXProperty().set(venusPos.get(index1).getX()/(scalingFactorOrbit));
                        venus.translateYProperty().set(venusPos.get(index1).getY()/(scalingFactorOrbit));
                        venus.translateZProperty().set(venusPos.get(index1).getZ()/(scalingFactorOrbit));
                        earth.translateXProperty().set(earthPos.get(index1).getX()/(scalingFactorOrbit));
                        earth.translateYProperty().set(earthPos.get(index1).getY()/(scalingFactorOrbit));
                        earth.translateZProperty().set(earthPos.get(index1).getZ()/(scalingFactorOrbit));
                        mars.translateXProperty().set(marsPos.get(index1).getX()/(scalingFactorOrbit));
                        mars.translateYProperty().set(marsPos.get(index1).getY()/(scalingFactorOrbit));
                        mars.translateZProperty().set(marsPos.get(index1).getZ()/(scalingFactorOrbit));
                        jupiter.translateXProperty().set(jupiterPos.get(index1).getX()/(scalingFactorOrbit));
                        jupiter.translateYProperty().set(jupiterPos.get(index1).getY()/(scalingFactorOrbit));
                        jupiter.translateZProperty().set(jupiterPos.get(index1).getZ()/(scalingFactorOrbit));
                        saturn.translateXProperty().set(saturnPos.get(index1).getX()/(scalingFactorOrbit));
                        saturn.translateYProperty().set(saturnPos.get(index1).getY()/(scalingFactorOrbit));
                        saturn.translateZProperty().set(saturnPos.get(index1).getZ()/(scalingFactorOrbit));
                        moon.translateXProperty().set(moonPos.get(index1).getX()/(scalingFactorOrbit));
                        moon.translateYProperty().set(moonPos.get(index1).getY()/(scalingFactorOrbit));
                        moon.translateZProperty().set(moonPos.get(index1).getZ()/(scalingFactorOrbit));
                        titan.translateXProperty().set(titanPos.get(index1).getX()/(scalingFactorOrbit));
                        titan.translateYProperty().set(titanPos.get(index1).getY()/(scalingFactorOrbit));
                        titan.translateZProperty().set(titanPos.get(index1).getZ()/(scalingFactorOrbit));
                        phobos.translateXProperty().set(phobosPos.get(index1).getX()/(scalingFactorOrbit));
                        phobos.translateYProperty().set(phobosPos.get(index1).getY()/(scalingFactorOrbit));
                        phobos.translateZProperty().set(phobosPos.get(index1).getZ()/(scalingFactorOrbit));
                        demios.translateXProperty().set(demiosPos.get(index1).getX()/(scalingFactorOrbit));
                        demios.translateYProperty().set(demiosPos.get(index1).getY()/(scalingFactorOrbit));
                        demios.translateZProperty().set(demiosPos.get(index1).getZ()/(scalingFactorOrbit));
                        io.translateXProperty().set(ioPos.get(index1).getX()/(scalingFactorOrbit));
                        io.translateYProperty().set(ioPos.get(index1).getY()/(scalingFactorOrbit));
                        io.translateZProperty().set(ioPos.get(index1).getZ()/(scalingFactorOrbit));
                        europa.translateXProperty().set(europaPos.get(index1).getX()/(scalingFactorOrbit));
                        europa.translateYProperty().set(europaPos.get(index1).getY()/(scalingFactorOrbit));
                        europa.translateZProperty().set(europaPos.get(index1).getZ()/(scalingFactorOrbit));
                        ganymede.translateXProperty().set(ganymedePos.get(index1).getX()/(scalingFactorOrbit));
                        ganymede.translateYProperty().set(ganymedePos.get(index1).getY()/(scalingFactorOrbit));
                        ganymede.translateZProperty().set(ganymedePos.get(index1).getZ()/(scalingFactorOrbit));
                        callisto.translateXProperty().set(callistoPos.get(index1).getX()/(scalingFactorOrbit));
                        callisto.translateYProperty().set(callistoPos.get(index1).getY()/(scalingFactorOrbit));
                        callisto.translateZProperty().set(callistoPos.get(index1).getZ()/(scalingFactorOrbit));
                        mimas.translateXProperty().set(mimasPos.get(index1).getX()/(scalingFactorOrbit));
                        mimas.translateYProperty().set(mimasPos.get(index1).getY()/(scalingFactorOrbit));
                        mimas.translateZProperty().set(mimasPos.get(index1).getZ()/(scalingFactorOrbit));
                        enceladus.translateXProperty().set(enceladusPos.get(index1).getX()/(scalingFactorOrbit));
                        enceladus.translateYProperty().set(enceladusPos.get(index1).getY()/(scalingFactorOrbit));
                        enceladus.translateZProperty().set(enceladusPos.get(index1).getZ()/(scalingFactorOrbit));
                        tethys.translateXProperty().set(tethysPos.get(index1).getX()/(scalingFactorOrbit));
                        tethys.translateYProperty().set(tethysPos.get(index1).getY()/(scalingFactorOrbit));
                        tethys.translateZProperty().set(tethysPos.get(index1).getZ()/(scalingFactorOrbit));
                        dione.translateXProperty().set(dionePos.get(index1).getX()/(scalingFactorOrbit));
                        dione.translateYProperty().set(dionePos.get(index1).getY()/(scalingFactorOrbit));
                        dione.translateZProperty().set(dionePos.get(index1).getZ()/(scalingFactorOrbit));
                        rhea.translateXProperty().set(rheaPos.get(index1).getX()/(scalingFactorOrbit));
                        rhea.translateYProperty().set(rheaPos.get(index1).getY()/(scalingFactorOrbit));
                        rhea.translateZProperty().set(rheaPos.get(index1).getZ()/(scalingFactorOrbit));
                        phoebe.translateXProperty().set(phoebePos.get(index1).getX()/(scalingFactorOrbit));
                        phoebe.translateYProperty().set(phoebePos.get(index1).getY()/(scalingFactorOrbit));
                        phoebe.translateZProperty().set(phoebePos.get(index1).getZ()/(scalingFactorOrbit));
                        index1++;
                    }else if(index1==0){
                        mercury.translateXProperty().set(mercuryPos.get(index1).getX()/(scalingFactorOrbit));
                        mercury.translateYProperty().set(mercuryPos.get(index1).getY()/(scalingFactorOrbit));
                        mercury.translateZProperty().set(mercuryPos.get(index1).getZ()/(scalingFactorOrbit));
                        venus.translateXProperty().set(venusPos.get(index1).getX()/(scalingFactorOrbit));
                        venus.translateYProperty().set(venusPos.get(index1).getY()/(scalingFactorOrbit));
                        venus.translateZProperty().set(venusPos.get(index1).getZ()/(scalingFactorOrbit));
                        earth.translateXProperty().set(earthPos.get(index1).getX()/(scalingFactorOrbit));
                        earth.translateYProperty().set(earthPos.get(index1).getY()/(scalingFactorOrbit));
                        earth.translateZProperty().set(earthPos.get(index1).getZ()/(scalingFactorOrbit));
                        mars.translateXProperty().set(marsPos.get(index1).getX()/(scalingFactorOrbit));
                        mars.translateYProperty().set(marsPos.get(index1).getY()/(scalingFactorOrbit));
                        mars.translateZProperty().set(marsPos.get(index1).getZ()/(scalingFactorOrbit));
                        jupiter.translateXProperty().set(jupiterPos.get(index1).getX()/(scalingFactorOrbit));
                        jupiter.translateYProperty().set(jupiterPos.get(index1).getY()/(scalingFactorOrbit));
                        jupiter.translateZProperty().set(jupiterPos.get(index1).getZ()/(scalingFactorOrbit));
                        saturn.translateXProperty().set(saturnPos.get(index1).getX()/(scalingFactorOrbit));
                        saturn.translateYProperty().set(saturnPos.get(index1).getY()/(scalingFactorOrbit));
                        saturn.translateZProperty().set(saturnPos.get(index1).getZ()/(scalingFactorOrbit));
                        moon.translateXProperty().set(moonPos.get(index1).getX()/(scalingFactorOrbit));
                        moon.translateYProperty().set(moonPos.get(index1).getY()/(scalingFactorOrbit));
                        moon.translateZProperty().set(moonPos.get(index1).getZ()/(scalingFactorOrbit));
                        titan.translateXProperty().set(titanPos.get(index1).getX()/(scalingFactorOrbit));
                        titan.translateYProperty().set(titanPos.get(index1).getY()/(scalingFactorOrbit));
                        titan.translateZProperty().set(titanPos.get(index1).getZ()/(scalingFactorOrbit));
                        phobos.translateXProperty().set(phobosPos.get(index1).getX()/(scalingFactorOrbit));
                        phobos.translateYProperty().set(phobosPos.get(index1).getY()/(scalingFactorOrbit));
                        phobos.translateZProperty().set(phobosPos.get(index1).getZ()/(scalingFactorOrbit));
                        demios.translateXProperty().set(demiosPos.get(index1).getX()/(scalingFactorOrbit));
                        demios.translateYProperty().set(demiosPos.get(index1).getY()/(scalingFactorOrbit));
                        demios.translateZProperty().set(demiosPos.get(index1).getZ()/(scalingFactorOrbit));
                        io.translateXProperty().set(ioPos.get(index1).getX()/(scalingFactorOrbit));
                        io.translateYProperty().set(ioPos.get(index1).getY()/(scalingFactorOrbit));
                        io.translateZProperty().set(ioPos.get(index1).getZ()/(scalingFactorOrbit));
                        europa.translateXProperty().set(europaPos.get(index1).getX()/(scalingFactorOrbit));
                        europa.translateYProperty().set(europaPos.get(index1).getY()/(scalingFactorOrbit));
                        europa.translateZProperty().set(europaPos.get(index1).getZ()/(scalingFactorOrbit));
                        ganymede.translateXProperty().set(ganymedePos.get(index1).getX()/(scalingFactorOrbit));
                        ganymede.translateYProperty().set(ganymedePos.get(index1).getY()/(scalingFactorOrbit));
                        ganymede.translateZProperty().set(ganymedePos.get(index1).getZ()/(scalingFactorOrbit));
                        callisto.translateXProperty().set(callistoPos.get(index1).getX()/(scalingFactorOrbit));
                        callisto.translateYProperty().set(callistoPos.get(index1).getY()/(scalingFactorOrbit));
                        callisto.translateZProperty().set(callistoPos.get(index1).getZ()/(scalingFactorOrbit));
                        mimas.translateXProperty().set(mimasPos.get(index1).getX()/(scalingFactorOrbit));
                        mimas.translateYProperty().set(mimasPos.get(index1).getY()/(scalingFactorOrbit));
                        mimas.translateZProperty().set(mimasPos.get(index1).getZ()/(scalingFactorOrbit));
                        enceladus.translateXProperty().set(enceladusPos.get(index1).getX()/(scalingFactorOrbit));
                        enceladus.translateYProperty().set(enceladusPos.get(index1).getY()/(scalingFactorOrbit));
                        enceladus.translateZProperty().set(enceladusPos.get(index1).getZ()/(scalingFactorOrbit));
                        tethys.translateXProperty().set(tethysPos.get(index1).getX()/(scalingFactorOrbit));
                        tethys.translateYProperty().set(tethysPos.get(index1).getY()/(scalingFactorOrbit));
                        tethys.translateZProperty().set(tethysPos.get(index1).getZ()/(scalingFactorOrbit));
                        dione.translateXProperty().set(dionePos.get(index1).getX()/(scalingFactorOrbit));
                        dione.translateYProperty().set(dionePos.get(index1).getY()/(scalingFactorOrbit));
                        dione.translateZProperty().set(dionePos.get(index1).getZ()/(scalingFactorOrbit));
                        rhea.translateXProperty().set(rheaPos.get(index1).getX()/(scalingFactorOrbit));
                        rhea.translateYProperty().set(rheaPos.get(index1).getY()/(scalingFactorOrbit));
                        rhea.translateZProperty().set(rheaPos.get(index1).getZ()/(scalingFactorOrbit));
                        phoebe.translateXProperty().set(phoebePos.get(index1).getX()/(scalingFactorOrbit));
                        phoebe.translateYProperty().set(phoebePos.get(index1).getY()/(scalingFactorOrbit));
                        phoebe.translateZProperty().set(phoebePos.get(index1).getZ()/(scalingFactorOrbit));
                        index1++;
                    }else{}
                    break;
                case D:
                    if(index1>0){
                        mercury.translateXProperty().set(mercuryPos.get(index1).getX()/(scalingFactorOrbit));
                        mercury.translateYProperty().set(mercuryPos.get(index1).getY()/(scalingFactorOrbit));
                        mercury.translateZProperty().set(mercuryPos.get(index1).getZ()/(scalingFactorOrbit));
                        venus.translateXProperty().set(venusPos.get(index1).getX()/(scalingFactorOrbit));
                        venus.translateYProperty().set(venusPos.get(index1).getY()/(scalingFactorOrbit));
                        venus.translateZProperty().set(venusPos.get(index1).getZ()/(scalingFactorOrbit));
                        earth.translateXProperty().set(earthPos.get(index1).getX()/(scalingFactorOrbit));
                        earth.translateYProperty().set(earthPos.get(index1).getY()/(scalingFactorOrbit));
                        earth.translateZProperty().set(earthPos.get(index1).getZ()/(scalingFactorOrbit));
                        mars.translateXProperty().set(marsPos.get(index1).getX()/(scalingFactorOrbit));
                        mars.translateYProperty().set(marsPos.get(index1).getY()/(scalingFactorOrbit));
                        mars.translateZProperty().set(marsPos.get(index1).getZ()/(scalingFactorOrbit));
                        jupiter.translateXProperty().set(jupiterPos.get(index1).getX()/(scalingFactorOrbit));
                        jupiter.translateYProperty().set(jupiterPos.get(index1).getY()/(scalingFactorOrbit));
                        jupiter.translateZProperty().set(jupiterPos.get(index1).getZ()/(scalingFactorOrbit));
                        saturn.translateXProperty().set(saturnPos.get(index1).getX()/(scalingFactorOrbit));
                        saturn.translateYProperty().set(saturnPos.get(index1).getY()/(scalingFactorOrbit));
                        saturn.translateZProperty().set(saturnPos.get(index1).getZ()/(scalingFactorOrbit));
                        moon.translateXProperty().set(moonPos.get(index1).getX()/(scalingFactorOrbit));
                        moon.translateYProperty().set(moonPos.get(index1).getY()/(scalingFactorOrbit));
                        moon.translateZProperty().set(moonPos.get(index1).getZ()/(scalingFactorOrbit));
                        titan.translateXProperty().set(titanPos.get(index1).getX()/(scalingFactorOrbit));
                        titan.translateYProperty().set(titanPos.get(index1).getY()/(scalingFactorOrbit));
                        titan.translateZProperty().set(titanPos.get(index1).getZ()/(scalingFactorOrbit));
                        phobos.translateXProperty().set(phobosPos.get(index1).getX()/(scalingFactorOrbit));
                        phobos.translateYProperty().set(phobosPos.get(index1).getY()/(scalingFactorOrbit));
                        phobos.translateZProperty().set(phobosPos.get(index1).getZ()/(scalingFactorOrbit));
                        demios.translateXProperty().set(demiosPos.get(index1).getX()/(scalingFactorOrbit));
                        demios.translateYProperty().set(demiosPos.get(index1).getY()/(scalingFactorOrbit));
                        demios.translateZProperty().set(demiosPos.get(index1).getZ()/(scalingFactorOrbit));
                        io.translateXProperty().set(ioPos.get(index1).getX()/(scalingFactorOrbit));
                        io.translateYProperty().set(ioPos.get(index1).getY()/(scalingFactorOrbit));
                        io.translateZProperty().set(ioPos.get(index1).getZ()/(scalingFactorOrbit));
                        europa.translateXProperty().set(europaPos.get(index1).getX()/(scalingFactorOrbit));
                        europa.translateYProperty().set(europaPos.get(index1).getY()/(scalingFactorOrbit));
                        europa.translateZProperty().set(europaPos.get(index1).getZ()/(scalingFactorOrbit));
                        ganymede.translateXProperty().set(ganymedePos.get(index1).getX()/(scalingFactorOrbit));
                        ganymede.translateYProperty().set(ganymedePos.get(index1).getY()/(scalingFactorOrbit));
                        ganymede.translateZProperty().set(ganymedePos.get(index1).getZ()/(scalingFactorOrbit));
                        callisto.translateXProperty().set(callistoPos.get(index1).getX()/(scalingFactorOrbit));
                        callisto.translateYProperty().set(callistoPos.get(index1).getY()/(scalingFactorOrbit));
                        callisto.translateZProperty().set(callistoPos.get(index1).getZ()/(scalingFactorOrbit));
                        mimas.translateXProperty().set(mimasPos.get(index1).getX()/(scalingFactorOrbit));
                        mimas.translateYProperty().set(mimasPos.get(index1).getY()/(scalingFactorOrbit));
                        mimas.translateZProperty().set(mimasPos.get(index1).getZ()/(scalingFactorOrbit));
                        enceladus.translateXProperty().set(enceladusPos.get(index1).getX()/(scalingFactorOrbit));
                        enceladus.translateYProperty().set(enceladusPos.get(index1).getY()/(scalingFactorOrbit));
                        enceladus.translateZProperty().set(enceladusPos.get(index1).getZ()/(scalingFactorOrbit));
                        tethys.translateXProperty().set(tethysPos.get(index1).getX()/(scalingFactorOrbit));
                        tethys.translateYProperty().set(tethysPos.get(index1).getY()/(scalingFactorOrbit));
                        tethys.translateZProperty().set(tethysPos.get(index1).getZ()/(scalingFactorOrbit));
                        dione.translateXProperty().set(dionePos.get(index1).getX()/(scalingFactorOrbit));
                        dione.translateYProperty().set(dionePos.get(index1).getY()/(scalingFactorOrbit));
                        dione.translateZProperty().set(dionePos.get(index1).getZ()/(scalingFactorOrbit));
                        rhea.translateXProperty().set(rheaPos.get(index1).getX()/(scalingFactorOrbit));
                        rhea.translateYProperty().set(rheaPos.get(index1).getY()/(scalingFactorOrbit));
                        rhea.translateZProperty().set(rheaPos.get(index1).getZ()/(scalingFactorOrbit));
                        phoebe.translateXProperty().set(phoebePos.get(index1).getX()/(scalingFactorOrbit));
                        phoebe.translateYProperty().set(phoebePos.get(index1).getY()/(scalingFactorOrbit));
                        phoebe.translateZProperty().set(phoebePos.get(index1).getZ()/(scalingFactorOrbit));
                        index1--;
                    }else if(index1==0){
                        mercury.translateXProperty().set(mercuryPos.get(index1).getX()/(scalingFactorOrbit));
                        mercury.translateYProperty().set(mercuryPos.get(index1).getY()/(scalingFactorOrbit));
                        mercury.translateZProperty().set(mercuryPos.get(index1).getZ()/(scalingFactorOrbit));
                        venus.translateXProperty().set(venusPos.get(index1).getX()/(scalingFactorOrbit));
                        venus.translateYProperty().set(venusPos.get(index1).getY()/(scalingFactorOrbit));
                        venus.translateZProperty().set(venusPos.get(index1).getZ()/(scalingFactorOrbit));
                        earth.translateXProperty().set(earthPos.get(index1).getX()/(scalingFactorOrbit));
                        earth.translateYProperty().set(earthPos.get(index1).getY()/(scalingFactorOrbit));
                        earth.translateZProperty().set(earthPos.get(index1).getZ()/(scalingFactorOrbit));
                        mars.translateXProperty().set(marsPos.get(index1).getX()/(scalingFactorOrbit));
                        mars.translateYProperty().set(marsPos.get(index1).getY()/(scalingFactorOrbit));
                        mars.translateZProperty().set(marsPos.get(index1).getZ()/(scalingFactorOrbit));
                        jupiter.translateXProperty().set(jupiterPos.get(index1).getX()/(scalingFactorOrbit));
                        jupiter.translateYProperty().set(jupiterPos.get(index1).getY()/(scalingFactorOrbit));
                        jupiter.translateZProperty().set(jupiterPos.get(index1).getZ()/(scalingFactorOrbit));
                        saturn.translateXProperty().set(saturnPos.get(index1).getX()/(scalingFactorOrbit));
                        saturn.translateYProperty().set(saturnPos.get(index1).getY()/(scalingFactorOrbit));
                        saturn.translateZProperty().set(saturnPos.get(index1).getZ()/(scalingFactorOrbit));
                        moon.translateXProperty().set(moonPos.get(index1).getX()/(scalingFactorOrbit));
                        moon.translateYProperty().set(moonPos.get(index1).getY()/(scalingFactorOrbit));
                        moon.translateZProperty().set(moonPos.get(index1).getZ()/(scalingFactorOrbit));
                        titan.translateXProperty().set(titanPos.get(index1).getX()/(scalingFactorOrbit));
                        titan.translateYProperty().set(titanPos.get(index1).getY()/(scalingFactorOrbit));
                        titan.translateZProperty().set(titanPos.get(index1).getZ()/(scalingFactorOrbit));
                        phobos.translateXProperty().set(phobosPos.get(index1).getX()/(scalingFactorOrbit));
                        phobos.translateYProperty().set(phobosPos.get(index1).getY()/(scalingFactorOrbit));
                        phobos.translateZProperty().set(phobosPos.get(index1).getZ()/(scalingFactorOrbit));
                        demios.translateXProperty().set(demiosPos.get(index1).getX()/(scalingFactorOrbit));
                        demios.translateYProperty().set(demiosPos.get(index1).getY()/(scalingFactorOrbit));
                        demios.translateZProperty().set(demiosPos.get(index1).getZ()/(scalingFactorOrbit));
                        io.translateXProperty().set(ioPos.get(index1).getX()/(scalingFactorOrbit));
                        io.translateYProperty().set(ioPos.get(index1).getY()/(scalingFactorOrbit));
                        io.translateZProperty().set(ioPos.get(index1).getZ()/(scalingFactorOrbit));
                        europa.translateXProperty().set(europaPos.get(index1).getX()/(scalingFactorOrbit));
                        europa.translateYProperty().set(europaPos.get(index1).getY()/(scalingFactorOrbit));
                        europa.translateZProperty().set(europaPos.get(index1).getZ()/(scalingFactorOrbit));
                        ganymede.translateXProperty().set(ganymedePos.get(index1).getX()/(scalingFactorOrbit));
                        ganymede.translateYProperty().set(ganymedePos.get(index1).getY()/(scalingFactorOrbit));
                        ganymede.translateZProperty().set(ganymedePos.get(index1).getZ()/(scalingFactorOrbit));
                        callisto.translateXProperty().set(callistoPos.get(index1).getX()/(scalingFactorOrbit));
                        callisto.translateYProperty().set(callistoPos.get(index1).getY()/(scalingFactorOrbit));
                        callisto.translateZProperty().set(callistoPos.get(index1).getZ()/(scalingFactorOrbit));
                        mimas.translateXProperty().set(mimasPos.get(index1).getX()/(scalingFactorOrbit));
                        mimas.translateYProperty().set(mimasPos.get(index1).getY()/(scalingFactorOrbit));
                        mimas.translateZProperty().set(mimasPos.get(index1).getZ()/(scalingFactorOrbit));
                        enceladus.translateXProperty().set(enceladusPos.get(index1).getX()/(scalingFactorOrbit));
                        enceladus.translateYProperty().set(enceladusPos.get(index1).getY()/(scalingFactorOrbit));
                        enceladus.translateZProperty().set(enceladusPos.get(index1).getZ()/(scalingFactorOrbit));
                        tethys.translateXProperty().set(tethysPos.get(index1).getX()/(scalingFactorOrbit));
                        tethys.translateYProperty().set(tethysPos.get(index1).getY()/(scalingFactorOrbit));
                        tethys.translateZProperty().set(tethysPos.get(index1).getZ()/(scalingFactorOrbit));
                        dione.translateXProperty().set(dionePos.get(index1).getX()/(scalingFactorOrbit));
                        dione.translateYProperty().set(dionePos.get(index1).getY()/(scalingFactorOrbit));
                        dione.translateZProperty().set(dionePos.get(index1).getZ()/(scalingFactorOrbit));
                        rhea.translateXProperty().set(rheaPos.get(index1).getX()/(scalingFactorOrbit));
                        rhea.translateYProperty().set(rheaPos.get(index1).getY()/(scalingFactorOrbit));
                        rhea.translateZProperty().set(rheaPos.get(index1).getZ()/(scalingFactorOrbit));
                        phoebe.translateXProperty().set(phoebePos.get(index1).getX()/(scalingFactorOrbit));
                        phoebe.translateYProperty().set(phoebePos.get(index1).getY()/(scalingFactorOrbit));
                        phoebe.translateZProperty().set(phoebePos.get(index1).getZ()/(scalingFactorOrbit));

                    }
                    break;
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
