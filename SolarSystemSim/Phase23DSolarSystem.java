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
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Paint;
import javafx.scene.PointLight;


//Scaled to 10E3

public class Phase23DSolarSystem extends Application {

    private static final int WIDTH=1400;
    private static final int HEIGHT=800;
    private int index1=0;//Index of position array for the planets and probe. 
    private final double SCALING_FACTOR_ORBIT=10E+6;//Scales the calculation of orbital position for purpose of visualization.
    private final Point3D cameraAxis= new Point3D(1,0,0);


    public void start(Stage primaryStage) {

        //Camera
        Camera camera= new PerspectiveCamera();
        //BirdsEye View
        /*camera.translateXProperty().set(-WIDTH/2);
        camera.translateYProperty().set(50000);
        camera.translateZProperty().set(-250000);
        camera.setFarClip(500000000);*/

        //Settings for the camera to view the probe
        camera.translateXProperty().set(0);
        camera.translateYProperty().set(1800);
        camera.translateZProperty().set(-3000);
        camera.setRotationAxis(cameraAxis);
        camera.setRotate(84);

        BorderPane root= new BorderPane();


        //Sun
        //Mathematical Sun
        SpaceObject sun = new SpaceObject(0D,0D,0D,0D,0D,0D, 1.989E30);
        //Graphical Sun
        Sphere sunGraphic= new Sphere(6955);
        Image sunTex= new Image("2k_sun.jpg");  //Source: https://www.solarsystemscope.com/textures/
        Point3D sunAxis= new Point3D(1,0,0);
        sunGraphic.setRotationAxis(sunAxis);
        sunGraphic.setRotate(85);   
        PhongMaterial sunSkin= new PhongMaterial();
        sunSkin.setDiffuseMap(sunTex);
        sunGraphic.setMaterial(sunSkin);

        //Mercury
        //Mathematical Mercury
        SpaceObject mercury = new SpaceObject(-2.105262111032039E+10, -6.640663808353403E+10, -3.492446023382954E+09,  3.665298706393840E+04, -1.228983810111077E+04, -4.368172898981951E+03, 3.301E+23);
        //Graphical Mercury
        Sphere mercuryGraphic= new Sphere(24);
        mercuryGraphic.setTranslateX(-2.105262111032039E+3);
        mercuryGraphic.setTranslateY(-6.640663808353403E+3);
        mercuryGraphic.setTranslateZ(-3.492446023382954E+2);
        Image mercuryTex= new Image("2k_mercury.jpg"); //Source: https://www.solarsystemscope.com/textures/
        PhongMaterial mercurySkin= new PhongMaterial();
        mercurySkin.setDiffuseMap(mercuryTex);
        mercuryGraphic.setMaterial(mercurySkin);
        Point3D mercuryAxis= new Point3D(1,0,0);
        mercuryGraphic.setRotationAxis(mercuryAxis);
        mercuryGraphic.setRotate(85);
       

        //Venus
        //Mathematical Venus
        SpaceObject venus = new SpaceObject(-1.075055502695123E+11, -3.366520720591562E+09,  6.159219802771119E+09,  8.891598046362434E+02, -3.515920774124290E+04, -5.318594054684045E+02, 4.867E+24);
        //Graphical Venus
        Sphere venusGraphic = new Sphere(61);
        venusGraphic.setTranslateX(-1.075055502695123E+4);
        venusGraphic.setTranslateY(-3.366520720591562E+2);
        venusGraphic.setTranslateZ(6.159219802771119E+2);
        Image venusTex= new Image("2k_venus_surface.jpg");//Source: https://www.solarsystemscope.com/textures/
        PhongMaterial venusSkin= new PhongMaterial();
        venusSkin.setDiffuseMap(venusTex);
        venusGraphic.setMaterial(venusSkin);
        Point3D venusAxis= new Point3D(1,0,0);
        venusGraphic.setRotationAxis(venusAxis);
        venusGraphic.setRotate(85);

        //Earth
        //Mathematical Earth
        SpaceObject earth = new SpaceObject(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+05, -2.983983333368269E+04, -5.207633918704476E+03,  6.169062303484907E-02, 5.972E+24);
        //Graphical Earth
        Sphere earthGraphic = new Sphere(64);
        earthGraphic.setTranslateX(-2.521092863852298E+3);
        earthGraphic.setTranslateY(1.449279195712076E+4);
        earthGraphic.setTranslateZ(-6.164888475164771E-02);
        Point3D earthAxis= new Point3D(1,0,0);
        earthGraphic.setRotationAxis(earthAxis);
        earthGraphic.setRotate(85);
        Image earthTex= new Image("world.topo.200412.3x5400x2700.jpg"); //Source: https://visibleearth.nasa.gov/view.php?id=73909
        PhongMaterial earthSkin= new PhongMaterial();
        earthSkin.setDiffuseMap(earthTex);
        earthGraphic.setMaterial(earthSkin);

        //Moon
        //Mathematical Moon
        SpaceObject moon = new SpaceObject(-2.552857888050620E+10,  1.446860363961675E+11,  3.593933517466486E+07, -2.927904627038706E+04, -6.007566180814270E+03, -1.577640655646029E00, 7.349E+22);
        //Graphical Moon
        Sphere moonGraphic= new Sphere(17);
        moonGraphic.setTranslateX(-2.552857888050620E+3);
        moonGraphic.setTranslateY(1.446860363961675E+4);
        moonGraphic.setTranslateZ(3.593933517466486);
        Image moony= new Image("2k_moon.jpg");//Source: https://www.solarsystemscope.com/textures/
        PhongMaterial moonSkin= new PhongMaterial();
        moonSkin.setDiffuseMap(moony);
        moonGraphic.setMaterial(moonSkin);

        //Probe
        //Mathematical Probe
        SpaceObject probe = new SpaceObject(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+07,  2.0747878783946157E5, 1.9179849992707407E5,  -1.6014482977865146E4, 5000);
        //Graphical Probe
        Sphere probeGraphic = new Sphere(6);
        probeGraphic.setTranslateX(-2.521092863852298E+3);
        probeGraphic.setTranslateY(1.449279195712076E+4);
        probeGraphic.setTranslateZ(-6.164888475164771);

        //Mars
        //Mathematical Mars
        SpaceObject mars = new SpaceObject(2.079950549908331E+11, -3.143009561106971E+09, -5.178781160069674E+09,  1.295003532851602E+03,  2.629442067068712E+04,  5.190097267545717E+02, 6.417E+23);
        //Graphical Mars
        Sphere marsGraphic= new Sphere(34);
        marsGraphic.setTranslateX(2.079950549908331E+4);
        marsGraphic.setTranslateY(-3.143009561106971E+2);
        marsGraphic.setTranslateZ(-5.178781160069674E+2);
        Point3D marsAxis= new Point3D(1,0,0);
        marsGraphic.setRotationAxis(marsAxis);
        marsGraphic.setRotate(85);
        Image marsTex= new Image("2k_mars.jpg");//Source: https://www.solarsystemscope.com/textures/
        PhongMaterial marsSkin= new PhongMaterial();
        marsSkin.setDiffuseMap(marsTex);
        marsGraphic.setMaterial(marsSkin);

        //Mars Moon: Phobos
        //Mathematical Phobos
        SpaceObject phobos= new SpaceObject(2.080001728304922E+11,-3.135629529907857E+09,-5.180946414782474E+09,-2.461795389080246E+02,2.758970765510455E+04,1.326588322007167E+03,1.06E+16);
        //Graphical Phobos
        Sphere phobosGraphic= new Sphere(1);
        phobosGraphic.setTranslateX(2.080001728304922E+04);
        phobosGraphic.setTranslateY(-3.135629529907857E+02);
        phobosGraphic.setTranslateZ(-5.180946414782474E+02);
        Image phobosTex= new Image("mar1kuu2.jpg");//Source: https://nasa3d.arc.nasa.gov/images
        PhongMaterial phobosSkin= new PhongMaterial();
        phobosSkin.setDiffuseMap(phobosTex);
        phobosGraphic.setMaterial(phobosSkin);

        //Mars Moon: Demios
        //Mathematical Demios
        SpaceObject demios= new SpaceObject( 2.079758459552988E+11,-3.134519020859756E+09 ,-5.168323663883276E+09, 8.310732201509531E+02,2.503674676157639E+04,  6.872885887768234E+02,2.4E+15);
        //Graphical Demios
        Sphere demiosGraphic= new Sphere(1);
        demiosGraphic.setTranslateX(2.079758459552988E+04);
        demiosGraphic.setTranslateY(-3.134519020859756E+02);
        demiosGraphic.setTranslateZ(-5.168323663883276E+02);
        Image demiosTex= new Image("2k_ceres_fictional.jpg");//Source: https://www.solarsystemscope.com/textures/
        PhongMaterial demiosSkin= new PhongMaterial();
        demiosSkin.setDiffuseMap(demiosTex);
        demiosGraphic.setMaterial(demiosSkin);

        //Jupiter
        //Mathematical Jupiter
        SpaceObject jupiter = new SpaceObject(5.989091595026654E+11,  4.391225931434094E+11, -1.523254615467653E+10, -7.901937631606453E+03,  1.116317697592017E+04,  1.306729060953327E+02, 1.899E+27);
        //Graphical Jupiter
        Sphere jupiterGraphic= new Sphere(699);
        jupiterGraphic.setTranslateX(5.989091595026654E+4);
        jupiterGraphic.setTranslateY(4.391225931434094E+4);
        jupiterGraphic.setTranslateZ(-1.523254615467653E+03);
        Image jupiterTex= new Image("2k_jupiter.jpg");//Source: https://www.solarsystemscope.com/textures/
        PhongMaterial jupiterSkin= new PhongMaterial();
        jupiterSkin.setDiffuseMap(jupiterTex);
        jupiterGraphic.setMaterial(jupiterSkin);
        Point3D jupiterAxis= new Point3D(1,0,0);
        jupiterGraphic.setRotationAxis(jupiterAxis);
        jupiterGraphic.setRotate(85);

        //Jupiter Moon: Io
        //Mathematical Io
        SpaceObject io= new SpaceObject(5.989539518409647E+11,4.387023144998344E+11,-1.524679648427427E+10, 9.283361803632189E+03,1.303819812527103E+04,4.583205514926929E+02,8.932E+22);
        //Graphical Io
        Sphere ioGraphic= new Sphere(18);
        ioGraphic.setTranslateX(5.989539518409647E+04);
        ioGraphic.setTranslateY(4.387023144998344E+04);
        ioGraphic.setTranslateZ(-1.524679648427427E+03);
        Image ioTex= new Image("noyPQQbBJyozEwVpULzUvM-970-80.jpg"); //Source: https://www.space.com/14977-jupiter-moon-io-global-map-photos.html
        PhongMaterial ioSkin= new PhongMaterial();
        ioSkin.setDiffuseMap(ioTex);
        ioGraphic.setMaterial(ioSkin);

        //Jupiter Moon: Europa
        //Mathematical Europa
        SpaceObject europa= new SpaceObject(5.982770778300034E+11,4.393402643657560E+11,-1.523558910378432E+10,-1.227402322885824E+04,-1.912604058757177E+03,-2.952416114807850E+02,4.8E+22);
        //Graphical Europa
        Sphere europaGraphic= new Sphere(16);
        europaGraphic.setTranslateX(5.982770778300034E+04);
        europaGraphic.setTranslateY(4.393402643657560E+04);
        europaGraphic.setTranslateZ(-1.523558910378432E+03);
        Image europaTex = new Image("20130305_mikebrown01_europa_f537.jpg");//Source: http://www.planetary.org/blogs/guest-blogs/2013/0305-brown-sea-salt.html
        PhongMaterial europaSkin= new PhongMaterial();
        europaSkin.setDiffuseMap(europaTex);
        europaGraphic.setMaterial(europaSkin);

        //Jupiter Moon: Ganymede
        //Mathematical Ganymede
        SpaceObject ganymede=new SpaceObject(5.978734287587731E+11,4.388509469893177E+11,-1.525419290729403E+10,-5.123069954141830E+03,6.549868238873174E+02,-2.270989208416142E+02,1.4819E+23);
        //Graphical Ganymede
        Sphere ganymedeGraphic= new Sphere(26);
        ganymedeGraphic.setTranslateX(5.978734287587731E+04);
        ganymedeGraphic.setTranslateY(4.388509469893177E+04);
        ganymedeGraphic.setTranslateZ(-1.525419290729403E+03);
        Image ganymedeTex= new Image("Map_of_Ganymede_by_Björn_Jónsson.jpg"); //Source: https://commons.wikimedia.org/wiki/File:Map_of_Ganymede_by_Bj%C3%B6rn_J%C3%B3nsson.jpg
        PhongMaterial ganymedeSkin= new PhongMaterial();
        ganymedeSkin.setDiffuseMap(ganymedeTex);
        ganymedeGraphic.setMaterial(ganymedeSkin);

        //Jupiter Moon: Callisto
        //Mathematical Callisto
        SpaceObject callisto= new SpaceObject(5.995751543436089E+11,4.408784590989536E+11,-1.516642941711968E+10,-1.556521929613705E+04,1.413266575371035E+04,1.262439229830568E+02,1.0759E+23);
        //Grapical Callisto
        Sphere callistoGraphic= new Sphere(24);
        callistoGraphic.setTranslateX(5.995751543436089E+04);
        callistoGraphic.setTranslateY(4.408784590989536E+04);
        callistoGraphic.setTranslateZ(-1.516642941711968E+03);
        Image callistoTex= new Image("callisto.jpg"); //Source: https://bjj.mmedia.is/data/callisto/index.html
        PhongMaterial callistoSkin= new PhongMaterial();
        callistoSkin.setDiffuseMap(callistoTex);
        callistoGraphic.setMaterial(callistoSkin);

        //Saturn
        //Mathematical Saturn
        SpaceObject saturn = new SpaceObject(9.587063371332250E+11,  9.825652108702583E+11, -5.522065686935234E+10, -7.428885681642827E+03,  6.738814233429374E+03,  1.776643556375199E+02, 5.685E+26);
        //Graphical Saturn
        Sphere saturnGraphic = new Sphere(582);
        saturnGraphic.setTranslateX(9.587063371332250E+4);
        saturnGraphic.setTranslateY(9.825652108702583E+4);
        saturnGraphic.setTranslateZ(-5.522065686935234E+3);
        Point3D saturnAxis= new Point3D(1,0,0);
        saturnGraphic.setRotationAxis(saturnAxis);
        saturnGraphic.setRotate(85);
        Image saturnTex= new Image("2k_saturn.jpg"); //Source: https://www.solarsystemscope.com/textures/
        PhongMaterial saturnSkin= new PhongMaterial();
        saturnSkin.setDiffuseMap(saturnTex);
        saturnGraphic.setMaterial(saturnSkin);

        //Saturn Moon:Titan
        //Mathematical Titan
        SpaceObject titan = new SpaceObject( 9.579293831681823E+11,  9.834677749678675E+11, -5.561032276665545E+10, -1.170810292990584E+04,  3.955109586303735E+03,  2.034356139087789E+03, 13455.3E+19);
        //Graphical Titan
        Sphere titanGraphic= new Sphere(25);
        titanGraphic.setTranslateX(9.579293831681823E+4);
        titanGraphic.setTranslateY(9.834677749678675E+4);
        titanGraphic.setTranslateZ(-5.561032276665545E+3);
        Image titanTex= new Image("sat6fss1.jpg"); //Source: https://nasa3d.arc.nasa.gov/images
        PhongMaterial titanSkin= new PhongMaterial();
        titanSkin.setDiffuseMap(titanTex);
        titanGraphic.setMaterial(titanSkin);

        //Saturn Moon: Mimas
        //Mathematical Mimas
        SpaceObject mimas= new SpaceObject(9.585933797467707E+11,9.827031937056004E+11,-5.528229528100616E+10,-1.858271656433577E+04,-5.864309674953683E+02,4.659784882381858E+03,3.73E+19);
        //Graphical Mimas
        Sphere mimasGraphic= new Sphere(1);
        mimasGraphic.setTranslateX(9.585933797467707E+04);
        mimasGraphic.setTranslateY(9.827031937056004E+04);
        mimasGraphic.setTranslateZ(-5.528229528100616E+03);
        Image mimasTex= new Image("PIA18437_hires.jpg"); //Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18437
        PhongMaterial mimasSkin= new PhongMaterial();
        mimasSkin.setDiffuseMap(mimasTex);
        mimasGraphic.setMaterial(mimasSkin);
        
        //Saturn Moon: Enceladus
        //Mathematical Enceladus
        SpaceObject enceladus= new SpaceObject(9.584686368989240E+11,9.825698015650455E+11,-5.519999340655005E+10,-7.087891656970907E+03,-4.432543978580975E+03,5.997973410718807E+03,1.076E+20);
        //Graphical Enceladus
        Sphere enceladusGraphic= new Sphere(2);
        enceladusGraphic.setTranslateX(9.584686368989240E+04);
        enceladusGraphic.setTranslateY(9.825698015650455E+04);
        enceladusGraphic.setTranslateZ(-5.519999340655005E+03);
        Image enceladusTex= new Image("PIA18435_hires.jpg");//Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18435
        PhongMaterial enceladusSkin= new PhongMaterial();
        enceladusSkin.setDiffuseMap(enceladusTex);
        enceladusGraphic.setMaterial(enceladusSkin);

        //Saturn Moon: Tethys
        //Mathematical Tethys
        SpaceObject tethys= new SpaceObject(9.584896767756197E+11,9.823969914493581E+11,-5.511282890265793E+10,1.884353823857955E+02,-1.075740574740226E+03,3.294335699239238E+03,6.130E+20);
        //Graphical Tethys
        Sphere tethysGraphic= new Sphere(5);
        tethysGraphic.setTranslateX(9.584896767756197E+04);
        tethysGraphic.setTranslateY(9.823969914493581E+04);
        tethysGraphic.setTranslateZ(-5.511282890265793E+03);
        Image tethysTex= new Image("PIA18439_hires.jpg");//Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18439
        PhongMaterial tethysSkin= new PhongMaterial();
        tethysSkin.setDiffuseMap(tethysTex);
        tethysGraphic.setMaterial(tethysSkin);

        //Saturn Moon: Dione
        //Mathematical Dione
        SpaceObject dione= new SpaceObject(9.585276353579862E+11, 9.822780179748093E+11,-5.505286854253536E+10,1.370108959468099E+03,2.186732770372938E+03,1.704947776738479E+03,1.097E+21);
        //Graphical Dione
        Sphere dioneGraphic= new Sphere(6);
        dioneGraphic.setTranslateX(9.585276353579862E+04);
        dioneGraphic.setTranslateY(9.822780179748093E+04);
        dioneGraphic.setTranslateZ(-5.505286854253536E+03);
        Image dioneTex= new Image("PIA18434_hires.jpg");//Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18434
        PhongMaterial dioneSkin= new PhongMaterial();
        dioneSkin.setDiffuseMap(dioneTex);
        dioneGraphic.setMaterial(dioneSkin);


        //Saturn Moon: Rhea
        //Mathematical Rhea
        SpaceObject rhea= new SpaceObject(9.582858745650558E+11,9.828629109039947E+11,-5.533445794600141E+10,-1.249378043406604E+04,9.548491287020374E+02,3.746988655327530E+03,2.29E+21);
        //Graphical Rhea
        Sphere rheaGraphic= new Sphere(8);
        rheaGraphic.setTranslateX(9.582858745650558E+04);
        rheaGraphic.setTranslateY(9.828629109039947E+04);
        rheaGraphic.setTranslateZ(-5.533445794600141E+03);
        Image rheaTex= new Image("PIA18434_hires.jpg");//Source: https://www.jpl.nasa.gov/spaceimages/details.php?id=PIA18438
        PhongMaterial rheaSkin= new PhongMaterial();
        rheaSkin.setDiffuseMap(rheaTex);
        rheaGraphic.setMaterial(rheaSkin);

        //Saturn Moon: Phoebe
        //Mathematical Phoebe
        SpaceObject phoebe= new SpaceObject(9.470030184077681E+11,9.797584943042043E+11,-5.388629873572934E+10,-8.111087864852331E+03,8.429736036831875E+03,2.813327671729886E+02,8.3E+18);
        //Graphical Phoebe
        Sphere phoebeGraphic= new Sphere(1);
        phoebeGraphic.setTranslateX(9.470030184077681E+04);
        phoebeGraphic.setTranslateY(9.797584943042043E+04);
        phoebeGraphic.setTranslateZ(-5.388629873572934E+03);
        Image phoebeTex= new Image("2k_makemake_fictional.jpg"); //Source:https://www.solarsystemscope.com/textures/
        PhongMaterial phoebeSkin= new PhongMaterial();
        phoebeSkin.setDiffuseMap(phoebeTex);
        phoebeGraphic.setMaterial(phoebeSkin);

        //Graphical Representation of Orbital Paths
        Color color= Color.TRANSPARENT;
        Color path= Color.CORNFLOWERBLUE;
        Circle mercuryPath=new Circle(0,0,Math.abs(mercuryGraphic.getTranslateX()), color);
        mercuryPath.setStroke(path);
        mercuryPath.setStrokeWidth(100);

        Circle venusPath=new Circle(0,0,Math.abs(venusGraphic.getTranslateX()), color);
        venusPath.setStroke(path);
        venusPath.setStrokeWidth(100);

        Circle earthPath=new Circle(0,0,Math.abs(earthGraphic.getTranslateX()), color);
        earthPath.setStroke(path);
        earthPath.setStrokeWidth(100);

        Circle marsPath=new Circle(0,0,Math.abs(marsGraphic.getTranslateX()), color);
        marsPath.setStroke(path);
        marsPath.setStrokeWidth(100);

        Circle jupiterPath=new Circle(0,0,Math.sqrt(Math.pow(jupiterGraphic.getTranslateX(),2)+Math.pow(jupiterGraphic.getTranslateY(),2)), color);
        jupiterPath.setStroke(path);
        jupiterPath.setStrokeWidth(100);

        Circle saturnPath=new Circle(0,0,Math.abs(saturnGraphic.getTranslateX()), color);
        saturnPath.setStroke(path);
        saturnPath.setStrokeWidth(100);


    

        Group planets= new Group();
        planets.getChildren().add(sunGraphic);
        planets.getChildren().add(mercuryGraphic);
        planets.getChildren().add(venusGraphic);
        planets.getChildren().add(earthGraphic);
        planets.getChildren().add(moonGraphic);
        planets.getChildren().add(marsGraphic);
        planets.getChildren().add(jupiterGraphic);
        planets.getChildren().add(saturnGraphic);
        planets.getChildren().add(titanGraphic);
        planets.getChildren().add(phobosGraphic);
        planets.getChildren().add(demiosGraphic);
        planets.getChildren().add(ioGraphic);
        planets.getChildren().add(europaGraphic);
        planets.getChildren().add(ganymedeGraphic);
        planets.getChildren().add(callistoGraphic);
        planets.getChildren().add(mimasGraphic);
        planets.getChildren().add(enceladusGraphic);
        planets.getChildren().add(tethysGraphic);
        planets.getChildren().add(dioneGraphic);
        planets.getChildren().add(rheaGraphic);
        planets.getChildren().add(phoebeGraphic);
        planets.getChildren().add(probeGraphic);
        planets.getChildren().add(mercuryPath);
        planets.getChildren().add(venusPath);
        planets.getChildren().add(earthPath);
        planets.getChildren().add(marsPath);
        planets.getChildren().add(jupiterPath);
        planets.getChildren().add(saturnPath);
        SubScene solarSystem=new SubScene(planets,WIDTH,HEIGHT);
        solarSystem.setCamera(camera);
        solarSystem.setFill(Color.BLACK);
        root.setCenter(solarSystem);


        //Creating a list of celestial bodies (mathematical representation)
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
        listOfObjects.add(probe);


        //Creating the scene
        Scene scene=new Scene(root,WIDTH,HEIGHT);
        primaryStage.setTitle("Mission to Titan");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Mathematical Model: Input
        final double YEAR_TO_SECONDS = 55*86400;
        final double TIME_STEP=1;
        List<Vector3D> mercuryPos= new ArrayList<Vector3D>();
        mercuryPos.add(mercury.getPosition());
        List<Vector3D> venusPos=  new ArrayList<Vector3D>();
        venusPos.add(venus.getPosition());
        List<Vector3D> earthPos=  new ArrayList<Vector3D>();
        earthPos.add(earth.getPosition());
        List<Vector3D> marsPos= new ArrayList<Vector3D>();
        marsPos.add(mars.getPosition());
        List<Vector3D> jupiterPos= new ArrayList<Vector3D>();
        jupiterPos.add(jupiter.getPosition());
        List<Vector3D> saturnPos= new ArrayList<Vector3D>();
        saturnPos.add(saturn.getPosition());
        List<Vector3D> moonPos= new ArrayList<Vector3D>();
        moonPos.add(moon.getPosition());
        List<Vector3D> titanPos= new ArrayList<Vector3D>();
        titanPos.add(titan.getPosition());
        List<Vector3D> phobosPos= new ArrayList<Vector3D>();
        phobosPos.add(mercury.getPosition());
        List<Vector3D> demiosPos= new ArrayList<Vector3D>();
        demiosPos.add(demios.getPosition());
        List<Vector3D> ioPos= new ArrayList<Vector3D>();
        ioPos.add(io.getPosition());
        List<Vector3D> europaPos= new ArrayList<Vector3D>();
        europaPos.add(europa.getPosition());
        List<Vector3D> ganymedePos= new ArrayList<Vector3D>();
        ganymedePos.add(ganymede.getPosition());
        List<Vector3D> callistoPos=new ArrayList<Vector3D>();
        callistoPos.add(callisto.getPosition());
        List<Vector3D> mimasPos= new ArrayList<Vector3D>();
        mimasPos.add(mimas.getPosition());
        List<Vector3D> enceladusPos= new ArrayList<Vector3D>();
        enceladusPos.add(enceladus.getPosition());
        List<Vector3D> tethysPos= new ArrayList<Vector3D>();
        tethysPos.add(tethys.getPosition());
        List<Vector3D> dionePos= new ArrayList<Vector3D>();
        dionePos.add(dione.getPosition());
        List<Vector3D> rheaPos= new ArrayList<Vector3D>();
        rheaPos.add(rhea.getPosition());
        List<Vector3D> phoebePos= new ArrayList<Vector3D>();
        phoebePos.add(phoebe.getPosition());
        List<Vector3D> probePos= new ArrayList<Vector3D>();
        probePos.add(probe.getPosition());

        for (int i = 0; i<YEAR_TO_SECONDS; i++) {
            Leapfrog leapfrog = new Leapfrog(TIME_STEP);
            for (SpaceObject spaceObject : listOfObjects) {
                spaceObject.updateForces(listOfObjects);
                leapfrog.fullUpdate(spaceObject);
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
                probePos.add(listOfObjects.get(21).getPosition());
            }
        }
        probePos.get(probePos.size()-1).setX(titanPos.get(titanPos.size()-1).getX());
        probePos.get(probePos.size()-1).setY(titanPos.get(titanPos.size()-1).getY());
        probePos.get(probePos.size()-1).setZ(titanPos.get(titanPos.size()-1).getZ());

        //User Controls
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,event2 ->{
            switch(event2.getCode()){
                //Moves the simulation forward in time
                case A:
                    if(index1<mercuryPos.size()-1 && index1>0) {
                        mercuryGraphic.translateXProperty().set(mercuryPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateYProperty().set(mercuryPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateZProperty().set(mercuryPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateXProperty().set(venusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateYProperty().set(venusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateZProperty().set(venusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateXProperty().set(earthPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateYProperty().set(earthPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateZProperty().set(earthPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateXProperty().set(marsPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateYProperty().set(marsPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateZProperty().set(marsPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateXProperty().set(jupiterPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateYProperty().set(jupiterPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateZProperty().set(jupiterPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateXProperty().set(saturnPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateYProperty().set(saturnPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateZProperty().set(saturnPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateXProperty().set(moonPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateYProperty().set(moonPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateZProperty().set(moonPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateXProperty().set(titanPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateYProperty().set(titanPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateZProperty().set(titanPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateXProperty().set(phobosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateYProperty().set(phobosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateZProperty().set(phobosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateXProperty().set(demiosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateYProperty().set(demiosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateZProperty().set(demiosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateXProperty().set(ioPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateYProperty().set(ioPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateZProperty().set(ioPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateXProperty().set(europaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateYProperty().set(europaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateZProperty().set(europaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateXProperty().set(ganymedePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateYProperty().set(ganymedePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateZProperty().set(ganymedePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateXProperty().set(callistoPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateYProperty().set(callistoPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateZProperty().set(callistoPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateXProperty().set(mimasPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateYProperty().set(mimasPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateZProperty().set(mimasPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateXProperty().set(enceladusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateYProperty().set(enceladusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateZProperty().set(enceladusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateXProperty().set(tethysPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateYProperty().set(tethysPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateZProperty().set(tethysPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateXProperty().set(dionePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateYProperty().set(dionePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateZProperty().set(dionePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateXProperty().set(rheaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateYProperty().set(rheaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateZProperty().set(rheaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateXProperty().set(phoebePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateYProperty().set(phoebePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateZProperty().set(phoebePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateXProperty().set(probePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateYProperty().set(probePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateZProperty().set(probePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        camera.translateXProperty().set(probeGraphic.getTranslateX()-500);
                        camera.translateYProperty().set(probeGraphic.getTranslateY()+100);
                        camera.translateZProperty().set(probeGraphic.getTranslateZ()-400);
                        index1++;
                    }else if(index1==mercuryPos.size()-1){
                        mercuryGraphic.translateXProperty().set(mercuryPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateYProperty().set(mercuryPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateZProperty().set(mercuryPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateXProperty().set(venusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateYProperty().set(venusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateZProperty().set(venusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateXProperty().set(earthPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateYProperty().set(earthPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateZProperty().set(earthPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateXProperty().set(marsPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateYProperty().set(marsPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateZProperty().set(marsPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateXProperty().set(jupiterPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateYProperty().set(jupiterPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateZProperty().set(jupiterPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateXProperty().set(saturnPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateYProperty().set(saturnPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateZProperty().set(saturnPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateXProperty().set(moonPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateYProperty().set(moonPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateZProperty().set(moonPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateXProperty().set(titanPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateYProperty().set(titanPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateZProperty().set(titanPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateXProperty().set(phobosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateYProperty().set(phobosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateZProperty().set(phobosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateXProperty().set(demiosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateYProperty().set(demiosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateZProperty().set(demiosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateXProperty().set(ioPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateYProperty().set(ioPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateZProperty().set(ioPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateXProperty().set(europaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateYProperty().set(europaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateZProperty().set(europaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateXProperty().set(ganymedePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateYProperty().set(ganymedePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateZProperty().set(ganymedePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateXProperty().set(callistoPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateYProperty().set(callistoPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateZProperty().set(callistoPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateXProperty().set(mimasPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateYProperty().set(mimasPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateZProperty().set(mimasPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateXProperty().set(enceladusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateYProperty().set(enceladusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateZProperty().set(enceladusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateXProperty().set(tethysPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateYProperty().set(tethysPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateZProperty().set(tethysPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateXProperty().set(dionePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateYProperty().set(dionePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateZProperty().set(dionePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateXProperty().set(rheaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateYProperty().set(rheaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateZProperty().set(rheaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateXProperty().set(phoebePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateYProperty().set(phoebePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateZProperty().set(phoebePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateXProperty().set(probePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateYProperty().set(probePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateZProperty().set(probePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        camera.translateXProperty().set(probeGraphic.getTranslateX()-500);
                        camera.translateYProperty().set(probeGraphic.getTranslateY()+100);
                        camera.translateZProperty().set(probeGraphic.getTranslateZ()-400);
                    }else if(index1==0){
                        mercuryGraphic.translateXProperty().set(mercuryPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateYProperty().set(mercuryPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateZProperty().set(mercuryPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateXProperty().set(venusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateYProperty().set(venusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateZProperty().set(venusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateXProperty().set(earthPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateYProperty().set(earthPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateZProperty().set(earthPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateXProperty().set(marsPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateYProperty().set(marsPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateZProperty().set(marsPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateXProperty().set(jupiterPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateYProperty().set(jupiterPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateZProperty().set(jupiterPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateXProperty().set(saturnPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateYProperty().set(saturnPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateZProperty().set(saturnPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateXProperty().set(moonPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateYProperty().set(moonPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateZProperty().set(moonPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateXProperty().set(titanPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateYProperty().set(titanPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateZProperty().set(titanPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateXProperty().set(phobosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateYProperty().set(phobosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateZProperty().set(phobosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateXProperty().set(demiosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateYProperty().set(demiosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateZProperty().set(demiosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateXProperty().set(ioPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateYProperty().set(ioPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateZProperty().set(ioPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateXProperty().set(europaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateYProperty().set(europaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateZProperty().set(europaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateXProperty().set(ganymedePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateYProperty().set(ganymedePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateZProperty().set(ganymedePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateXProperty().set(callistoPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateYProperty().set(callistoPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateZProperty().set(callistoPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateXProperty().set(mimasPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateYProperty().set(mimasPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateZProperty().set(mimasPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateXProperty().set(enceladusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateYProperty().set(enceladusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateZProperty().set(enceladusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateXProperty().set(tethysPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateYProperty().set(tethysPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateZProperty().set(tethysPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateXProperty().set(dionePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateYProperty().set(dionePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateZProperty().set(dionePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateXProperty().set(rheaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateYProperty().set(rheaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateZProperty().set(rheaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateXProperty().set(phoebePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateYProperty().set(phoebePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateZProperty().set(phoebePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateXProperty().set(probePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateYProperty().set(probePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateZProperty().set(probePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        camera.translateXProperty().set(probeGraphic.getTranslateX()-500);
                        camera.translateYProperty().set(probeGraphic.getTranslateY()+100);
                        camera.translateZProperty().set(probeGraphic.getTranslateZ()-400);
                        index1++;
                    }else{}
                    break;
                //Moves the simulation in reverse. 
                case D:
                    if(index1>0){
                        mercuryGraphic.translateXProperty().set(mercuryPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateYProperty().set(mercuryPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateZProperty().set(mercuryPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateXProperty().set(venusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateYProperty().set(venusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateZProperty().set(venusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateXProperty().set(mercuryPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateYProperty().set(mercuryPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateZProperty().set(mercuryPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateXProperty().set(venusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateYProperty().set(venusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateZProperty().set(venusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateXProperty().set(earthPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateYProperty().set(earthPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateZProperty().set(earthPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateXProperty().set(marsPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateYProperty().set(marsPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateZProperty().set(marsPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateXProperty().set(jupiterPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateYProperty().set(jupiterPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateZProperty().set(jupiterPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateXProperty().set(saturnPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateYProperty().set(saturnPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateZProperty().set(saturnPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateXProperty().set(moonPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateYProperty().set(moonPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateZProperty().set(moonPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateXProperty().set(titanPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateYProperty().set(titanPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateZProperty().set(titanPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateXProperty().set(phobosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateYProperty().set(phobosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateZProperty().set(phobosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateXProperty().set(demiosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateYProperty().set(demiosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateZProperty().set(demiosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateXProperty().set(ioPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateYProperty().set(ioPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateZProperty().set(ioPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateXProperty().set(europaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateYProperty().set(europaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateZProperty().set(europaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateXProperty().set(ganymedePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateYProperty().set(ganymedePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateZProperty().set(ganymedePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateXProperty().set(callistoPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateYProperty().set(callistoPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateZProperty().set(callistoPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateXProperty().set(mimasPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateYProperty().set(mimasPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateZProperty().set(mimasPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateXProperty().set(enceladusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateYProperty().set(enceladusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateZProperty().set(enceladusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateXProperty().set(tethysPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateYProperty().set(tethysPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateZProperty().set(tethysPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateXProperty().set(dionePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateYProperty().set(dionePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateZProperty().set(dionePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateXProperty().set(rheaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateYProperty().set(rheaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateZProperty().set(rheaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateXProperty().set(phoebePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateYProperty().set(phoebePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateZProperty().set(phoebePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateXProperty().set(probePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateYProperty().set(probePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateZProperty().set(probePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        camera.translateXProperty().set(probeGraphic.getTranslateX()-500);
                        camera.translateYProperty().set(probeGraphic.getTranslateY()+100);
                        camera.translateZProperty().set(probeGraphic.getTranslateZ()-400);
                        index1--;
                    }else if(index1==0){
                        mercuryGraphic.translateXProperty().set(mercuryPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateYProperty().set(mercuryPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mercuryGraphic.translateZProperty().set(mercuryPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateXProperty().set(venusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateYProperty().set(venusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        venusGraphic.translateZProperty().set(venusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateXProperty().set(earthPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateYProperty().set(earthPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        earthGraphic.translateZProperty().set(earthPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateXProperty().set(marsPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateYProperty().set(marsPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        marsGraphic.translateZProperty().set(marsPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateXProperty().set(jupiterPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateYProperty().set(jupiterPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        jupiterGraphic.translateZProperty().set(jupiterPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateXProperty().set(saturnPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateYProperty().set(saturnPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        saturnGraphic.translateZProperty().set(saturnPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateXProperty().set(moonPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateYProperty().set(moonPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        moonGraphic.translateZProperty().set(moonPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateXProperty().set(titanPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateYProperty().set(titanPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        titanGraphic.translateZProperty().set(titanPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateXProperty().set(phobosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateYProperty().set(phobosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phobosGraphic.translateZProperty().set(phobosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateXProperty().set(demiosPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateYProperty().set(demiosPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        demiosGraphic.translateZProperty().set(demiosPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateXProperty().set(ioPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateYProperty().set(ioPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ioGraphic.translateZProperty().set(ioPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateXProperty().set(europaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateYProperty().set(europaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        europaGraphic.translateZProperty().set(europaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateXProperty().set(ganymedePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateYProperty().set(ganymedePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        ganymedeGraphic.translateZProperty().set(ganymedePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateXProperty().set(callistoPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateYProperty().set(callistoPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        callistoGraphic.translateZProperty().set(callistoPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateXProperty().set(mimasPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateYProperty().set(mimasPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        mimasGraphic.translateZProperty().set(mimasPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateXProperty().set(enceladusPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateYProperty().set(enceladusPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        enceladusGraphic.translateZProperty().set(enceladusPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateXProperty().set(tethysPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateYProperty().set(tethysPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        tethysGraphic.translateZProperty().set(tethysPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateXProperty().set(dionePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateYProperty().set(dionePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        dioneGraphic.translateZProperty().set(dionePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateXProperty().set(rheaPos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateYProperty().set(rheaPos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        rheaGraphic.translateZProperty().set(rheaPos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateXProperty().set(phoebePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateYProperty().set(phoebePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        phoebeGraphic.translateZProperty().set(phoebePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateXProperty().set(probePos.get(index1).getX()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateYProperty().set(probePos.get(index1).getY()/(SCALING_FACTOR_ORBIT));
                        probeGraphic.translateZProperty().set(probePos.get(index1).getZ()/(SCALING_FACTOR_ORBIT));
                        camera.translateXProperty().set(probeGraphic.getTranslateX()-500);
                        camera.translateYProperty().set(probeGraphic.getTranslateY()+100);
                        camera.translateZProperty().set(probeGraphic.getTranslateZ()-400);
                    }
                    break;
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
