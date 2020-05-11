package model;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String id;
    private List<Street> streets;
    private List<Stop> stops;
    private List<Integer> busesTimes = new ArrayList<>(); // buses start times in minutes (schedule)

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

    public void setBusesTimes(Integer busesTime) {
        this.busesTimes.add(busesTime);
    }

    public List<Integer> getBusesTimes() {
        return this.busesTimes;
    }
}