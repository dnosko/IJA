package model;

import gui.StreetGui;

import java.util.List;

public class Street {

    private String id;
    private List<Coordinate> coordinates;
    private List<Stop> stops;
    private int traffic = 0;
    private StreetGui streetGui;

    public Street(String id, List<Coordinate> coordinates, List<Stop> stops) {
        this.id = id;
        this.coordinates = coordinates;
        this.stops = stops; // if no stops than empty list

        for ( Stop stop : stops ) {
            stop.setStreet(this);
        }
    }

    public String getId () {
        return this.id;
    }

    public List<Coordinate> getCoordinates () {
        return this.coordinates;
    }

    public List<Stop> getStops () {
        return this.stops;
    }

    public Coordinate start () {
        return this.coordinates.get(0);
    }

    public Coordinate end () {
        return this.coordinates.get(1);
    }

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    public void setStreetGui(StreetGui streetGui) {
        this.streetGui = streetGui;
    }
}