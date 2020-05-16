/**
 * Class representing drawable street on the map.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */

package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;
import model.Coordinate;
import model.Street;
import java.util.Collections;
import java.util.List;

public class StreetGui implements Drawable {
    private final Coordinate start;
    private final Coordinate end;
    private final Street street;
    private final Line line;

    public StreetGui(Coordinate start, Coordinate end, Street street){
        this.start = start;
        this.end = end;
        this.street = street;
        this.line = setLine();
        street.setStreetGui(this);
    }

    private Line setLine() {
        Line line = new Line(this.start.getX(),this.start.getY(),this.end.getX(),this.end.getY());
        line.setStrokeWidth(7);
        line.setStroke(Color.SILVER);
        return line;
    }

    @Override
    public List<Shape> getGUI() {
        return Collections.singletonList(line);
    }

    public Street getStreet() {
        return street;
    }
}
