package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Coordinate;
import model.Line;
import model.Stop;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Drawable, TimeUpdate {
    private int distance = 0;
    private double speed = 1;
    private Coordinate position;
    private List<Shape> gui;
    public Path path;
    private List<Stop> stops;
    private Color color;
    List<Drawable> lineList = new ArrayList<>();
    @FXML
    public AnchorPane anchor_lines;


    public Vehicle(Line line, double speed, Path path) {
        this.stops = line.getStops();
        this.position = this.stops.get(0).getCoordinate(); //first position
        this.speed = speed;
        this.path = path;
        this.color = line.getColor();
        gui = new ArrayList<>();
        gui.add(new Circle(this.position.getX(),this.position.getY(),8,color));

        gui.addAll(path.getGUI()); //add path
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Hello World");
                System.out.println(path.getGUI().size());
                try {
                    for (int i = 0; i < (path.getGUI().size()); i++) {
                        System.out.println(i);
                        gui.get(i).setStroke(color);
                    }
                }
                catch (IndexOutOfBoundsException exception) {
                    System.out.println("INDEX OUT OF BOUNDS");
                }
            }
        };
        //Adding event Filter
        gui.get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }


    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    @Override
    public String getType() {
        return "Vehicle";
    }

    public void move(Coordinate coordinates) {
        for (Shape shape : gui) {
            if (shape.getTypeSelector().equals("Line"))
                continue;
            shape.setTranslateX(coordinates.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinates.getY() - position.getY() + shape.getTranslateY());
        }
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        if (distance > path.getPathsize())
            return;
        Coordinate coords = path.getCoordinateDistance(distance);
        move(coords);
        position = coords;
    }

}
