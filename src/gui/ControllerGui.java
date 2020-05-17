package gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;
import java.time.temporal.ChronoField;

/**
 * Class representing controller which handles user interface.
 *
 * @author Andrej Pavlovič <xpavlo14@stud.fit.vutbr.cz>
 * @author Daša Nosková <xnosko05@stud.fit.vutbr.cz>
 */
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

    /**
     * Method sets time to time entered by user.
     */
    @FXML
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

    /**
     * Method fast-forwards time.
     */
    @FXML
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

    /**
     * This method is called whenever user confirm set traffic button
     */
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

    /**
     * Method sets street as selected on mouseclick.
     * @param street Clicked on street.
     */
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

    /**
     * Method shows time.
     */
    @FXML
    public void showTime(){
        clock.clear();
        clock.setText(LocalTime.of(time.getHour(),time.getMinute(),time.getSecond()).toString());
    }

    /**
     * Method zooms in and out the map.
     */
    @FXML
    private void onZoom(KeyEvent event) {
        event.consume();
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

    /**
     * Sets Buses elements to gui
     *
     * @param elements buses elements
     */
    private void setBusElements(List<BusGui> elements){
        for (BusGui busGui : elements) {
            content.getChildren().addAll(busGui.getGUI());
        }
    }

    /**
     * sets basic map elements (streets, stops) to gui
     */
    public void setMapBase() {
        List<Drawable> elements = new ArrayList<>();

        /* create street elements */
        for (Street street : holder.getStreets()) {
            StreetGui streetGui = new StreetGui(street.getStart(), street.getEnd(), street);
            setSelectedStreet(streetGui);
            elements.add(streetGui);
        }

        /* create stop elements */
        for (Stop stop : holder.getStops()) {
            elements.add(new StopGui(stop.getId(), stop.getCoordinate()));
        }

        /* set basic elements to gui */
        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
        }
    }

    /**
     * Method unsets selected street.
     */
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
            //no street to unselect
        }
    }

    /**
     * Method removes highlighted path of selected bus on mouse-click.
     */
    public void removeLines(MouseEvent event) {
        event.consume();
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            removeLines();
            unsetSelectedStreet();
        }
    }

    /**
     * Method removes highlighted path of selected bus.
     */
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

    /**
     * Method starts timer and activates buses.
     * @param scale Time speed.
     */
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

    /**
     * Activate buses if local time reached their start time
     */
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
            this.setBusElements(elements);
        }
    }

    /**
     * Method creates path for line.
     * @param line Line for which to create path.
     * @return List of coordinates representing path.
     */
    public List<Coordinate> createPathCoords(model.Line line) {
        List<Stop> StopsLine = line.getStops();
        List<Coordinate> pathCoords = new ArrayList<>();

        List<Street> StreetLine = line.getStreets();
        pathCoords.add(StopsLine.get(0).getCoordinate()); //first stop
        for (Street str : StreetLine) {
            // if its last street in Line, get only beginning of street
            if (str.equals(StreetLine.get(StreetLine.size() - 1))) {
                pathCoords.add(str.getStart());
                continue;
            }
            //if its first street in line get only end of street
            if (str.equals(StreetLine.get(0))) {
                pathCoords.add(str.getEnd());
                continue;
            }
            pathCoords.add(str.getStart());
            pathCoords.add(str.getEnd());
        }
        pathCoords.add(StopsLine.get(StopsLine.size() - 1).getCoordinate()); //last stop

        return pathCoords;
    }

    /**
     * Deactivate buses if local time reached their end time
     */
    private void deactivateBuses() {
        List<BusGui> busesToRemove = new ArrayList<>();

        for ( BusGui busGui : this.busElements ) {
            if (busGui.getDistance() > busGui.getPath().getPathSize()) {
                /* bus finished */
                for (int i = 0; i < busGui.getGUI().size()-1; i++) {
                    if (busGui.getGUI().get(i+1).getTypeSelector().equals("Line"))
                        busGui.getGUI().get(i+1).setStroke(Color.TRANSPARENT);
                }
                busesToRemove.add(busGui);
                content.getChildren().remove(busGui.getGUI().get(0));
            }
        }

        this.busElements.removeAll(busesToRemove);
    }

    /**
     * Activate buses if some sort of time jump is made in application (first start, time change, ..)
     *
     * Will activate all buses already on road, that means local time is somewhere between their start and end time
     *
     * @param offset if offset is equal to zero, method is activating buses from range midnight to actual time, if greater than zero, method is activating buses from range (midnight - offset) to midnight
     */
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
        this.setBusElements(elements);
    }


    /**
     * Method shows itinerary of the bus on mouse-click on the bus.
     * @param busGui Clicked on bus.
     */
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

    /**
     * Deactivate all buses and paint them again with new information, used if data dictating bus position were changed
     */
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

    /**
     * Setter for 'holder'
     *
     * @param holder data holder
     */
    public void setHolder(DataHolder holder) {
        this.holder = holder;
    }
}