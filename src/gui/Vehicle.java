package gui;

import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.List;

public class Vehicle implements Drawable, TimeUpdate {
    private int distance = 0;
    private int speed = 1;


    @Override
    public List<Shape> getGUI() {
        return null;
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        // pridat podmienku iba do poslednej stop
        //Coordinate coords = path.getCoordinateByDistance(distance);

    }
}
