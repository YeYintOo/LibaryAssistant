/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import library.assistant.database.Database;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;

/**
 * FXML Controller class
 *
 * @author ChanMyaeOo
 */
public class NewbookController implements Initializable {

    @FXML
    private JFXTextField bookField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    private BookDAO bookDAO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            bookDAO = new BookDAO();
    }    

    @FXML
    private void saveBook(ActionEvent event)  {
        
        String id = bookField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        //String available = "";
        if(id.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all fields.");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        
        try {
            bookDAO.saveBook(new Book(id,title,author,publisher));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Success adding book");
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error to save book");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.show();
            Logger.getLogger(NewbookController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage)saveBtn.getScene().getWindow();
        stage.close();
    }
}
