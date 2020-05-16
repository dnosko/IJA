/*
TODO
    ked sa klikne na mapu -> NullPointerException
    scroll na mape s koleckom iba odzoomuje??
 */

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
    private TextField setTime, changeTimeSpeed, TextFieldTraffic;
    @FXML
    private TextArea clock;
    @FXML
    private Pane canvas;

    private final List<BusGui> busElements = new ArrayList<>();

    private int longestPathLength = 0;

    private DataHolder holder;

    private Street selectedStreet = null;

    private Timer timer;
    private LocalTime time = LocalTime.now();
    private static int zoomInXth = 0;

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
        this.resetBuses();
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
    public void onTrafficSet() {
        try {
            int traffic = Integer.parseInt(TextFieldTraffic.getText());

            if ( this.selectedStreet == null ) {
                TextFieldTraffic.replaceSelection("Street not selected.");
            }
            else {
                this.selectedStreet.setTraffic(traffic);
                this.resetBuses();
            }
        }
        catch (IllegalArgumentException e) {
            TextFieldTraffic.replaceSelection("Must be a positive integer number.");
        }
    }

    private void setSelectedStreet(StreetGui street) {
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    removeLines();
                    selectedStreet = street.getStreet();
                    street.getGUI().get(0).setStroke(Color.GOLD);
                }
                catch (IndexOutOfBoundsException exception) {
                    System.out.println("INDEX OUT OF BOUNDS");
                }
            }
        };
        //Adding event Filter
        street.getGUI().get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @FXML
    public void showTime(){
        clock.clear();
        clock.setText(LocalTime.of(time.getHour(),time.getMinute(),time.getSecond()).toString());
    }

    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
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
               zoomInXth++;
               content.setScaleX(1.1 * content.getScaleX());
               content.setScaleY(1.1 * content.getScaleY());
           }
           else if (code == KeyCode.SUBTRACT || code == KeyCode.DOWN) {
               zoomInXth--;
               content.setScaleX(0.9 * content.getScaleX());
               content.setScaleY(0.9 * content.getScaleY());
           }
           // move the object
           if (zoomInXth > 0) {
               content.setTranslateX(100* zoomInXth);
               content.setTranslateY(100* zoomInXth);
            }
           else {
               content.setTranslateX(0);
               content.setTranslateY(0);
           }
           content.layout();
        }
    }

    private void setVehicleElements(List<BusGui> elements){
        for (BusGui busGui : elements) {
            content.getChildren().addAll(busGui.getGUI());
        }
    }

    public void setMapBase() {
        List<Drawable> elements = new ArrayList<>();

        /* create street elements */
        for (Street street : holder.getStreets()) {
            StreetGui streetGui = new StreetGui(street.start(), street.end(), street);
            setSelectedStreet(streetGui);
            elements.add(streetGui);
        }

        /* create stop elements */
        for (Stop stop : holder.getStops()) {
            elements.add(new StopGui(stop.getId(), stop.getCoordinate()));
        }

        this.setBaseElements(elements);
    }

    private void setBaseElements(List<Drawable> elements) {
        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
        }
    }

    private void unsetSelectedStreet() {
        try {
            if ( this.selectedStreet.getTraffic() == 0 ) {
                selectedStreet.getStreetGui().getGUI().get(0).setStroke(Color.SILVER);
            }
            else {
                selectedStreet.getStreetGui().getGUI().get(0).setStroke(Color.RED);
            }
            selectedStreet = null;
        }
        catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
    }

    
    public void removeLines(MouseEvent event) {
        event.consume();
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            removeLines();
            unsetSelectedStreet();
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
                    for (BusGui busGui : busElements) {
                        busGui.update();
                        showItinerary(busGui);
                    }
                    showTime();
                    activateBuses();
                    deactivateBuses();
                });
            }
        }, 0, (long)(1000/scale));

        for ( model.Line line : holder.getLines() ) {
            if ( this.longestPathLength < line.getPathLength() ) {
                this.longestPathLength = line.getPathLength();
            }
        }
    }

    private void activateBuses() {

        List<BusGui> elements = new ArrayList<>();

        for (model.Line line : this.holder.getLines()) {
            if ( line.getBusesTimes().contains(time.get(ChronoField.MINUTE_OF_DAY)) && time.get(ChronoField.SECOND_OF_MINUTE) == 1 ) {
                /* bus is starting right now */
                elements.add(new BusGui(line, 1, new Path(createPathCoords(line), line),time.toSecondOfDay()));
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
        List<BusGui> vehiclesToRemove = new ArrayList<>();

        for ( BusGui busGui : this.busElements ) {
            if (busGui.getDistance() > busGui.getPath().getPathSize()) {
                /* bus finished */
                for (int i = 0; i < busGui.getGUI().size()-1; i++) {
                    if (busGui.getGUI().get(i+1).getTypeSelector().equals("Line"))
                        busGui.getGUI().get(i+1).setStroke(Color.TRANSPARENT);
                }
                vehiclesToRemove.add(busGui);
                content.getChildren().remove(busGui.getGUI().get(0));
            }
        }

        this.busElements.removeAll(vehiclesToRemove);
    }

    public void activateActiveBuses (int offset) {

        final int SECOND_BEFORE_MIDNIGHT = 86399;
        List<BusGui> elements = new ArrayList<>();

        for ( model.Line line : this.holder.getLines() ) {
            for ( int busTime : line.getBusesTimes() ) {
                if ( busTime * 60 > (offset == 0 ? this.time.toSecondOfDay() : SECOND_BEFORE_MIDNIGHT) - line.getPathLength() + offset && busTime * 60 < (offset == 0 ? this.time.toSecondOfDay() : SECOND_BEFORE_MIDNIGHT) ) {
                    /* bus is active on road right now */

                    // create new bus and initialize his position as his starting position
                    BusGui busGui = new BusGui(line, 1, new Path(createPathCoords(line), line), time.toSecondOfDay());
                    elements.add(busGui);

                    for ( int i = busTime * 60 ; i <= (offset == 0 ? this.time.toSecondOfDay() : SECOND_BEFORE_MIDNIGHT + offset); i++ ) {
                        /* catch up bus position from his starting position to actual time position */
                        busGui.update();
                    }

                    busGui.updateDeparture();
                }
            }
        }

        this.busElements.addAll(elements);
        this.setVehicleElements(elements);
    }

    public void setHolder(DataHolder holder) {
        this.holder = holder;
    }

    private void showItinerary(BusGui busGui) {
        Itinerary it = busGui.getItinerary();

        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    removeLines();
                    canvas =  it.createItinerary(canvas);
                    canvas.setVisible(true);

                    for (int i = 0; i < (busGui.getPath().getGUI().size()); i++) {
                        busGui.getGUI().get(i).setStroke(busGui.getLine().getColor());
                    }
                }
                catch (IndexOutOfBoundsException exception) {
                    //System.out.println("INDEX OUT OF BOUNDS");
                }
            }
        };
        //Adding event Filter
        busGui.getGUI().get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void resetBuses() {

        /* deactivate all buses */
        for ( BusGui busGui : this.busElements ) {
            content.getChildren().remove(busGui.getGUI().get(0));
        }
        this.busElements.clear();

        /* activate all buses */
        this.activateActiveBuses(0);

        /* Activate buses before midnight if time was set close after midnight and not all buses starting before midnight already finished */
        if ( this.time.toSecondOfDay() - this.longestPathLength < 0) {
            this.activateActiveBuses(this.time.toSecondOfDay());
        }
    }
}