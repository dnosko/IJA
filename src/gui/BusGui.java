package gui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Coordinate;
import model.Line;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BusGui implements Drawable {

    private int distance = 0;
    private final double speed;
    private Coordinate position;
    private final List<Shape> gui;
    private final Path path;
    private final Line line;
    private final Itinerary it;
    private int departure;

    public BusGui(Line line, double speed, Path path, int departure) {
        this.departure = departure;
        this.line = line;
        this.position = this.line.getStops().get(0).getCoordinate(); //first position
        this.speed = speed;
        this.path = path;

        gui = new ArrayList<>();
        gui.add(new Circle(this.position.getX(),this.position.getY(),8, this.line.getColor()));
        gui.addAll(path.getGUI()); //add path

        it = new Itinerary(this);
    }

    public Itinerary getItinerary() {
        return it;
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public void move(Coordinate coordinates) {
        for (Shape shape : gui) {
            if (shape.getTypeSelector().equals("Line"))
                continue;
            shape.setTranslateX(coordinates.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinates.getY() - position.getY() + shape.getTranslateY());
        }
    }

    public void update() {
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
