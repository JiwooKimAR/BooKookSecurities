package BooKookSecurities;

import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.ExcelInput;
import BooKookSecurities.Scheduler.NotifyScheduler;
import BooKookSecurities.Scheduler.NotifyThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {
    private static Scene mainWindow;
    private static Stage reportScene;
    private static Stage excelinputScene;
    private ArrayList<ExcelInput> excelInputs;
    private SettingsManager settingsManager;
    NotifyScheduler notifyScheduler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout/main.fxml"));
        primaryStage.setTitle("부국증권");
        mainWindow = new Scene(root);
        primaryStage.setScene(mainWindow);

        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            //e.consume(); to not close the program

            closeProgram();
            notifyScheduler.disableScheduler();
            Platform.exit();
        });


    }

    private void closeProgram() {
        System.out.println("program is closed");
    }

    public void init() {
        settingsManager = SettingsManager.getInstance();
        notifyScheduler = new NotifyScheduler();
        notifyScheduler.setScheduler(new NotifyThread(), settingsManager.getSetting().getTime_period_hrs()); //time_period

        excelInputs = new ArrayList<>();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene currentWIndow() {
        return mainWindow;
    }

    public static Stage getReportScene() {
        return reportScene;
    }

    public static void setReportScene(Stage reportScene) {
        Main.reportScene = reportScene;
    }

    public static void setExcelInputScene(Stage excelInputScene){
        Main.excelinputScene = excelInputScene;
    }
//    org.silentsoft.core.tray.TrayIconHandler.registerTrayIcon(
//		Toolkit.getDefaultToolkit().getImage("src/main/resources/icon/computer.png"),
//		"Example", new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// Open your application here.
//			}
//		}
//	);
//	org.silentsoft.core.tray.TrayIconHandler.addItem("Exit", new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.exit(0);
//		}
//	});
}
