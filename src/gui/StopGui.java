package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinate;

import java.util.Arrays;
import java.util.List;

/**
 * Class representing drawable stop on the map.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class StopGui implements Drawable{
    private final String name;
    private final Coordinate position;

    /**
     * Method creates a new instance.
     * @param name Stops name.
     * @param position Coordinates of stop.
     */
    public StopGui(String name, Coordinate position) {
        this.name = name;
        this.position = position;
    }

    /**
     * @return List of drawable shapes representing stop.
     */
    @Override
    public List<Shape> getGUI() {

        return Arrays.asList(
                new Circle(this.position.getX(),this.position.getY(),8, Color.BLACK),
                new Text(this.position.getX(),this.position.getY() - 7,this.name)
        );
    }
}