package BooKookSecurities.Controller;

import BooKookSecurities.Model.Report;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView table_report;
    private ArrayList<Report> reports;

    @FXML
    private Label label_itemSelected;

    public void OnCancelClicked(){
        System.out.println("cancel clicked");
    }
    public void OnDeleteClicked(){
        System.out.println("delete clicked");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(label_itemSelected.getText());
    }

    public void setReports(ArrayList<Report> reports){
        this.reports = reports;
    }
}
