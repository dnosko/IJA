package model;

import java.util.List;

public class DataHolderMap implements DataHolder {

    private List<Stop> stops;
    private List<Street> streets;
    private List<Line> lines;
    private List<Bus> buses;

    public List<Stop> getStops() {
        return this.stops;
    }

    public List<Street> getStreets() {
        return this.streets;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public List<Bus> getBuses() {
        return this.buses;
    }
}
