package BooKookSecurities.Controller;

import BooKookSecurities.Model.ExcelInput;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExcelInputController {
    @FXML
    public TableView<ExcelInput> tableInput;
    public DatePicker txtStartdate, txtEnddate;
    public TextField txtTarget;

    private ObservableList<ExcelInput> excelInputs;
    public void OnAddClicked(){
        System.out.println(txtEnddate.getEditor().getText() + txtStartdate.getEditor().getText() + " " + txtTarget.getText());

        LocalDate start = txtStartdate.getConverter().fromString(txtStartdate.getEditor().getText());

        int temp = start.getDayOfMonth();
        System.out.println(temp);
    }

}
