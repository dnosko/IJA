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

public class Itinerary {
    private final double X = 20;
    private final double Y = 60;
    private Text name;
    private int no_stops;
    private List<Stop> liststop;
    private Color color;
    private double distance, actual_position, drivenDistanceFromOneStopToAnother;
    private Circle actual_pos;
    private List<Double> distanceBetweenStops = new ArrayList<>();
    private List<Double> timesinsec = new ArrayList<>();
    private model.Line line;

    public Itinerary(Vehicle vehicle){
        this.name = this.createText(50,30,vehicle.getLine().getId(),15);
        this.line = vehicle.getLine();

        this.liststop = vehicle.getLine().getStops();
        this.no_stops = vehicle.getLine().getStops().size();
        this.timesinsec.add((double)vehicle.getDeparture());

        this.color = vehicle.getLine().getColor();
        this.distance = (no_stops-1)*20;
        this.actual_position =  Y;
    }

    private Line createLine() {
        Line line = new Line();
        line.setStartX(X); line.setEndX(X);
        line.setStartY(Y); line.setEndY(distance+Y);
        line.setStroke(this.color);

        return line;
    }

    private Text createText(double X, double Y, String write_text, int size) {
        Text text = new Text(X,Y,write_text);
        text.setFont(Font.font("Times New Roman", FontWeight.NORMAL,FontPosture.REGULAR,size));
        return text;
    }

    private Circle createCircle(double X, double Y, double radius, Color fill) {
        Circle circle = new Circle(X,Y,radius);
        circle.setStroke(this.color);
        circle.setFill(fill);
        return circle;
    }

    public Pane createItinerary(Pane it){
        it.setVisible(false);
        it.getChildren().addAll(name,this.createLine());
        for (int i =0; i < no_stops; i++) {
            Circle stop = createCircle(X,Y+i*20,2.5, this.color);
            Text stop_name = createText(X+20,Y+i*20,liststop.get(i).getId(),11);
            Text stop_time = createText(X+70, Y+i*20,this.convertSecondstoHH_MM(timesinsec.get(i)),11);
            it.getChildren().addAll(stop_name,stop_time,stop);
        }
        actual_pos = createCircle(X,this.actual_position,2.8,Color.WHITE);
        it.getChildren().add(actual_pos);

        it.setOpacity(100);

        return it;
    }

    public void updateDistance(double distance){
        int nthStop = whereIsVehicle(distance);
        getStopTime();
        this.actual_position = Y + (20*(nthStop)) + ((drivenDistanceFromOneStopToAnother / distanceBetweenStops.get(nthStop)) * 20);
    }

    private String convertSecondstoHH_MM(double seconds){
       int hour = (int)seconds/3600;
       double minute = ((seconds/3600.00) - hour)* 60;
       if (minute < 10)
            return hour + ":0" + (int)(minute);
       else
            return hour + ":" + (int)(minute);
    }

    private void getStopTime(){
        double size;
        for (int i = 0; i < liststop.size()-1;i++) {
                Coordinate a = liststop.get(i).getCoordinate();
                Coordinate b = liststop.get(i+1).getCoordinate();
                Path stops = new Path(Arrays.asList(a,b), this.line);

                if (a.getX() == b.getX() || a.getY() == b.getY()) // points lie on the same line
                    size = stops.getDistanceBetweenPoints(a,b);
                else { // points hold right angle
                    Coordinate c = new Coordinate(a.getX(),b.getY());
                    size = stops.getDistanceBetweenPoints(a,c);
                    size += stops.getDistanceBetweenPoints(c,b);
                }

                this.timesinsec.add(this.timesinsec.get(i) + size);
                distanceBetweenStops.add(size);
        }
    }

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


    public void updateDeparture(double time) {
        this.timesinsec.set(0,time);
    }

}
