package model;

import java.util.List;

public interface Street {

    String getId();

    List<Coordinate> getCoordinates();

    List<Stop> getStops();

    Coordinate start();

    Coordinate end();
}
