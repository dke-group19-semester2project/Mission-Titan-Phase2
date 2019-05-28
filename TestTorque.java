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

public class TestTorque extends Application{

    final int WIDTH=500;
    final int HEIGHT=500;
    static double titanRadius = 2575*1000;
    static double startingDistance = titanRadius+800*1000;

    public void start(Stage firstStage){
        //Calculations
        //Test: Probe
        SimulationBody internalProbe = new SimulationBody(new Vector2D(startingDistance,0), new Vector2D(0, 1600), 5000, 1, new WindSpeed(1));
        TorqueTry testTorque= new TorqueTry(internalProbe);

        Box lander= new Box(100,100,100);
        PhongMaterial boxColor= new PhongMaterial();
        boxColor.setDiffuseColor(Color.BLUE);
        lander.setMaterial(boxColor);
        Text torqueTrack= new Text(20,30,"Torque");
        Font torqueFont= new Font("Calibri",30);
        torqueTrack.setFont(torqueFont);
        System.out.println(torqueTrack.getY());

        BorderPane root= new BorderPane(lander);
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