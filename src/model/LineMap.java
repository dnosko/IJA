package model;

import java.util.List;

public class LineMap implements Line {

    private String id;
    private List<Street> streets;
    private List<Stop> stops;
    private List<Bus> buses;

    public LineMap(String id, List<Street> streets, List<Stop> stops, List<Bus> buses) {
        this.id = id;
        this.streets = streets;
        this.stops = stops;
        this.buses = buses;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public List<Street> getStreets() {
        return this.streets;
    }

    @Override
    public List<Stop> getStops() {
        return this.stops;
    }

    @Override
    public List<Bus> getBuses() {
        return this.buses;
    }
}