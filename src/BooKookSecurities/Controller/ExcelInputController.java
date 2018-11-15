package BooKookSecurities.Controller;

import BooKookSecurities.Model.ExcelInput;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExcelInputController {
    @FXML
    public TableView<ExcelInput> tableInput;
    public DatePicker txtStartdate, txtEnddate;
    public TextField txtTarget;

    private ObservableList<ExcelInput> excelInputs;
    public void OnAddClicked(){
        if (txtStartdate.getEditor().getText() == "" || txtEnddate.getEditor().getText() =="" || txtTarget.getText().equals("")){
            System.out.println("One of the fields is empty.");
        }else{
            LocalDate startDate = txtStartdate.getConverter().fromString(txtStartdate.getEditor().getText());
            LocalDate endDate = txtEnddate.getConverter().fromString(txtEnddate.getEditor().getText());
            int targetValue = Integer.parseInt(txtTarget.getText());

            ExcelInput newInput = new ExcelInput(startDate, endDate, targetValue);
            excelInputs.add(newInput);

        }
    }

    public void setExcelInputs(ObservableList<ExcelInput> excelInputs) {
        this.excelInputs = excelInputs;
        fillTable();
    }

    private void fillTable(){
        TableColumn<ExcelInput, LocalDate> colStartDate = new TableColumn<>("Start Date");
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<ExcelInput, LocalDate> colEndDate = new TableColumn<>("End Date");
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<ExcelInput, Integer> colTargetValue = new TableColumn<>("Target Value");
        colTargetValue.setCellValueFactory(new PropertyValueFactory<>("targetValue"));

        colStartDate.prefWidthProperty().bind(tableInput.widthProperty().divide(3));
        colEndDate.prefWidthProperty().bind(tableInput.widthProperty().divide(3));
        colTargetValue.prefWidthProperty().bind(tableInput.widthProperty().divide(3));
        tableInput.setItems(excelInputs);
        tableInput.getColumns().addAll(colStartDate, colEndDate, colTargetValue);

        tableInput.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
