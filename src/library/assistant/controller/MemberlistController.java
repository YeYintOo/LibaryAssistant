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
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;

/**
 * FXML Controller class
 *
 * @author ChanMyaeOo
 */
public class MemberlistController implements Initializable {

    @FXML
    private TableView<Member> memberTableView;
    @FXML
    private TableColumn<Member, String> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;
    private MemberDAO memberDAO;
    @FXML
    private MenuItem selectMember;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO = new MemberDAO();
        try {
            List<Member> memberList = memberDAO.getMemberList();
            memberTableView.getItems().addAll(memberList);
        } catch (SQLException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initColumns();
        
    }

    public void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

    }

    @FXML
    private void deleteMember(ActionEvent event) {
        Member member = memberTableView.getSelectionModel().getSelectedItem();
        if(member == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select the member");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        memberTableView.getItems().remove(member);
        
        try {
            memberDAO.deleteMember(member.getId());
        } catch (SQLException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
