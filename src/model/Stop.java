package model;

/**
 * Class representing stop on map
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class Stop {

    private final String id;
    private final Coordinate coordinate;

    /**
     * Constructor
     *
     * @param id Stop's identifier
     * @param coordinate Stop's coordinate
     */
    public Stop(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    /**
     * Getter of 'id'
     *
     * @return Stop's id
     */
    public String getId () {
        return this.id;
    }

    /**
     * Getter of 'coordinate'
     *
     * @return Line's coordinate
     */
    public Coordinate getCoordinate () {
        return this.coordinate;
    }
}