package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import javax.sound.midi.SysexMessage;
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

        DataHolder holder = new DataHolder("data/");
        ControllerGui controller = loader.getController();


        controller.setElements(add_elements(holder.getStreets(), holder.getStops(), holder.getLines()));
        controller.startTime(1);

    }

    public List<Drawable> add_elements(List<Street> streetList, List<Stop> stopList, List<Line> lineList) {
        List<Drawable> elements = new ArrayList<>();

        for (Street str : streetList) {
            elements.add(new StreetGui(str.getId(),str.start(),str.end()));
        }
        for (Stop stop : stopList) {
            elements.add(new StopGui(stop.getId(),stop.getCoordinate()));
        }
        for (Line line : lineList) {
            List<Stop> StopsLine = line.getStops();
            List<Coordinate> pathCoords = new ArrayList<>();

            List<Street> StreetLine = line.getStreets();
            pathCoords.add(StopsLine.get(0).getCoordinate()); //first stop
            for (Street str : StreetLine) {
                // if its last street in Line, get only beggining of street
                if (str.equals(StreetLine.get(StreetLine.size()-1))) {
                    pathCoords.add(str.start());
                    continue;
                }
                //if its first street in line get only end of street
                if (str.equals(StreetLine.get(0))) {
                    pathCoords.add(str.end());
                    continue;
                }
                pathCoords.add(str.start());
                pathCoords.add(str.end());
            }
            pathCoords.add(StopsLine.get(StopsLine.size()-1).getCoordinate()); //last stop
            elements.add(new Vehicle(line,1, new Path (line.getColor(),pathCoords)));
        }

        return elements;
    }

    public static void main(String[] args) {
        launch(args);
    }
}