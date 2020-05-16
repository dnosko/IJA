package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;
import model.Coordinate;
import model.Street;
import java.util.Collections;
import java.util.List;
/**
 * Class representing drawable street on the map.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class StreetGui implements Drawable {
    private final Coordinate start;
    private final Coordinate end;
    private final Street street;
    private final Line line;

    /**
     * Method creates a new instance.
     * @param start Start coordinate of street.
     * @param end End coordinate of street.
     * @param street Street to be drawn.
     */
    public StreetGui(Coordinate start, Coordinate end, Street street){
        this.start = start;
        this.end = end;
        this.street = street;
        this.line = setLine();
        street.setStreetGui(this);
    }

    /**
     * @return Return new line.
     */
    private Line setLine() {
        Line line = new Line(this.start.getX(),this.start.getY(),this.end.getX(),this.end.getY());
        line.setStrokeWidth(7);
        line.setStroke(Color.SILVER);
        return line;
    }

    /**
     * @return List of drawable shapes representing stop.
     */
    @Override
    public List<Shape> getGUI() {
        return Collections.singletonList(line);
    }

    /**
     * @return street instance.
     */
    public Street getStreet() {
        return street;
    }
}
