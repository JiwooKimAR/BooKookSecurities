package BooKookSecurities;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene mainWindow;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        primaryStage.setTitle("부국증권");
        mainWindow = new Scene(root);
        primaryStage.setScene(mainWindow);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Scene currentWIndow(){
        return mainWindow;
    }
}
