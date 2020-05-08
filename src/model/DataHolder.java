package model;

import java.util.List;

public interface DataHolder {

    List<Stop> getStops();

    List<Street> getStreets();

    List<Line> getLines();

    List<Bus> getBuses();
}
