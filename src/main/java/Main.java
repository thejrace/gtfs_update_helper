/*
 *  Gitas Fleet Tracking System 2019
 *
 *  Contributors:
 *      - Ahmet Ziya Kanbur
 *
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
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
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Update helper started!");
                if( Common.checkFile( "app_config.json" ) ){
                    JSONObject config = new JSONObject( Common.readJSONData("app_config.json"));
                    STATIC_LOCATION = config.getString("installDir");
                } else {
                    Platform.exit();
                }

                try {
                    Thread.sleep(3000);
                } catch( InterruptedException e ){
                    e.printStackTrace();
                }
                System.out.println("RENAME THREAD START");
                Path sourcePath      = Paths.get(STATIC_LOCATION + "GFTS_new.exe");
                Path destinationPath = Paths.get(STATIC_LOCATION + "GFTS.exe");
                try {
                    Files.move(sourcePath, destinationPath,
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    //moving file failed.
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch( InterruptedException e ){
                    e.printStackTrace();
                }
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
    }

    public static void main(String[] args){
        launch(args);
    }


}
