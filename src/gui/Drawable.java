package gui;

import javafx.scene.shape.Shape;
import java.util.List;

/**
 * Interface representing drawable object on map.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public interface Drawable {
    List<Shape> getGUI();
}