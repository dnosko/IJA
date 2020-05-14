package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;
import model.Coordinate;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import model.Street;

import java.util.Arrays;
import java.util.List;

public class StreetGui implements Drawable {
    private String name;
    private Coordinate start;
    private Coordinate end;
    private Street street;

    public StreetGui(String name, Coordinate start, Coordinate end, Street street){
        this.name = name;
        this.start = start;
        this.end = end;
        this.street = street;
        street.setStreetGui(this);
    }

    @Override
    public List<Shape> getGUI() {
        javafx.scene.shape.Line line = new javafx.scene.shape.Line(this.start.getX(),this.start.getY(),this.end.getX(),this.end.getY());
        return Arrays.asList(
                line
               // new Text(this.start.getX(),this.start.getY(),this.name)
        );
    }

    @Override
    public String getType() {
        return "Street";
    }

    public Street getStreet() {
        return street;
    }
}
