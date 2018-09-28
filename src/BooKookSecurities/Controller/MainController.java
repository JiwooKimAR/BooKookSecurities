package BooKookSecurities.Controller;

import BooKookSecurities.Main;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(label_filelocation.getText());
        combo_year.getItems().addAll("1", "2");
        for (int i = 1; i <= 12; i++) combo_month.getItems().add(Integer.toString(i));
        for (int i = 1; i <= 30; i++) combo_day.getItems().add(Integer.toString(i));
    }

    public void OnFindReportClicked() throws Exception{
        //read report file and load to table view
        System.out.println("Find Report Button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../layout/report.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Reports");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void OnCalculateClicked(){
        //read excel files in selected directory and create an excel file with results
        System.out.println("Calculate disparate ratio");
        System.out.println(txt_excelLocation.getText());
        System.out.println(txt_startDate.getEditor().getText());
        System.out.println(txt_endDate.getEditor().getText());
        System.out.println(txt_targetValue.getText());
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
        System.out.println("email clicked");
    }

    public void OnLocateReportClicked(){
        System.out.println("locate report clicked");
    }

    public void OnUpdateClicked(){
        System.out.println("update clicked");
    }

}
