package model;

/**
 * Class representing point on map as coordinates with x and y value
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class Coordinate {

    private final double x;
    private final double y;

    /**
     * Constructor
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter of 'x'
     *
     * @return x-coordinate
     */
    public double getX () {
        return this.x;
    }

    /**
     * Getter of 'y'
     *
     * @return y-coordinate
     */
    public double getY () {
        return this.y;
    }
}
