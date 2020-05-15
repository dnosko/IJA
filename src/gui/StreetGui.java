package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;
import model.Coordinate;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import model.Street;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreetGui implements Drawable {
    private String name;
    private Coordinate start;
    private Coordinate end;
    private Street street;
    private Line line;

    public StreetGui(String name, Coordinate start, Coordinate end, Street street){
        this.name = name;
        this.start = start;
        this.end = end;
        this.street = street;
        street.setStreetGui(this);
        this.line = setLine();

    }

    private Line setLine() {
        Line line = new Line(this.start.getX(),this.start.getY(),this.end.getX(),this.end.getY());
        line.setStrokeWidth(7);
        line.setStroke(Color.SILVER);
        return line;
    }

    @Override
    public List<Shape> getGUI() {
        return Arrays.asList(line);
    }

    public Street getStreet() {
        return street;
    }
}
