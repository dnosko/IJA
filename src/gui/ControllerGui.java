package gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;
import java.time.temporal.ChronoField;

public class ControllerGui {
    @FXML
    public AnchorPane content;
    @FXML
    private TextField setTime, changeTimeSpeed;
    @FXML
    private TextArea clock;
    @FXML
    private Pane canvas;

    private List<Vehicle> busElements = new ArrayList<>();

    private DataHolder holder;

    private Timer timer;
    private LocalTime time = LocalTime.now();
    private static int zoominXth = 0;

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
        this.deactivateAllBuses();
        this.activateActiveBuses();
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
               zoominXth++;
               content.setScaleX(1.1 * content.getScaleX());
               content.setScaleY(1.1 * content.getScaleY());
           }
           else if (code == KeyCode.SUBTRACT || code == KeyCode.DOWN) {
               zoominXth--;
               content.setScaleX(0.9 * content.getScaleX());
               content.setScaleY(0.9 * content.getScaleY());
           }
           // move the object
           if (zoominXth > 0) {
               content.setTranslateX(100*zoominXth);
               content.setTranslateY(100*zoominXth);
            }
           else {
               content.setTranslateX(0);
               content.setTranslateY(0);
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
    
    public void removeLines(MouseEvent event) {
        event.consume();
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            canvas.getChildren().clear();
            for (Drawable element : this.busElements) {
                for (int i = 0; i < element.getGUI().size(); i++) {
                    Shape el = element.getGUI().get(i);
                    if (el.getTypeSelector().equals("Line")) {
                        el.setStroke(Color.TRANSPARENT);
                    }
                }
            }
        }
    }

    public void removeLines() {
        canvas.getChildren().clear();
        for (Drawable element : this.busElements) {
            for (int i = 0; i < element.getGUI().size(); i++) {
                Shape el = element.getGUI().get(i);
                if (el.getTypeSelector().equals("Line")) {
                    el.setStroke(Color.TRANSPARENT);
                }
            }
        }
    }

    public void startTime(float scale) {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    time = time.plusSeconds(1);
                    for (Vehicle vehicle : busElements) {
                        vehicle.update(time);
                    }
                    showTime();
                    activateBuses();
                    deactivateBuses();
                });
            }
        }, 0, (long)(1000/scale));
    }

    private void activateBuses() {

        List<Vehicle> elements = new ArrayList<>();

        for (model.Line line : this.holder.getLines()) {
            if ( line.getBusesTimes().contains(time.get(ChronoField.MINUTE_OF_DAY)) && time.get(ChronoField.SECOND_OF_MINUTE) <= 1 ) {
                elements.add(new Vehicle(line, 1, new Path(createPathCoords(line))));
            }
        }

        if ( ! elements.isEmpty() ) {
            this.busElements.addAll(elements);
            this.setVehicleElements(elements);
        }
    }

    public List<Coordinate> createPathCoords(model.Line line) {
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

        return pathCoords;
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

    public void activateActiveBuses () {

        List<Vehicle> elements = new ArrayList<>();

        for (model.Line line : this.holder.getLines()) {
            for ( int busTime : line.getBusesTimes() ) {
                if ( busTime >= time.get(ChronoField.MINUTE_OF_DAY) - line.getPathLength() / 60 && busTime <= time.get(ChronoField.MINUTE_OF_DAY) ) {
                    Vehicle vehicle = new Vehicle(line, 1, new Path(createPathCoords(line)));
                    elements.add(vehicle);
                    showItinerar(vehicle);

                    for ( double i = time.get(ChronoField.MINUTE_OF_DAY) - (line.getPathLength() / 60) + (time.get(ChronoField.MINUTE_OF_DAY) - busTime) ; i < time.get(ChronoField.MINUTE_OF_DAY); i+=1.0/60.0 ) {
                        vehicle.update(time);
                    }
                }
            }
        }

        this.busElements.addAll(elements);
        this.setVehicleElements(elements);
    }

    private void deactivateAllBuses() {
        for ( Vehicle vehicle : this.busElements ) {
            content.getChildren().remove(vehicle.getGUI().get(0));
        }
        this.busElements.clear();
    }

    public void setHolder(DataHolder holder) {
        this.holder = holder;
    }

    public void showItinerar(Vehicle vehicle) {
        Itinerar it = new Itinerar(vehicle);
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println(vehicle.getPath().getGUI().size());
                try {
                    removeLines();
                    canvas =  it.createItinerar(canvas);
                    canvas.setVisible(true);

                    for (int i = 0; i < (vehicle.getPath().getGUI().size()); i++) {
                        System.out.println(i);
                        vehicle.getGUI().get(i).setStroke(vehicle.getLine().getColor());
                    }
                }
                catch (IndexOutOfBoundsException exception) {
                    System.out.println("INDEX OUT OF BOUNDS");
                }
            }
        };
        //Adding event Filter
        vehicle.getGUI().get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}