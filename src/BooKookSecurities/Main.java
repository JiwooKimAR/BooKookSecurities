package BooKookSecurities;

import BooKookSecurities.Controller.MainController;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.ExcelInput;
import BooKookSecurities.Scheduler.NotifyScheduler;
import BooKookSecurities.Scheduler.NotifyThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene mainWindow;
    private static Stage reportScene;
    private static Stage excelinputScene;
    private static ObservableList<ExcelInput> excelInputs;
    private static Label label_notification = null;
    private SettingsManager settingsManager;
    private MainController mainController;
    NotifyScheduler notifyScheduler;

    public static void setLabel_notification(javafx.scene.control.Label label_notification) {
        label_notification = label_notification;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/main.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("layout/main.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("부국증권");

        mainController = fxmlLoader.getController();
        mainWindow = new Scene(root);

        primaryStage.setScene(mainWindow);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            //e.consume(); to not close the program

            closeProgram();
            notifyScheduler.disableScheduler();
            Platform.exit();
        });

        setNotifyScheduler();
    }

    private void closeProgram() {
        System.out.println("program is closed");
    }

    private void setNotifyScheduler(){
        notifyScheduler = new NotifyScheduler();
        notifyScheduler.setScheduler(new NotifyThread(mainController), settingsManager.getSetting().getTime_period_hrs()); //time_period
    }
    public void init() {
        settingsManager = SettingsManager.getInstance();

        excelInputs = FXCollections.observableArrayList();

    }
//
//    public void setLabeltoNotify(){
//           notifyScheduler.
//    }
    public static void main(String[] args) { launch(args); }

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

    public static ObservableList<ExcelInput> getExcelInputs(){
        return excelInputs;
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
