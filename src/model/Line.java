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

    public Line(String id, List<Stop> stops, List<Street> streets) {
        this.id = id;
        this.stops = stops;
        this.streets = streets;
        this.setColor();
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

    public Color getColor() { return color; }
}