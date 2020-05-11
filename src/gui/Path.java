package gui;

import model.Coordinate;

import java.util.List;

public class Path {
    private List<Coordinate> path;

    public Path(List<Coordinate> path) {
        this.path = path;
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

    private double getDistanceBetweenPoints(Coordinate a, Coordinate b){
        return Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY() - b.getY(),2));
    }

    public double getPathsize(){
        double size = 0;
        for (int i =0; i < path.size() -1; i++) {
            size += getDistanceBetweenPoints(path.get(i), path.get(i+1));
        }
        return size;
    }
}
