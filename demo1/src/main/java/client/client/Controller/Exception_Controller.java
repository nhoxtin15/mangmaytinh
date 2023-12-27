package client.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Exception_Controller {
    @FXML
    public Label Error_name;
    @FXML
    public Label Error_description;


    public void ExitPopup(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
    }





}
