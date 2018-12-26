package BooKookSecurities.Controller;

import BooKookSecurities.Manager.SettingsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputNameController {
    @FXML
    Button btnRegister;
    @FXML
    TextField txtUsername;

    public void OnRegisterClicked(){
        Stage stage = (Stage) btnRegister.getScene().getWindow();
        if (txtUsername.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("이름을 입력해주세요");
            alert.showAndWait();
        }
        else {
            stage.close();
            SettingsManager settingsManager = SettingsManager.getInstance();
            settingsManager.getSetting().setUsername(txtUsername.getText());
        }
    }
}
