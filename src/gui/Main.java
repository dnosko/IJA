package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Coordinate;
import model.DataHolder;
import model.Stop;
import model.Street;

import javax.sound.midi.SysexMessage;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("IJA 2020");
        BorderPane root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //DataHolder holder = new DataHolder("src/model/");
        ControllerGui controller = loader.getController();

        /***********************************************************/
        List<Drawable> elements = new ArrayList<>();
        List<Street> streetsList = new ArrayList<>();
        List<Stop> stopList = new ArrayList<>();
        stopList.add(new Stop("Stop1", new Coordinate(200,100)));
        stopList.add(new Stop("Stop2", new Coordinate(100,300)));
        streetsList.add(new Street("Street1", Arrays.asList(new Coordinate(100,100), new Coordinate(400,100)),
                Arrays.asList(new Stop("Stop1",new Coordinate(200,100)))));
        streetsList.add(new Street("Street2", Arrays.asList(new Coordinate(100,100), new Coordinate(100,400)),
                Arrays.asList(new Stop("Stop2",new Coordinate(100,300)))));
        for (Street str : streetsList) {
            elements.add(new StreetGui(str.getId(),str.start(),str.end()));
        }
        for (Stop stop : stopList) {
            elements.add(new StopGui(stop.getId(),stop.getCoordinate()));
        }
        elements.add(new Vehicle(new Coordinate(100,100),2, Color.AQUA, new Path(
                Arrays.asList( new Coordinate(100,100),
                               new Coordinate(500,500),
                        new Coordinate(1000,600)
                             )
        )));
        controller.setElements(elements);
        /***********************************************************/

        /* toto ked bude holder */
        //controller.setElements(add_elements(//holder.getStreets(), holder.getStops()))));
        controller.startTime(1);

    }
    /* toto ked bude holder
    public List<Drawable> add_elements(List<Street> streetList, List<Stop> stopList) {
        List<Drawable> elements = new ArrayList<>();
        //elements.add(new Vehicle(new Coordinate(100,200),2));
        List<Street> streetsList = new ArrayList<>();
        streetsList.add(new Street("Street1", Arrays.asList(new Coordinate(100,100), new Coordinate(400,100)),
                Arrays.asList(new Stop("Stop1",new Coordinate(200,100)))));
        streetsList.add(new Street("Street2", Arrays.asList(new Coordinate(100,100), new Coordinate(100,400)),
                Arrays.asList(new Stop("Stop2",new Coordinate(100,300)))));
        for (Street str : streetsList) {
            elements.add(new StreetGui(str.getId(),str.start(),str.end()));
        }
        //elements.add(new StreetGui("Street1",new Coordinate(100,100), new Coordinate(400,100)));
        return elements;
    } */

    public static void main(String[] args) {
        launch(args);
    }
}
