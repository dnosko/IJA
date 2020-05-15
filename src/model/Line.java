package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String id;
    private List<Street> streets;
    private List<Stop> stops;
    private Color color;
    private List<Integer> busesTimes = new ArrayList<>(); // buses start times in minutes (schedule)
    private int pathLength;

    public Line(String id, List<Stop> stops, List<Street> streets) {
        this.id = id;
        this.stops = stops;
        this.streets = streets;
        this.setColor();

        double pathLength = 0;
        for ( Street street : this.streets ) {
            double x1 = street.start().getX();
            double x2 = street.end().getX();
            double y1 = street.start().getY();
            double y2 = street.end().getY();

            pathLength += Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)) + street.getTraffic();
        }
        this.pathLength = (int) pathLength;
    }


    public String getId() {
        return this.id;
    }

    public List<Street> getStreets() {
        return this.streets;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public void setBusesTimes(Integer busesTime) {
        this.busesTimes.add(busesTime);
    }

    public List<Integer> getBusesTimes() {
        return this.busesTimes;
    }

    public int getPathLength() {
        return this.pathLength;
    }

    private void setColor() {
        switch (this.id) {
            case "line1":
                this.color = Color.RED;
                break;
            case "line2":
                this.color = Color.BLUE;
                break;
            case "line3":
                this.color = Color.GREEN;
                break;
        }
    }

    public Color getColor() {
        return color;
    }
}