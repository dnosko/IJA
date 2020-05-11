package gui;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;

import java.util.List;

public class StreetGui implements Drawable {
    private List<Shape> gui;

    public StreetGui(float startx, float starty, float endx, float endy ){
        Line line = new Line();
        line.setStartX(startx);
        line.setStartY(starty);
        line.setEndX(startx);
        line.setEndY(starty);
        gui.add(line);
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }
}
