package model;

import gui.StreetGui;
import java.util.List;

/**
 * Class representing street on map
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class Street {

    private final String id;
    private final Coordinate start;
    private final Coordinate end;
    private int traffic = 0;
    public StreetGui streetGui;

    /**
     * Constructor
     *
     * @param id Street's identifier
     * @param coordinates List containing street's start and end coordinate
     */
    public Street(String id, List<Coordinate> coordinates) {
        this.id = id;
        this.start = coordinates.get(0);
        this.end = coordinates.get(1);
    }

    /**
     * Getter of 'id'
     *
     * @return Streets's id
     */
    public String getId () {
        return this.id;
    }

    /**
     * Getter of 'start'
     *
     * @return start coordinate
     */
    public Coordinate getStart() {
        return this.start;
    }

    /**
     * Getter 'end'
     *
     * @return end coordinate
     */
    public Coordinate getEnd() {
        return this.end;
    }

    /**
     * Getter of 'traffic'
     *
     * @return stop's traffic
     */
    public int getTraffic() {
        return traffic;
    }

    /**
     * Setter of 'traffic'
     *
     * @param traffic new traffic value
     */
    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    /**
     * Setter of 'StreetGui'
     *
     * @param streetGui Street's StreetGui
     */
    public void setStreetGui(StreetGui streetGui) {
        this.streetGui = streetGui;
    }

    /**
     * Getter of 'StreetGui'
     *
     * @return Street's StreetGui
     */
    public StreetGui getStreetGui() {
        return this.streetGui;
    }
}