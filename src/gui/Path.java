package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Path implements Drawable  {
    private final List<Coordinate> path;
    private final List<Shape> gui = new ArrayList<>();
    private final model.Line line;

    public Path(List<Coordinate> path, model.Line line) {
        this.path = path;
        this.line = line;
    }

    public Coordinate getCoordinateDistance(double distance){
        double length = 0;
        double currentLength = 0;

        Coordinate a = null;
        Coordinate b = null;
        for (int i = 0; i < path.size() -1;i++) {
            a = path.get(i);
            b = path.get(i+1);
            currentLength = getDistanceBetweenPoints(a,b);
            if (length + getDistanceBetweenPoints(a,b) >= distance)
                break;

            length += getDistanceBetweenPoints(a,b);
        }
        if (a == null)
            return null;

        double distance_driven = (distance - length) / currentLength;
        return new Coordinate(a.getX() + (b.getX()- a.getX())* distance_driven,
                a.getY() + (b.getY()- a.getY())* distance_driven);
    }

    public double getDistanceBetweenPoints(Coordinate start, Coordinate end){

        /* calculate real distance */
        double distance;
        distance = Math.sqrt(Math.pow(start.getX()-end.getX(),2) + Math.pow(start.getY() - end.getY(),2));

        /* find out which road are represented as those coordinates (if any), then add traffic delay */
        for ( model.Street street : this.line.getStreets() ) {
            if ( (street.start().getX() == start.getX() && street.start().getY() == start.getY() && street.end().getX() == end.getX() && street.end().getY() == end.getY()) ||
                 (street.start().getX() == end.getX() && street.start().getY() == end.getY() && street.end().getX() == start.getX() && street.end().getY() == start.getY())
            ) {
                distance += street.getTraffic();
            }
        }

        /* if this coordinates represent first street of line route, they need to get their own handle */
        if ( this.path.get(0).getX() == start.getX() && this.path.get(0).getY() == start.getY() || this.path.get(0).getX() == end.getX() && this.path.get(0).getY() == end.getY() ) {
            distance += this.line.getStreets().get(0).getTraffic();
        }

        /* if this coordinates represent last street of line route, they need to get their own handle */
        if ( this.path.get(this.path.size() - 1).getX() == start.getX() && this.path.get(this.path.size() - 1).getY() == start.getY() || this.path.get(this.path.size() - 1).getX() == end.getX() && this.path.get(this.path.size() - 1).getY() == end.getY() ) {
            distance += this.line.getStreets().get(this.line.getStreets().size() - 1).getTraffic();
        }

        return distance;
    }

    public double getPathSize(){
        double size = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            size += getDistanceBetweenPoints(path.get(i), path.get(i+1));
        }
        return size;
    }

    public List<Shape> getGUI() {
        for (int i = 0; i < path.size() - 1; i++) {
            Line line = new Line(this.path.get(i).getX(), this.path.get(i).getY(), this.path.get(i + 1).getX(), this.path.get(i + 1).getY());
            line.setStroke(Color.TRANSPARENT);
            line.setStrokeWidth(1);
            gui.add(line);
        }
        return gui;
    }
}
