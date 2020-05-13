package gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import model.*;


import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

public class ControllerGui {
    @FXML
    private AnchorPane content;
    @FXML
    private TextField setTime, changeTimeSpeed;
    @FXML
    private TextArea clock;

    private List<Vehicle> busElements = new ArrayList<>();

    private DataHolder holder;

    private Timer timer;
    private LocalTime time = LocalTime.now();

    @FXML
    /* sets time */
    private void onTimeChange() {
        try {
            time = LocalTime.parse(setTime.getText());
        }
        catch(DateTimeException ex) {
            setTime.clear();
            setTime.replaceSelection("Invalid time");
        }
        showTime();
    }

    @FXML
    /* changes time's speed */
    public void onTimeChangeSpeed() {
        try {
            float scale = Float.parseFloat(changeTimeSpeed.getText());
            timer.cancel();
            startTime(scale);
        }
        catch (IllegalArgumentException e) {
            changeTimeSpeed.replaceSelection("Must be a positive number.");
        }
    }

    @FXML
    public void showTime(){
        clock.clear();
        clock.setText(time.toString());
    }

    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        System.out.println("testscroll");
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    @FXML
    private void onZoo(KeyEvent event) {
        event.consume();
        System.out.println("Test" + event.getCode());
        if ( event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyCode code = event.getCode();
           if (code == KeyCode.ADD || code == KeyCode.UP) {
                content.setScaleX(1.1 * content.getScaleX());
                content.setScaleY(1.1 * content.getScaleY());
           }
           else if (code == KeyCode.SUBTRACT || code == KeyCode.DOWN) {
               content.setScaleX(0.9 * content.getScaleX());
               content.setScaleY(0.9 * content.getScaleY());
           }
           content.layout();
        }
    }

    private void setVehicleElements(List<Vehicle> elements){
        for (Vehicle vehicle : elements) {
            content.getChildren().addAll(vehicle.getGUI());
        }
    }

    public void setMapBase() {
        List<Drawable> elements = new ArrayList<>();

        /* create street elements */
        for (Street str : holder.getStreets()) {
            elements.add(new StreetGui(str.getId(),str.start(),str.end()));
        }

        /* create stop elements */
        for (Stop stop : holder.getStops()) {
            elements.add(new StopGui(stop.getId(),stop.getCoordinate()));
        }

        this.setBaseElements(elements);
    }

    private void setBaseElements(List<Drawable> elements) {
        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
        }
    }

    public void startTime(float scale) {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        time = time.plusSeconds(1);
                        for (Vehicle vehicle : busElements) {
                            vehicle.update(time);
                        }
                        showTime();
                        activateBuses();
                        deactivateBuses();
                    }
                });
            }
        }, 0, (long)(1000/scale));
    }

    private void activateBuses() {
        List<Vehicle> elements = new ArrayList<>();
        for (Line line : this.holder.getLines()) {
            if ( line.getBusesTimes().contains(time.get(ChronoField.MINUTE_OF_DAY)) && time.get(ChronoField.SECOND_OF_MINUTE) <= 1 ) {
                List<Stop> StopsLine = line.getStops();
                List<Coordinate> pathCoords = new ArrayList<>();

                List<Street> StreetLine = line.getStreets();
                pathCoords.add(StopsLine.get(0).getCoordinate()); //first stop
                for (Street str : StreetLine) {
                    // if its last street in Line, get only beginning of street
                    if (str.equals(StreetLine.get(StreetLine.size() - 1))) {
                        pathCoords.add(str.start());
                        continue;
                    }
                    //if its first street in line get only end of street
                    if (str.equals(StreetLine.get(0))) {
                        pathCoords.add(str.end());
                        continue;
                    }
                    pathCoords.add(str.start());
                    pathCoords.add(str.end());
                }
                pathCoords.add(StopsLine.get(StopsLine.size() - 1).getCoordinate()); //last stop
                elements.add(new Vehicle(line, 1, new Path(pathCoords)));
            }
        }

        if ( ! elements.isEmpty() ) {
            this.busElements.addAll(elements);
            this.setVehicleElements(elements);
        }
    }

    private void deactivateBuses() {
        List<Vehicle> vehiclesToRemove = new ArrayList<>();

        for ( Vehicle vehicle : this.busElements ) {
            if (vehicle.getDistance() > vehicle.getPath().getPathsize()) {
                vehiclesToRemove.add(vehicle);
                content.getChildren().remove(vehicle.getGUI().get(0));
            }
        }

        this.busElements.removeAll(vehiclesToRemove);
    }

    public void setHolder(DataHolder holder) {
        this.holder = holder;
    }
}