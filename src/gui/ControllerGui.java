package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

import javax.xml.soap.Text;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerGui {
    @FXML
    private AnchorPane content;
    @FXML
    private TextField setTime, changeTimeSpeed;
    //@FXML
    //private TextField changeTimeSpeed;

    private List<Drawable> elements = new ArrayList<>();
    private List<TimeUpdate> updates = new ArrayList<>();

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
    }

    @FXML
    /* changes time's speed */
    public void onTimeChangeSpeed(ActionEvent actionEvent) {
        float scale = Float.parseFloat(changeTimeSpeed.getText());
        timer.cancel();
        startTime(scale);
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

    public void setElements(List<Drawable> elements){
        this.elements = elements;
        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
            if (drawable instanceof TimeUpdate) {
               // updates.add(TimeUpdate) drawable);
            }
        }
    }

    public void startTime(float scale) {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time = time.plusSeconds(1);
                for (TimeUpdate update : updates) {

                }
            }
        }, 0, 1000);
    }
}
