package BooKookSecurities.Controller;

import BooKookSecurities.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
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

    }

    public void OnFindReportClicked(){
        //read report file and load to table view
        System.out.println("Find Report Button clicked");
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
