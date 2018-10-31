/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.getAlumnosData;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author r
 */
public class FXMLloginController implements Initializable {

    @FXML
    private PasswordField pswd;
    @FXML
    private TextField user;
    @FXML
    private TextField server;
    @FXML
    private CheckBox ssl;
    
    public getAlumnosData d;
    public Boolean ok = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.d = new getAlumnosData(false);
    }    
    
    @FXML
    void pbAceptar(ActionEvent event) {
        if (this.d.getConnection(this.user.getText(),this.pswd.getText(),this.server.getText(),this.ssl.isSelected())) {
            this.ok = true;
            closeWindow();
        }
    }

    @FXML
    void pbCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) this.user.getScene().getWindow();
        stage.close();
    }
}
