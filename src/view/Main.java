package view;

import model.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("IJA 2020");
        primaryStage.setScene(new Scene(root, 800, 600));

        DataHolder holder = new DataHolder("./../../data");

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
