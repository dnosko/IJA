package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Path implements Drawable  {
    private List<Coordinate> path;
    private List<Shape> gui = new ArrayList<>();

    public Path(List<Coordinate> path) {
        this.path = path;
    }

    public Coordinate getPoint(int nth) {
        return path.get(nth);
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
        if (a == null || b == null)
            return null;

        double distance_driven = (distance - length) / currentLength;
        return new Coordinate(a.getX() + (b.getX()- a.getX())* distance_driven,
                a.getY() + (b.getY()- a.getY())* distance_driven);
    }

    public double getDistanceBetweenPoints(Coordinate a, Coordinate b){
        return Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY() - b.getY(),2));
    }

    public double getPathsize(){
        double size = 0;
        for (int i =0; i < path.size() -1; i++) {
            size += getDistanceBetweenPoints(path.get(i), path.get(i+1));
        }
        return size;
    }

    public List<Shape> getGUI() {
        for(int i =0; i < path.size()-1; i++) {
            Line line = new Line(this.path.get(i).getX(),this.path.get(i).getY(),this.path.get(i+1).getX(),this.path.get(i+1).getY());
            line.setStroke(Color.TRANSPARENT);
            line.setStrokeWidth(1);
            gui.add(line);
        }
        return gui;
    }

    @Override
    public String getType() {
        return "Path";
    }

}
