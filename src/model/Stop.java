package model;

public class Stop {

    private final String id;
    private final Coordinate coordinate;

    public Stop(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public String getId () {
        return this.id;
    }

    public Coordinate getCoordinate () {
        return this.coordinate;
    }
}