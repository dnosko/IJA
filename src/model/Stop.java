package model;

public class Stop {

    private String id;
    private Coordinate coordinate;
    private Street street;

    public Stop(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public void setStreet (Street street) {
        this.street = street;
    }

    public String getId () {
        return this.id;
    }

    public Coordinate getCoordinate () {
        return this.coordinate;
    }

    public Street getStreet () {
        return this.street;
    }
}