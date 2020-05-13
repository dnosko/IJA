package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Coordinate;
import model.Line;
import model.Stop;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Drawable, TimeUpdate {

    private int distance = 0;
    private double speed = 1;
    private Coordinate position;
    private List<Shape> gui;
    private Path path;
    private List<Stop> stops;

    public Vehicle(Line line, double speed, Path path) {
        this.stops = line.getStops();
        this.position = this.stops.get(0).getCoordinate(); //first position
        this.speed = speed;
        this.path = path;
        gui = new ArrayList<>();
        gui.add(new Circle(this.position.getX(),this.position.getY(),8,getColor(line.getId())));
    }

    private Color getColor(String lineName) {
        switch (lineName) {
            case "line1":
                return Color.RED;
            case "line2":
                return Color.BLUE;
            case "line3":
                return Color.GREEN;
        }
        return null;
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public void move(Coordinate coordinates) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinates.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinates.getY() - position.getY() + shape.getTranslateY());
        }
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        Coordinate coords = path.getCoordinateDistance(distance);
        move(coords);
        position = coords;
    }

    public int getDistance() {
        return this.distance;
    }

    public Path getPath() {
        return this.path;
    }

}
