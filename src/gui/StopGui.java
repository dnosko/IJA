package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinate;

import java.util.Arrays;
import java.util.List;

public class StopGui implements Drawable{
    private String name;
    private Coordinate position;

    public StopGui(String name, Coordinate position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public List<Shape> getGUI() {
        return Arrays.asList(
                new Circle(this.position.getX(),this.position.getY(),8, Color.BLACK),
                new Text(this.position.getX(),this.position.getY() - 7,this.name)
        );
    }

    @Override
    public String getType() {
        return "Stop";
    }
}
