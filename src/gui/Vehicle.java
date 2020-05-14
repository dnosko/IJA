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
    private Color color;
    private Line line;
    private Itinerary it;
    private int departure;

    public Vehicle(Line line, double speed, Path path, int departure) {
        this.departure = departure;
        this.line = line;
        this.stops = line.getStops();
        this.position = this.stops.get(0).getCoordinate(); //first position
        this.speed = speed;
        this.path = path;
        this.color = line.getColor();
        gui = new ArrayList<>();
        gui.add(new Circle(this.position.getX(),this.position.getY(),8,color));

        gui.addAll(path.getGUI()); //add path
        it = new Itinerary(this);
    }

    public Itinerary getItinerar() {
        return it;
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
        Coordinate coords = path.getCoordinateDistance(distance);
        move(coords);
        position = coords;
        it.updateDistance(distance);
    }

    public int getDistance() {
        return this.distance;
    }

    public Path getPath() {
        return this.path;
    }

    public Line getLine() {
        return line;
    }

    public int getDeparture() {
        return this.departure;
    }

    public void updateDeparture() {
        this.departure = this.departure-distance;
        it.updateDeparture(this.departure);
    }
}
