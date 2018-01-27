/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.main;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import library.assistant.database.Database;

/**
 *
 * @author YeYintOo
 */
public class LibraryAssistant extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Connect to database
        
        try{
            Database.getInstance();
        }catch(SQLException ex){
            
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText(null);
//        alert.setContentText(" Cannot Connect to Database ");
//        alert.showAndWait();
             System.out.println("Cannot Connnect form Database");
        }
       
        //db.connect();
        //db.createDb();
        //db.createTable();
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Radm Book Shop");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
