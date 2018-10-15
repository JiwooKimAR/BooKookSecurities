package BooKookSecurities.Controller;

import BooKookSecurities.Main;
import BooKookSecurities.Manager.ReportManager;
import BooKookSecurities.Model.Report;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView<Report> table_report;
    private ArrayList<Report> reportArrayList;
    private ObservableList<Report> selectedIndices = FXCollections.observableArrayList();
    private ReportManager reportManager;
    @FXML
    private Label label_itemSelected;

    public void OnCancelClicked(){
        Main.getReportScene().close();
    }
    public void OnDeleteClicked(){
        reportManager.writeReports(table_report.getSelectionModel().getSelectedItems(), table_report.getItems());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportManager = new ReportManager();
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

        table_report.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table_report.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            selectedIndices = table_report.getSelectionModel().getSelectedItems();
            label_itemSelected.setText(selectedIndices.size() + "개가 선택됨.");
        });
    }

    public ObservableList<Report> getReportsList(){
        ObservableList<Report> reports = FXCollections.observableArrayList();
        for (Report report : reportArrayList){
            reports.add(report);
        }

        return reports;
    }
}
