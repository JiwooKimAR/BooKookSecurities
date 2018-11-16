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
    public DatePicker txtStartdate;
    public TextField txtTarget;

    private ObservableList<ExcelInput> excelInputs;
    public void OnAddClicked(){
        if (txtStartdate.getEditor().getText() == "" || txtTarget.getText().equals("")){
            System.out.println("One of the fields is empty.");
        }else{
            LocalDate startDate = txtStartdate.getConverter().fromString(txtStartdate.getEditor().getText());
            int targetValue = Integer.parseInt(txtTarget.getText());

            ExcelInput newInput = new ExcelInput(startDate, targetValue);
            excelInputs.add(newInput);

        }
    }

    public void OnDeleteClicked(){
        excelInputs.removeAll(tableInput.getSelectionModel().getSelectedItems());
    }
    public void setExcelInputs(ObservableList<ExcelInput> excelInputs) {
        this.excelInputs = excelInputs;
        fillTable();
    }

    private void fillTable(){
        TableColumn<ExcelInput, LocalDate> colStartDate = new TableColumn<>("Start Date");
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<ExcelInput, Integer> colTargetValue = new TableColumn<>("Target Value");
        colTargetValue.setCellValueFactory(new PropertyValueFactory<>("targetValue"));

        colStartDate.prefWidthProperty().bind(tableInput.widthProperty().divide(2));
        colTargetValue.prefWidthProperty().bind(tableInput.widthProperty().divide(2));
        tableInput.setItems(excelInputs);
        tableInput.getColumns().addAll(colStartDate, colTargetValue);

        tableInput.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
