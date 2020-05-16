package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing line with route (sequence of streets) and sequence of stops
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class Line {

    private final String id;
    private final List<Street> streets;
    private final List<Stop> stops;
    private final Color color;
    private final List<Integer> busesTimes = new ArrayList<>(); // buses start times in minutes (schedule)
    private final int pathLength;

    /**
     * Constructor, assign necessary data, calculate path and set color
     *
     * @param id Identifier of line
     * @param stops List of stops on line's route
     * @param streets List of streets representing line's route
     */
    public Line(String id, List<Stop> stops, List<Street> streets) {
        this.id = id;
        this.stops = stops;
        this.streets = streets;

        /* set color */
        if (this.id.equals("line1")) {
            this.color = Color.RED;
        } else if (this.id.equals("line2")) {
            this.color = Color.BLUE;
        } else {
            this.color = Color.GREEN;
        }

        /* calculate length of path */
        double pathLength = 0;
        for ( Street street : this.streets ) {
            double x1 = street.getStart().getX();
            double x2 = street.getEnd().getX();
            double y1 = street.getStart().getY();
            double y2 = street.getEnd().getY();

            pathLength += Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)) + street.getTraffic();
        }
        this.pathLength = (int) pathLength;
    }

    /**
     * Getter of 'id'
     *
     * @return Line's id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter of 'streets'
     *
     * @return Line's streets
     */
    public List<Street> getStreets() {
        return this.streets;
    }

    /**
     * Getter of 'stops'
     *
     * @return Line's stops
     */
    public List<Stop> getStops() {
        return this.stops;
    }

    /**
     * Appending setter of 'busesTimes', appends value to the end of the busesTimes
     *
     * @param busesTime New bus time
     */
    public void setBusesTimes(Integer busesTime) {
        this.busesTimes.add(busesTime);
    }

    /**
     * Getter of 'busesTimes'
     *
     * @return List of start times of buses of this line
     */
    public List<Integer> getBusesTimes() {
        return this.busesTimes;
    }

    /**
     * Getter of 'pathLength'
     *
     * @return Sum of all streets lengths round down to integer
     */
    public int getPathLength() {
        return this.pathLength;
    }

    /**
     * Getter of 'color'
     *
     * @return Line's color
     */
    public Color getColor() {
        return color;
    }
}