package BooKookSecurities.Controller;

import BooKookSecurities.Main;
import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Manager.SettingsManager;
import BooKookSecurities.Model.Report;
import BooKookSecurities.Model.Setting;
import BooKookSecurities.String.Strings;
import BooKookSecurities.Util.EmailSender;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label label_filelocation, label_lastchecked; //실행탭 파일위치, 실행탭 마지막으로 확인된 보고서
    @FXML
    private DatePicker txt_endDate, txt_startDate;
    @FXML
    private TextField txt_targetValue, txt_excelLocation, txt_email, txt_reportFile;
    @FXML
    private ToggleButton toggle_startprogram;
    @FXML
    private ComboBox<String> combo_alarmtype, combo_year, combo_month, combo_day;

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
    }
    private void init(){
        loadSettings();
        loadReports();
    }

    private void loadReports(){
        reportManager = new ReportManager();
        reports = reportManager.getReports();
        Report oldest = reportManager.getOldestReport();
        label_lastchecked.setText("마지막으로 확인 된 보고서: " + oldest.getItem_name() + ", " + oldest.getDate_difference() + "일 지남.");
    }
    private void loadSettings(){
        settingsManager = SettingsManager.getInstance();
        Setting setting = settingsManager.getSetting();

        txt_email.setText(setting.getRecipient_mail());
        txt_reportFile.setText(setting.getReport_path());
        toggle_startprogram.setSelected(setting.isStartProgram());
        if (setting.isStartProgram()) toggle_startprogram.setText("ON");
        else toggle_startprogram.setText("OFF");

        combo_year.getSelectionModel().select(setting.getLimit_year());
        combo_month.getSelectionModel().select(setting.getLimit_month());
        combo_day.getSelectionModel().select(setting.getLimit_day());
        label_filelocation.setText("파일 위치: " + setting.getReport_path());
    }

    public void OnFindReportClicked() throws Exception{
        //read report file and load to table view
        System.out.println("Find Report Button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../layout/report.fxml"));
        Parent   root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
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

        if (txt_excelLocation.getText().isEmpty() || txt_startDate.getEditor().getText().isEmpty() || txt_endDate.getEditor().getText().isEmpty() ||
            txt_targetValue.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("경고");
            alert.setContentText("빈 칸을 채워주세요.");

            alert.showAndWait();

        }
    else{
            //calculate disparate ratio
            //read excel files in selected directory and create an excel file with results
//        System.out.println("Calculate disparate ratio");
//        System.out.println(txt_excelLocation.getText());
//        System.out.println(txt_startDate.getEditor().getText());
//        System.out.println(txt_endDate.getEditor().getText());
//        System.out.println(txt_targetValue.getText());

//        EmailSender sender = new EmailSender(Strings.EmailSenderMail);
//        sender.SendMail("yo", 10);
        }

    }

    public void OnChooseExcelClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(Main.currentWIndow().getWindow());
        if (selectedFile != null){
            txt_excelLocation.setText(selectedFile.getAbsolutePath());
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

}
