package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DataHolder;

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

        DataHolder holder = new DataHolder("src/model/");
        ControllerGui controller = loader.getController();
        //controller.setElements(//LIST_STREET, LIST_STOPS ...);
        controller.startTime(1);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
