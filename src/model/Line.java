package model;

import java.util.List;

public interface Line {

    String getId();

    List<Street> getStreets();

    List<Stop> getStops();

    List<Bus> getBuses();
}
