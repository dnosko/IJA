package model;

import java.util.List;

public class Street {

    private String id;
    private List<Coordinate> coordinates;
    private List<Stop> stops;

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
}