import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.shape.Rectangle;

public class TestTorque extends Application{

    final int WIDTH=700;
    final int HEIGHT=700;
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;

    public void start(Stage firstStage){
        //Calculations
        //Test: Probe
        Rectangle lander= new Rectangle(100,100);
        PhongMaterial boxColor= new PhongMaterial();
        boxColor.setDiffuseColor(Color.BLUE);
        //lander.setMaterial(boxColor);

        //Calculations
        //Test: Probe
        //SimulationBody testProbe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
        //SimulationBody titan = new SimulationBody(new Vector2D(0,0), new Vector2D(0,0), 1.3452E23, 2*titanRadius, new WindSpeed(1));
        TestBody testProbe= new TestBody(5000);
        testProbe.setForce(new Vector2D(20000,20000));
        TorqueTry testTorque= new TorqueTry(testProbe);
         testTorque.useLeftThrusters();
        double test= testTorque.leftThrustAction();
        /*double currentX= lander.getTranslateX();
        double currentY=lander.getTranslateY();*/
        //Point3D testAxis= new Point3D(1,1,0);
       // testProbe.getTransforms().add(new Rotate(test,0,0));
        lander.setRotate(test);
        lander.setTranslateX(300);
        lander.setTranslateY(300);
        System.out.println(lander.getTranslateX());




        Text torqueTrack= new Text(20,30,"Torque");
        Font torqueFont= new Font("Calibri",30);
        torqueTrack.setFont(torqueFont);
        Group collection= new Group(lander);
        SubScene testy= new SubScene(collection,600,600);
        BorderPane root= new BorderPane();
        root.setCenter(testy);

        HBox words= new HBox();
        words.getChildren().add(torqueTrack);
        words.setAlignment(Pos.TOP_CENTER);
        root.setBottom(words);
        //SubSc
        //root.setCenter(lander);
        Scene scene= new Scene(root,WIDTH,HEIGHT);
        firstStage.setTitle("Torque Demo");
        firstStage.setScene(scene);
        firstStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}