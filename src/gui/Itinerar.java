package gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Stop;

import java.util.List;

public class Itinerar {
    private final double X = 20;
    private final double Y = 60;
    private Text name;
    private int no_stops;
    private List<Stop> liststop;
    private Color color;
    private double distance, actual_position, scale;
    Circle actual_pos;

    public Itinerar(Vehicle vehicle){
        this.name = this.createText(50,30,vehicle.getLine().getId(),15);

        this.liststop = vehicle.getLine().getStops();
        this.no_stops = vehicle.getLine().getStops().size();

        this.color = vehicle.getLine().getColor();
        this.distance = (no_stops-1)*20;
        this.scale = distance/vehicle.getLine().getPathLength();
        this.actual_position =  Y+ vehicle.getDistance()/scale;
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

    public Pane createItinerar(Pane it){
        it.setVisible(false);
        it.getChildren().add(name);
        it.getChildren().add(this.createLine());
        for (int i =0; i < no_stops; i++) {
            Circle circle = new Circle(X,Y+i*20,2.5);
            circle.setFill(this.color);
            Text stop_name = createText(X+20,Y+i*20,liststop.get(i).getId(),11);
            Text stop_time = createText(X+70, Y+i*20,"11:11",11);
            it.getChildren().addAll(stop_name,stop_time,circle);
        }
        actual_pos = new Circle(X,this.actual_position,2.8);
        actual_pos.setStroke(color);
        actual_pos.setFill(Color.WHITE);
        it.getChildren().add(actual_pos);

        it.setOpacity(100);

        return it;
    }

    public void updateDistance(double distance){
        this.actual_position = Y + distance*scale;
        //shape.setTranslateY(coordinates.getY() - position.getY() + shape.getTranslateY());
        //this.actual_pos.setTranslateY();
    }

}
