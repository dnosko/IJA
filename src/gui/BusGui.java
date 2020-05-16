package gui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Coordinate;
import model.Line;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing drawable bus on the map.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class BusGui implements Drawable {

    private int distance = 0;
    private final double speed;
    private Coordinate position;
    private final List<Shape> gui;
    private final Path path;
    private final Line line;
    private final Itinerary it;
    private int departure;

    /**
     * Creates a new instance.
     * @param line Line of the bus.
     * @param speed Current speed
     * @param path Path of the bus.
     * @param departure Time of departure.
     */
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

    /**
     * @return returns itinerary of the bus.
     */
    public Itinerary getItinerary() {
        return it;
    }

    /**
     * @return returns list of drawable objects.
     */
    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    /**
     * Method translates bus object.
     * @param coordinates new position.
     */
    public void move(Coordinate coordinates) {
        for (Shape shape : gui) {
            if (shape.getTypeSelector().equals("Line"))
                continue;
            shape.setTranslateX(coordinates.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinates.getY() - position.getY() + shape.getTranslateY());
        }
    }

    /**
     * Method updates position of the bus.
     */
    public void update() {
        distance += speed;
        Coordinate coords = path.getCoordinateDistance(distance);
        move(coords);
        position = coords;
        it.updateDistance(distance);
    }

    /**
     * @return returns already driven distance.
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * @return returns path of the bus.
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * @return returns line of the bus.
     */
    public Line getLine() {
        return line;
    }

    /**
     * @return returns departure time.
     */
    public int getDeparture() {
        return this.departure;
    }

    /**
     * Method updates departure time.
     */
    public void updateDeparture() {
        this.departure = this.departure-distance;
        it.updateDeparture(this.departure);
    }
}
