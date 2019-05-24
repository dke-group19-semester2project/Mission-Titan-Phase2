import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Phase23DSolarSystem_01 extends Application {

    private static final int WIDTH=1400;
    private static final int HEIGHT=800;
    private int index1=0;


    public void start(Stage primaryStage) {

        //Camera
        Camera camera= new PerspectiveCamera();
        camera.translateXProperty().set(-WIDTH/2);
        camera.translateYProperty().set(50000);
        camera.translateZProperty().set(-250000);
        camera.setFarClip(900000000);

        //Solar System
        BorderPane root= new BorderPane();
        //Sun
        Star sun = new Star(0D,0D,0D,0D,0D,0D, 1.989E30,695);
        //Mercury. Add some sort of marker! It's too small.
        Planet mercury = new Planet(-2.105262111032039E+10, -6.640663808353403E+10, -3.492446023382954E+09,  3.665298706393840E+04, -1.228983810111077E+04, -4.368172898981951E+03, 3.301E+23,700);
        mercury.setTranslateX(-2.105262111032039E+4);
        mercury.setTranslateY(-6.640663808353403E+4);
        mercury.setTranslateZ(-3.492446023382954E+3);
        //Venus
        Planet venus = new Planet(-1.075055502695123E+11, -3.366520720591562E+09,  6.159219802771119E+09,  8.891598046362434E+02, -3.515920774124290E+04, -5.318594054684045E+02, 4.867E+24,700);
        venus.setTranslateX(-1.075055502695123E+5);
        venus.setTranslateY(-3.366520720591562E+3);
        venus.setTranslateZ(6.159219802771119E+3);
        //Earth
        Planet earth = new Planet(-2.521092863852298E+10,  1.449279195712076E+11, -6.164888475164771E+05, -2.983983333368269E+04, -5.207633918704476E+03,  6.169062303484907E-02, 5.972E+24, 700);
        earth.setTranslateX(-2.521092863852298E+4);
        earth.setTranslateY(1.449279195712076E+5);
        earth.setTranslateZ(-6.164888475164771E-01);
        //Mars
        Planet mars = new Planet(2.079950549908331E+11, -3.143009561106971E+09, -5.178781160069674E+09,  1.295003532851602E+03,  2.629442067068712E+04,  5.190097267545717E+02, 6.417E+23,700);
        mars.setTranslateX(2.079950549908331E+5);
        mars.setTranslateY(-3.143009561106971E+3);
        mars.setTranslateZ(-5.178781160069674E+3);
        //Jupiter
        Planet jupiter = new Planet(5.989091595026654E+11,  4.391225931434094E+11, -1.523254615467653E+10, -7.901937631606453E+03,  1.116317697592017E+04,  1.306729060953327E+02, 1.899E+27,700);
        jupiter.setTranslateX(5.989091595026654E+5);
        jupiter.setTranslateY(4.391225931434094E+5);
        jupiter.setTranslateZ(-1.523254615467653E+10);
        //Saturn
        Planet saturn = new Planet(9.587063371332250E+11,  9.825652108702583E+11, -5.522065686935234E+10, -7.428885681642827E+03,  6.738814233429374E+03,  1.776643556375199E+02, 5.685E+26,700);
        saturn.setTranslateX(9.587063371332250E+5);
        saturn.setTranslateY(9.825652108702583E+5);
        saturn.setTranslateZ(-5.522065686935234E+4);

        Group planets= new Group();
        planets.getChildren().add(sun);
        planets.getChildren().add(mercury);
        planets.getChildren().add(venus);
        planets.getChildren().add(earth);
        planets.getChildren().add(mars);
        planets.getChildren().add(jupiter);
        planets.getChildren().add(saturn);
        SubScene solarSystem=new SubScene(planets,WIDTH,HEIGHT);
        solarSystem.setCamera(camera);
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
        //listOfObjects.add(moon);

        //Creating the scene
        Scene scene=new Scene(root,WIDTH,HEIGHT);
        primaryStage.setTitle("Mission to Titan");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Mathematical Model
        final double YEAR_TO_SECONDS = 365*86400;
        final double TIME_STEP=1;
        double[] mercuryXPos= new double[316];
        double[] mercuryYPos=new double[316];
        double[] mercuryZPos=new double[316];
        double[] venusXPos= new double[316];
        double[] venusYPos= new double[316];
        double[] venusZPos=new double[316];
        double[] earthXPos= new double[316];
        double[] earthYPos= new double[316];
        double[] earthZPos=new double[316];
        double[] marsXPos= new double[316];
        double[] marsYPos= new double[316];
        double[] marsZPos= new double[316];
        double[] jupiterXPos= new double[316];
        double[] jupiterYPos= new double[316];
        double[] jupiterZPos= new double[316];
        double[] saturnXPos= new double[316];
        double[] saturnYPos= new double[316];
        double[] saturnZPos= new double[316];
       /* double[] moonXPos= new double[316];
        double[] moonYPos= new double[316];
        double[] moonZPos= new double[316];*/
        int index=0;

        for (int i = 0; i<YEAR_TO_SECONDS; i++) {
            for (SpaceObject spaceObject : listOfObjects) {
                spaceObject.updateForce(listOfObjects);
                spaceObject.updateAcceleration();
                spaceObject.updateVelocity(TIME_STEP);
                spaceObject.updatePosition(TIME_STEP);

            }
            if(i%100000==0){
               mercuryXPos[index]=listOfObjects.get(1).getPosition().getX()/(10E+6);
               mercuryYPos[index]=listOfObjects.get(1).getPosition().getY()/(10E+6);
               mercuryZPos[index]=listOfObjects.get(1).getPosition().getZ()/(10E+6);
               venusXPos[index]=listOfObjects.get(2).getPosition().getX()/(10E+6);
               venusYPos[index]=listOfObjects.get(2).getPosition().getY()/(10E+6);
               venusZPos[index]=listOfObjects.get(2).getPosition().getZ()/(10E+6);
               earthXPos[index]=listOfObjects.get(3).getPosition().getX()/(10E+6);
               earthYPos[index]=listOfObjects.get(3).getPosition().getY()/(10E+6);
               earthZPos[index]=listOfObjects.get(3).getPosition().getZ()/(10E+6);
               marsXPos[index]=listOfObjects.get(4).getPosition().getX()/(10E+6);
               marsYPos[index]=listOfObjects.get(4).getPosition().getY()/(10E+6);
               marsZPos[index]=listOfObjects.get(4).getPosition().getZ()/(10E+6);
               jupiterXPos[index]=listOfObjects.get(5).getPosition().getX()/(10E+6);
               jupiterYPos[index]=listOfObjects.get(5).getPosition().getY()/(10E+6);
               jupiterZPos[index]=listOfObjects.get(5).getPosition().getZ()/(10E+6);
               saturnXPos[index]=listOfObjects.get(6).getPosition().getX()/(10E+6);
               saturnYPos[index]=listOfObjects.get(6).getPosition().getY()/(10E+6);
               saturnZPos[index]=listOfObjects.get(6).getPosition().getZ()/(10E+6);
               /*moonXPos[index]=listOfObjects.get(4).getPosition().getX()/(10E+6);
               moonYPos[index]=listOfObjects.get(4).getPosition().getY()/(10E+6);
               moonZPos[index]=listOfObjects.get(4).getPosition().getZ()/(10E+6);*/
               index++;
            }
        }

        //User Controls
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,event2 ->{
            switch(event2.getCode()){
                case A:
                    if(index1<315 && index1>0) {
                        mercury.translateXProperty().set(mercuryXPos[index1]);
                        mercury.translateYProperty().set(mercuryYPos[index1]);
                        mercury.translateZProperty().set(mercuryZPos[index1]);
                        venus.translateXProperty().set(venusXPos[index1]);
                        venus.translateYProperty().set(venusYPos[index1]);
                        venus.translateZProperty().set(venusZPos[index1]);
                        earth.translateXProperty().set(earthXPos[index1]);
                        earth.translateYProperty().set(earthYPos[index1]);
                        earth.translateZProperty().set(earthZPos[index1]);
                        mars.translateXProperty().set(marsXPos[index1]);
                        mars.translateYProperty().set(marsYPos[index1]);
                        mars.translateZProperty().set(marsZPos[index1]);
                        jupiter.translateXProperty().set(jupiterXPos[index1]);
                        jupiter.translateYProperty().set(jupiterYPos[index1]);
                        jupiter.translateZProperty().set(jupiterZPos[index1]);
                        saturn.translateXProperty().set(saturnXPos[index1]);
                        saturn.translateYProperty().set(saturnYPos[index1]);
                        saturn.translateZProperty().set(saturnZPos[index1]);
                        /*moon.translateXProperty().set(moonXPos[index1]);
                        moon.translateYProperty().set(moonYPos[index1]);
                        moon.translateZProperty().set(moonZPos[index1]);*/
                        index1++;
                    }else if(index1==0){
                        mercury.translateXProperty().set(mercuryXPos[index1]);
                        mercury.translateYProperty().set(mercuryYPos[index1]);
                        mercury.translateZProperty().set(mercuryZPos[index1]);
                        venus.translateXProperty().set(venusXPos[index1]);
                        venus.translateYProperty().set(venusYPos[index1]);
                        venus.translateZProperty().set(venusZPos[index1]);
                        earth.translateXProperty().set(earthXPos[index1]);
                        earth.translateYProperty().set(earthYPos[index1]);
                        earth.translateZProperty().set(earthZPos[index1]);
                        mars.translateXProperty().set(marsXPos[index1]);
                        mars.translateYProperty().set(marsYPos[index1]);
                        mars.translateZProperty().set(marsZPos[index1]);
                        jupiter.translateXProperty().set(jupiterXPos[index1]);
                        jupiter.translateYProperty().set(jupiterYPos[index1]);
                        jupiter.translateZProperty().set(jupiterZPos[index1]);
                        saturn.translateXProperty().set(saturnXPos[index1]);
                        saturn.translateYProperty().set(saturnYPos[index1]);
                        saturn.translateZProperty().set(saturnZPos[index1]);
                        /*moon.translateXProperty().set(moonXPos[index1]);
                        moon.translateYProperty().set(moonYPos[index1]);
                        moon.translateZProperty().set(moonZPos[index1]);*/
                        index1++;
                    }else{}
                    break;
                case D:
                    if(index1>0){
                        mercury.translateXProperty().set(mercuryXPos[index1]);
                        mercury.translateYProperty().set(mercuryYPos[index1]);
                        mercury.translateZProperty().set(mercuryZPos[index1]);
                        venus.translateXProperty().set(venusXPos[index1]);
                        venus.translateYProperty().set(venusYPos[index1]);
                        venus.translateZProperty().set(venusZPos[index1]);
                        earth.translateXProperty().set(earthXPos[index1]);
                        earth.translateYProperty().set(earthYPos[index1]);
                        earth.translateZProperty().set(earthZPos[index1]);
                        mars.translateXProperty().set(marsXPos[index1]);
                        mars.translateYProperty().set(marsYPos[index1]);
                        mars.translateZProperty().set(marsZPos[index1]);
                        jupiter.translateXProperty().set(jupiterXPos[index1]);
                        jupiter.translateYProperty().set(jupiterYPos[index1]);
                        jupiter.translateZProperty().set(jupiterZPos[index1]);
                        saturn.translateXProperty().set(saturnXPos[index1]);
                        saturn.translateYProperty().set(saturnYPos[index1]);
                        saturn.translateZProperty().set(saturnZPos[index1]);
                        /*moon.translateXProperty().set(moonXPos[index1]);
                        moon.translateYProperty().set(moonYPos[index1]);
                        moon.translateZProperty().set(moonZPos[index1]);*/
                        index1--;
                    }else if(index1==0){
                        mercury.translateXProperty().set(mercuryXPos[index1]);
                        mercury.translateYProperty().set(mercuryYPos[index1]);
                        mercury.translateZProperty().set(mercuryZPos[index1]);
                        venus.translateXProperty().set(venusXPos[index1]);
                        venus.translateYProperty().set(venusYPos[index1]);
                        venus.translateZProperty().set(venusZPos[index1]);
                        earth.translateXProperty().set(earthXPos[index1]);
                        earth.translateYProperty().set(earthYPos[index1]);
                        earth.translateZProperty().set(earthZPos[index1]);
                        mars.translateXProperty().set(marsXPos[index1]);
                        mars.translateYProperty().set(marsYPos[index1]);
                        mars.translateZProperty().set(marsZPos[index1]);
                        jupiter.translateXProperty().set(jupiterXPos[index1]);
                        jupiter.translateYProperty().set(jupiterYPos[index1]);
                        jupiter.translateZProperty().set(jupiterZPos[index1]);
                        saturn.translateXProperty().set(saturnXPos[index1]);
                        saturn.translateYProperty().set(saturnYPos[index1]);
                        saturn.translateZProperty().set(saturnZPos[index1]);
                        /*moon.translateXProperty().set(moonXPos[index1]);
                        moon.translateYProperty().set(moonYPos[index1]);
                        moon.translateZProperty().set(moonZPos[index1]);*/
                    }
                    break;
            }
        });
        }
    public static void main(String[] args) {
        launch(args);
    }
}
