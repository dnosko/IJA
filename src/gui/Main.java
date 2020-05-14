package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("IJA 2020");
        BorderPane root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        DataHolder holder = new DataHolder("data/");
        ControllerGui controller = loader.getController();

        controller.setHolder(holder);
        controller.setMapBase();
        controller.startTime(1);
        controller.activateActiveBuses(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}