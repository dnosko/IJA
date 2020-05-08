package model;

public class StopMap implements Stop {

    private String id;
    private Coordinate coordinate;
    private Street street;

    public StopMap(String id, Coordinate coordinate, Street street) {
        this.id = id;
        this.coordinate = coordinate;
        this.street = street;
    }

    @Override
    public String getId () {
        return this.id;
    }

    @Override
    public Coordinate getCoordinate () {
        return this.coordinate;
    }

    @Override
    public Street getStreet () {
        return this.street;
    }
}