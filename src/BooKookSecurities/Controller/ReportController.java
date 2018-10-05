package BooKookSecurities.Controller;

import BooKookSecurities.Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView<Report> table_report;
    private ArrayList<Report> reportArrayList;

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
        this.reportArrayList = reports;
        fillTable();

    }

    private void fillTable(){
        TableColumn<Report, Integer> numCol = new TableColumn<>("Number");
        numCol.setCellValueFactory(new PropertyValueFactory<>("item_code"));

        TableColumn<Report, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<Report, Date> dateCol = new TableColumn<>("Date Added");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("item_added_date"));

        TableColumn<Report, Integer> diffCol = new TableColumn<>("Days Passed");
        diffCol.setCellValueFactory(new PropertyValueFactory<>("date_difference"));

        //table_report = new TableView<>();
        table_report.setItems(getReportsList());
        table_report.getColumns().addAll(numCol, nameCol, dateCol, diffCol);


    }

    public ObservableList<Report> getReportsList(){
        ObservableList<Report> reports = FXCollections.observableArrayList();
        for (Report report : reportArrayList){
            reports.add(report);
        }

        return reports;
    }
}
