package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Coordinate;

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

    public Vehicle(Coordinate position, double speed, Color color, Path path) {
        this.position = position;
        this.speed = speed;
        this.path = path;
        gui = new ArrayList<>();
        gui.add(new Circle(this.position.getX(),this.position.getY(),8,color));
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public void move(Coordinate coordinates) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinates.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinates.getY() - position.getX() + shape.getTranslateY());
        }
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        Coordinate coords = path.getCoordinateDistance(distance);
        move(coords);
        // pridat podmienku iba do poslednej stop
        //Coordinate coords = path.getCoordinateByDistance(distance);

    }
}
