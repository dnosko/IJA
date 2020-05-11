package model;

import java.util.List;

public class Line {

    private String id;
    private List<Street> streets;
    private List<Stop> stops;

    public Line(String id, List<Stop> stops, List<Street> streets) {
        this.id = id;
        this.stops = stops;
        this.streets = streets;
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
}