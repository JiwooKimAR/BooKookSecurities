package BooKookSecurities.Controller;

import BooKookSecurities.Main;
import BooKookSecurities.Manager.ExcelManager;
import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.ExcelData;
import BooKookSecurities.Model.ExcelInput;
import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.Setting;
import BooKookSecurities.String.Strings;
import BooKookSecurities.Util.EmailSender;
import BooKookSecurities.Util.TimeUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Label label_filelocation, label_lastchecked, label_inputDscrp, label_progress; //실행탭 파일위치, 실행탭 마지막으로 확인된 보고서
    @FXML
    private ScrollPane scrollpane_alert;
    @FXML
    private TextField txt_excelLocation, txt_name, txt_reportFile;
    @FXML
    private ToggleButton toggle_startprogram;
    @FXML
    private ComboBox<String> combo_alarmtype, combo_year, combo_month, combo_day;
    public Label label_notification;
    private SettingsManager settingsManager;
    private ReportManager reportManager;
    private ArrayList<Report> reports;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(label_filelocation.getText());
        combo_year.getItems().addAll("1", "2");
        for (int i = 1; i <= 12; i++) combo_month.getItems().add(Integer.toString(i));
        for (int i = 1; i <= 30; i++) combo_day.getItems().add(Integer.toString(i));


        init();

        settingsManager = SettingsManager.getInstance();

        setNotificationLabel();

    }
    private void setNotificationLabel(){
        Setting setting = settingsManager.getSetting();
        int Limit = setting.getLimit_date();
        ArrayList<Report> reportsF = reportManager.getReports();
        ArrayList<Report> target_reports = new ArrayList<>();
        for (Report report : reportsF) {
            if (report.getDate_difference() > Limit && report.getDate_difference() < 180) {
                target_reports.add(report);
            }
        }
        String list = "";
        for (Report report : target_reports) {
            int month = 0, day = 0, total = 0;
            total = 180 - report.getDate_difference();
            month = total / 30;
            day = total - month * 30;
            list += "'" + report.getItem_name() + "' 종목이 현재 일자에서부터 " + report.getDate_difference() + "일 경과하였고,\n 6개월(180일)로부터 약 " +
                    month + "개월 " + day + "일 남았습니다.\n";
        }
        if (target_reports.isEmpty()) {
            list += "써야하는 보고서가 존재하지 않습니다.";
        }
        scrollpane_alert.setContent(new Text(list));
    }
    private void init(){
        loadSettings();
        loadReports();
        updateInputDscrp();

        label_progress.setText("");
        Main.setLabel_notification(label_notification);
    }

    private void loadReports(){
        reportManager = new ReportManager();
        reports = reportManager.getReports();
        Report oldest = reportManager.getOldestReport();
        label_lastchecked.setText(" 마지막으로 확인 된 보고서: " + oldest.getItem_name() + ", " + oldest.getDate_difference() + "일 지남.");
    }
    private void loadSettings(){
        settingsManager = SettingsManager.getInstance();
        Setting setting = settingsManager.getSetting();

        txt_name.setText(setting.getUsername());
        txt_reportFile.setText(setting.getReport_path());
        toggle_startprogram.setSelected(setting.isStartProgram());
        if (setting.isStartProgram()) toggle_startprogram.setText("ON");
        else toggle_startprogram.setText("OFF");

        combo_year.getSelectionModel().select(setting.getLimit_year());
        combo_month.getSelectionModel().select(setting.getLimit_month());
        combo_day.getSelectionModel().select(setting.getLimit_day());
        label_filelocation.setText(" 파일 위치: " + setting.getReport_path());
    }

    public void OnFindReportClicked() throws Exception{
        //read report file and load to table view
        System.out.println("Find Report Button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../layout/report.fxml"));
        Parent   root = (Parent) fxmlLoader.load();
        ReportController reportController = fxmlLoader.getController();

        reportManager.notifyDataChanged();
        reports = reportManager.getReports(); //send latest report version
        reportController.setReports(this.reports);
        Stage stage = new Stage();
        Main.setReportScene(stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Reports");
        stage.setScene(new Scene(root));


        stage.show();
    }

    public void OnToggleSelected(){
        if (this.toggle_startprogram.isSelected()) toggle_startprogram.setText("ON");
        else toggle_startprogram.setText("OFF");
    }
    public void OnCalculateClicked(){


        if (txt_excelLocation.getText().isEmpty() || Main.getExcelInputs().size() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("경고");
            alert.setContentText("옳바르지 않은 입력입니다.");

            alert.showAndWait();

        }
        else{
            String output_name = "test.xlsx";
            label_progress.setText("계산중...");
            ExcelManager excelManager = new ExcelManager(txt_excelLocation.getText(), Main.getExcelInputs());
            excelManager.read();
            excelManager.calculate();
            excelManager.write(output_name);
            Date date = Calendar.getInstance().getTime();
            long getTime = date.getTime();
            label_progress.setText(TimeUtil.getCurrentTime() + ": " + output_name + "로파일 저장됨.");
//        EmailSender sender = new EmailSender(Strings.EmailSenderMail);
//        sender.SendMail("yo", 10);
        }

    }

    public void OnChooseExcelClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel Files (*.xlsm)", ".xlsm"));
        fileChooser.setInitialDirectory(new File("."));
        File selectedFile = fileChooser.showOpenDialog(Main.currentWIndow().getWindow());
        if (selectedFile != null){
            txt_excelLocation.setText(selectedFile.getAbsolutePath());
        }
    }

    public void OnGetInputClicked() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../layout/excel_input.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ExcelInputController excelInputController = fxmlLoader.getController();

        excelInputController.setExcelInputs(Main.getExcelInputs());
        Stage stage = new Stage();
        Main.setExcelInputScene(stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Excel Inputs");
        stage.setScene(new Scene(root));
        stage.setOnHidden(e -> this.updateInputDscrp());
        stage.show();
    }


    private void updateInputDscrp(){
        ObservableList<ExcelInput> excelInputs = Main.getExcelInputs();
        int size = excelInputs.size();
        if (size == 0){
            label_inputDscrp.setText("입력된 값이 없습니다.");
        }
        else{
            Collections.sort(excelInputs);

            label_inputDscrp.setText("입력된 수: " + size + ", 전체 기간: " + excelInputs.get(0).getStartDate().toString() + " ~ "
                    + excelInputs.get(size - 1).getStartDate().toString());
        }
    }
    public void OnSetEmailClicked(){
        ReportManager reportManager = new ReportManager();
        reportManager.getReports();
    }

    public void OnLocateReportClicked(){
        System.out.println("locate report clicked");
    }

    public void OnUpdateClicked(){
        System.out.println("update clicked");
    }

    public void setNotificationText(String text){
        //update UI later
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label_notification.setText(text);
            }
        });
    }
}
