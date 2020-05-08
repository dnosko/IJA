package view;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerGui {
    @FXML
    private AnchorPane content;

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
           if (code == KeyCode.ADD) {
                content.setScaleX(1.1 * content.getScaleX());
                content.setScaleY(1.1 * content.getScaleY());
           }
           else if (code == KeyCode.SUBTRACT) {
               content.setScaleX(0.9 * content.getScaleX());
               content.setScaleY(0.9 * content.getScaleY());
           }
           content.layout();
        }
    }

}
