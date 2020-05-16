package model;

import gui.StreetGui;
import java.util.List;

public class Street {

    private final String id;
    private final List<Coordinate> coordinates;
    private int traffic = 0;
    public StreetGui streetGui;

    public Street(String id, List<Coordinate> coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public String getId () {
        return this.id;
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

    public StreetGui getStreetGui() {
        return this.streetGui;
    }
}