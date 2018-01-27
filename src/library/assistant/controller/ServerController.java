/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ServerController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancleBtn;

    private Preferences prefs;// key - value //registery / node
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        prefs = Preferences.userRoot().node("libdb");
        String host = prefs.get("host", "localhost");
        int port = prefs.getInt("port", 3306);
        String name = prefs.get("name", "root");
        String password = prefs.get("password", "");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 33020, port);
       
        portSpinner.setValueFactory(valueFactory);
        hostField.setText(host);
        userField.setText(name);
        passField.setText(password);
    }    

    @FXML
    private void saveServerConfiguration(ActionEvent event) {
     
        String host = hostField.getText();
        int port = portSpinner.getValue();
        String name = userField.getText();
        String password = passField.getText();
        prefs.put("host", host);
        prefs.putInt("port", port);
        prefs.put("name", name);
        prefs.put("password", password);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(" Success!Please Restart This Application ");
        alert.showAndWait();
        
         Stage oldStage = (Stage) saveBtn.getScene().getWindow();
        oldStage.close();
    
    }

    @FXML
    private void CloseWindow(ActionEvent event) {
        Stage oldStage = (Stage) saveBtn.getScene().getWindow();
        oldStage.close();
        
        
    }
    
}
