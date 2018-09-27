package BooKookSecurities.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView table_report;
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
}
