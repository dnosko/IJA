package model;

import java.util.List;

public class StreetMap implements Street {

    private String id;
    private List<Coordinate> coordinates;
    private List<Stop> stops;

    public StreetMap(String id, List<Coordinate> coordinates, List<Stop> stops) {
        this.id = id;
        this.coordinates = coordinates;
        this.stops = stops; // if no stops than empty list
    }

    @Override
    public String getId () {
        return this.id;
    }

    @Override
    public List<Coordinate> getCoordinates () {
        return this.coordinates;
    }

    @Override
    public List<Stop> getStops () {
        return this.stops;
    }

    @Override
    public Coordinate start () {
        return this.coordinates.get(0);
    }

    @Override
    public Coordinate end () {
        return this.coordinates.get(coordinates.size()-1);
    }
}