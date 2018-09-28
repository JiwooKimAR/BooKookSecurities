package BooKookSecurities;

import BooKookSecurities.Scheduler.NotifyScheduler;
import BooKookSecurities.Scheduler.NotifyThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene mainWindow;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout/main.fxml"));
        primaryStage.setTitle("부국증권");
        mainWindow = new Scene(root);
        primaryStage.setScene(mainWindow);

        primaryStage.show();
    }

    public void init(){
        NotifyScheduler notifyScheduler = new NotifyScheduler();
        notifyScheduler.setScheduler(new NotifyThread(), 10);
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static Scene currentWIndow(){
        return mainWindow;
    }
}
