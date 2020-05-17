package gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Coordinate;
import model.Stop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing Itinerary of one bus.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
public class Itinerary {
    private final double X = 20;
    private final double Y = 60;
    private final Text name;
    private final int no_stops;
    private final List<Stop> listStop;
    private final Color color;
    private final double distance;
    private double actual_position;
    private double drivenDistanceFromOneStopToAnother;
    private final List<Double> distanceBetweenStops = new ArrayList<>();
    private final List<Double> timesInSec = new ArrayList<>();
    private final model.Line line;

    /**
     * Creates a new instance.
     * @param busGui Bus of itinerary.
     */
    public Itinerary(BusGui busGui){
        this.name = this.createText(50,30, busGui.getLine().getId(),15);
        this.line = busGui.getLine();

        this.listStop = busGui.getLine().getStops();
        this.no_stops = busGui.getLine().getStops().size();
        this.timesInSec.add((double) busGui.getDeparture());

        this.color = busGui.getLine().getColor();
        this.distance = (no_stops-1)*20;
        this.actual_position =  Y;
    }

    /**
     * @return New base line of itinerary.
     */
    private Line createLine() {
        Line line = new Line();
        line.setStartX(X); line.setEndX(X);
        line.setStartY(Y); line.setEndY(distance+Y);
        line.setStroke(this.color);

        return line;
    }

    /**
     * @param X X position
     * @param Y Y position
     * @param write_text Text to be written.
     * @param size Size of text.
     * @return New text.
     */
    private Text createText(double X, double Y, String write_text, int size) {
        Text text = new Text(X,Y,write_text);
        text.setFont(Font.font("Times New Roman", FontWeight.NORMAL,FontPosture.REGULAR,size));
        return text;
    }

    /**
     * @param Y position on itinerary.
     * @param radius Radius of circle.
     * @param fill Filling of circle.
     * @return New circle on itinerary.
     */
    private Circle createCircle(double Y, double radius, Color fill) {
        Circle circle = new Circle(X, Y, radius);
        circle.setStroke(this.color);
        circle.setFill(fill);
        return circle;
    }

    /**
     * Method creates itinerary of bus.
     * @param it Pane to draw on.
     * @return Itinerary of bus.
     */
    public Pane createItinerary(Pane it){
        it.setVisible(false);
        it.getChildren().addAll(name,this.createLine());
        for (int i =0; i < no_stops; i++) {
            Circle stop = createCircle(Y+i*20,2.5, this.color);
            Text stop_name = createText(X+20,Y+i*20, listStop.get(i).getId(),11); // write stop name
            Text stop_time = createText(X+70, Y+i*20,this.convertSecondsToHH_MM(timesInSec.get(i)),11); //write time of departure to stop
            it.getChildren().addAll(stop_name,stop_time,stop);
        }

        it.getChildren().add(createCircle(this.actual_position,2.8,Color.WHITE));

        it.setOpacity(100);

        return it;
    }

    /**
     * Method updates actual position of bus on itinerary.
     * @param distance Actual distance of bus.
     */
    public void updateDistance(double distance){
        int nthStop = whereIsVehicle(distance);
        getStopTime();
        this.actual_position = Y + (20*(nthStop)) + ((drivenDistanceFromOneStopToAnother / distanceBetweenStops.get(nthStop)) * 20);
    }

    /**
     * Method converts time in seconds to HH:MM format.
     * @param seconds Time in seconds.
     * @return Time in HH:MM format.
     */
    private String convertSecondsToHH_MM(double seconds){
       int hour = (int)seconds/3600;
       double minute = ((seconds/3600.00) - hour)* 60;
       if (minute < 10)
            return hour + ":0" + (int)(minute);
       else
            return hour + ":" + (int)(minute);
    }

    /**
     * Method calculates time of arrival at each stop.
     */
    private void getStopTime(){
        double size;
        for (int i = 0; i < listStop.size()-1; i++) {
                Coordinate a = listStop.get(i).getCoordinate();
                Coordinate b = listStop.get(i+1).getCoordinate();
                Path stops = new Path(Arrays.asList(a,b), this.line);

                if (a.getX() == b.getX() || a.getY() == b.getY()) // points lie on the same line
                    size = stops.getDistanceBetweenPoints(a,b);
                else { // points hold right angle
                    Coordinate c = new Coordinate(a.getX(),b.getY());
                    size = stops.getDistanceBetweenPoints(a,c);
                    size += stops.getDistanceBetweenPoints(c,b);
                }

                this.timesInSec.add(this.timesInSec.get(i) + size);
                distanceBetweenStops.add(size);
        }
    }

    /** Method calculates position of bus.
     * @param drivenDistance Already driven distance of the bus.
     * @return Number of the last stop
     */
    private Integer whereIsVehicle(double drivenDistance){
        double stopDistance = 0;
        int i = 0;
        try {
            while (drivenDistance >= stopDistance) {
                this.drivenDistanceFromOneStopToAnother = drivenDistance - stopDistance;
                stopDistance += distanceBetweenStops.get(i);
                i++;
            }
            return i - 1;
        }
        catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    /** Method updates departure of bus,
     * @param time Time of departure.
     */
    public void updateDeparture(double time) {
        this.timesInSec.set(0,time);
    }

}
