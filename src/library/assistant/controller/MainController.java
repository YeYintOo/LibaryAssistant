/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;
import library.assistant.model.IssueDAO;
import library.assistant.model.IssueInfo;
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;

/**
 *
 * @author ChanMyaeOo
 */
public class MainController implements Initializable {

    @FXML
    private Button newBookBtn;
    @FXML
    private Button newMemberBtn;
    @FXML
    private MenuItem closeItem;
    @FXML
    private Button newMemberBtn1;
    @FXML
    private JFXTextField bookIdField;
    @FXML
    private Text titleText;
    @FXML
    private Text availableText;

    private BookDAO bookDAO;
    private IssueDAO issueDAO;
    private MemberDAO memberDAO;
    @FXML
    private Text authorText;
    @FXML
    private JFXTextField memberIdField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField bookIdSearchField;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text issueDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton submissionBtn;
    @FXML
    private MenuItem serverItem;
    @FXML
    private JFXButton memberLIst;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDAO = new BookDAO();
        issueDAO = new IssueDAO();
    }

    @FXML
    private void loadNewBookWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/newbook.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New Book Window");
        stage.show();
    }

    @FXML
    private void loadNewMemberWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/newmember.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("New Member Window");
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) newBookBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loadBookListWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/booklist.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Book List");
    }

    @FXML
    private void loadMemberListWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/memberlist.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Member List");
    }

    @FXML
    private void searchBook(ActionEvent event) {

        String bookId = bookIdField.getText();
        if (bookId.isEmpty()) {
            titleText.setText("-");
            authorText.setText("-");
            availableText.setText("-");
        }
        try {
            Book book = bookDAO.getBook(bookId);
            if (book != null) {
                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                availableText.setText(book.getIsAvailable());
            } else {
                titleText.setText("-");
                authorText.setText("-");
                availableText.setText("-");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchMember(ActionEvent event) {
        String memberId = memberIdField.getText();

        if (memberId.isEmpty()) {
            nameText.setText("-");
            mobileText.setText("-");
            addressText.setText("-");
        }
        try {
            Member member = MemberDAO.getMember(memberId);
            if (member != null) {
                nameText.setText(member.getName());
                mobileText.setText(member.getMobile());
                addressText.setText(member.getAddress());
            } else {
                nameText.setText("-");
                mobileText.setText("-");
                addressText.setText("-");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void issueBook(ActionEvent event) {
        String bookId = bookIdField.getText();
        String memberId = memberIdField.getText();

        try {
            if (issueDAO.checkBook(bookId)) {
                // System.out.println("This book was already issued.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This book was already issued.");
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.show();
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //insert issue info to issue table
            issueDAO.issueBook(bookId, memberId);

            //update is_available to false book table
            bookDAO.updateBook(bookId, false);
            //System.out.println("Success");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Success");
            alert.setTitle("Success.");
            alert.setHeaderText(null);
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void renewBook(ActionEvent event) {
        
         String bookId = bookIdSearchField.getText();
         
        try {
            
           issueDAO.updateIssueInfo(bookId);

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void startSubmission(ActionEvent event) {
        String bookId = bookIdSearchField.getText();

        try {
            issueDAO.deleteIssueInfo(bookId);

            bookDAO.updateBook(bookId, true);
            
            clearText();

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchIssueBook(ActionEvent event) {
        String issueBookId = bookIdSearchField.getText();

        if (issueBookId.isEmpty()) {
               clearText();
        }
        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(issueBookId);
            if (issueInfo == null) {
                clearText();
                return;
            }
            if (issueInfo != null) {
//                System.out.println("Book ID : " + issueInfo.getBookId());
//                System.out.println("Member ID : " + issueInfo.getMemberId());
//                System.out.println("IssueDate : " + issueInfo.getIssueDate());
//                System.out.println("Renew count : " + issueInfo.getRenewCount());
                Member memberInfo = memberDAO.getMember(issueInfo.getMemberId());
                mNameText.setText(memberInfo.getName());
                mMobileText.setText(memberInfo.getMobile());
                mAddressText.setText(memberInfo.getAddress());

                Book bookInfo = bookDAO.getBook(issueInfo.getBookId());
                bTitleText.setText(bookInfo.getTitle());
                bAuthorText.setText(bookInfo.getAuthor());
                bPublisherText.setText(bookInfo.getPublisher());

                SimpleDateFormat date = new SimpleDateFormat("dd/MM/YYYY, E");
                String dateStr = date.format(issueInfo.getIssueDate());

                issueDateText.setText("Issue Date : " + dateStr);

                renewCountText.setText("Renew Count : " + issueInfo.getRenewCount());
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearText() {
        mNameText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");

        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");

        issueDateText.setText("-");
        renewCountText.setText("-");
    }

    @FXML
    private void loadServerConfigWindow(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/server.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Server Window");
    }

   
}
