/*
 *  Gitas Fleet Tracking System 2019
 *
 *  Contributors:
 *      - Ahmet Ziya Kanbur
 *
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main extends Application {

    public static String STATIC_LOCATION;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/start_splash.fxml"));
            Parent content = loader.load();
            primaryStage.setTitle("Gitas FTS Setup");
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image(getClass().getResource("/img/gpts_setup_ico.png").toExternalForm()));
            primaryStage.setScene( new Scene(content, 500, 280 ));
            primaryStage.show();
            StartSplashScreenController controller = loader.getController();

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {

                    controller.updateStatus("Yükleniyor..", "Lütfen bekleyin..");

                    if( Common.checkFile( "app_config.json" ) ){
                        JSONObject config = new JSONObject( Common.readJSONData("app_config.json"));
                        STATIC_LOCATION = config.getString("installDir");
                    } else {
                        controller.updateStatus("Hata oluştu. [STAT_LOC_FAIL]", "Sistem yöneticisine bu hatayı bildirin.");
                        controller.initError();
                        return;
                    }

                    ThreadHelper.delay(3000);

                    Path sourcePath      = Paths.get(STATIC_LOCATION + "GFTS_new.exe");
                    Path destinationPath = Paths.get(STATIC_LOCATION + "GFTS.exe");
                    try {
                        Files.move(sourcePath, destinationPath,
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        //moving file failed.
                        controller.updateStatus("Hata oluştu. [FILE_MOV_FAIL]", "Sistem yöneticisine bu hatayı bildirin.");
                        controller.initError();
                        return;
                    }

                    controller.initTick();
                    controller.updateStatus("Tamamlandı.", "Bu pencere kapandıktan sonra, programı tekrar başlatabilirsiniz.");

                    ThreadHelper.delay(3000);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Platform.exit();
                        }
                    });
                }
            });
            th.setDaemon(true);
            th.start();

        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }


}
