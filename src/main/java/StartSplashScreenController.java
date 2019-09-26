/*
 *  Gitas Fleet Tracking System 2019
 *
 *  Contributors:
 *      - Ahmet Ziya Kanbur
 *
 */
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class StartSplashScreenController implements Initializable {
    /**
     * Big notification label
     */
    @FXML private Label uiNotfLabel;

    /**
     * Small notf label
     */
    @FXML private Label uiSubNotfLabel;

    /**
     * Success icon
     */
    @FXML private ImageView uiTick;

    /**
     * Fail icon
     */
    @FXML private ImageView uiError;

    /**
     * Big notf string property
     */

    private StringProperty notf;
    /**
     * Small notf string property
     */
    private StringProperty subNotf;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notf = new SimpleStringProperty(this, "Yükleniyor..");
        subNotf = new SimpleStringProperty(this, "Lütfen bekleyin..");
        uiNotfLabel.textProperty().bindBidirectional(notf);
        uiSubNotfLabel.textProperty().bindBidirectional(subNotf);
    }

    /**
     * Updates notification labels.
     *
     * @param notf
     * @param subNotf
     */
    public void updateStatus(String notf, String subNotf){
        Platform.runLater(() -> {
            this.notf.setValue(notf);
            this.subNotf.setValue(subNotf);
        });
    }

    /**
     * Shows success icon
     */
    public void initTick(){
        Platform.runLater(()->{
            uiTick.setVisible(true);
        });
    }

    /**
     * Shows error icon
     */
    public void initError(){
        Platform.runLater(()->{
            uiError.setVisible(true);
        });
    }

}
