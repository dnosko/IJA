package gui;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.Coordinate;

import java.util.Arrays;
import java.util.List;

public class StreetGui implements Drawable {
    private String name;
    private Coordinate start;
    private Coordinate end;

    public StreetGui(String name, Coordinate start, Coordinate end){
       this.name = name;
       this.start = start;
       this.end = end;
    }

    @Override
    public List<Shape> getGUI() {
        return Arrays.asList(
                new Line(this.start.getX(),this.start.getY(),this.end.getX(),this.end.getY())
               // new Text(Math.abs(this.start.getX()+this.end.getX()/2),Math.abs(this.start.getY()+this.end.getY()/2),this.name)
        );
    }
}
