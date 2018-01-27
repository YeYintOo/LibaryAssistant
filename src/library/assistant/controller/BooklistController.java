/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.assistant.database.Database;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;

/**
 * FXML Controller class
 *
 * @author ChanMyaeOo
 */
public class BooklistController implements Initializable {

    @FXML
    private TableColumn<Book, String> idField;
    @FXML
    private TableColumn<Book, String> titleField;
    @FXML
    private TableColumn<Book, String> authorField;
    @FXML
    private TableColumn<Book, String> publisherField;
    @FXML
    private TableView<Book> bookTable;
    private BookDAO bookDAO;
    @FXML
    private TableColumn<Book, String> availableCol;
    @FXML
    private MenuItem selectItem;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            //db = Database.getInstance();
            bookDAO = new BookDAO();
            List<Book> bookList = bookDAO.getBooks();
            bookTable.getItems().addAll(bookList);
            initColumn();
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void initColumn() {
        idField.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleField.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorField.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherField.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        Book book = bookTable.getSelectionModel().getSelectedItem();
        
        if(book == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select the book");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.show();
            //System.out.println("Please select the book");
            return;
        }
        try {
            bookDAO.deleteBook(book.getId());
            bookTable.getItems().remove(book);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no book to delete");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.show();
        }
    }
    
}
