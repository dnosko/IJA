/**
 * Interface representing drawable object on map.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */

package gui;

import javafx.scene.shape.Shape;

import java.util.List;

public interface Drawable {
    List<Shape> getGUI();
}