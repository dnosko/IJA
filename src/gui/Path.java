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

        Coordinate a = null;
        Coordinate b = null;
        for (int i = 0; i < path.size() -1;i++) {
            a = path.get(i);
            b = path.get(i+1);

            if (length + getDistanceBetweenPoints(a,b) >= distance)
                break;

            length += getDistanceBetweenPoints(a,b);
        }
        if (a == null || b == null)
            return null;
        double distance_driven = (distance + length) / getDistanceBetweenPoints(a,b);
        return new Coordinate(a.getX() + (b.getX()- a.getX())* distance_driven,
                a.getY() + (b.getY()- a.getY())* distance_driven);
    }

    private double getDistanceBetweenPoints(Coordinate a, Coordinate b){
        return Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY() - b.getY(),2));
    }
}
