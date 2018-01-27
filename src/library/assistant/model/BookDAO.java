/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import library.assistant.database.Database;

/**
 *
 * @author YeYintOo
 */
public class BookDAO {          //DAO means Database Access Object    
    public void saveBook(Book book) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String insertBook = "insert into libdb.books (id,title,author,publisher) values (?,?,?,?)";

        PreparedStatement preState = conn.prepareStatement(insertBook);
        preState.setString(1, book.getId());
        preState.setString(2, book.getTitle());
        preState.setString(3, book.getAuthor());
        preState.setString(4, book.getPublisher());
        preState.execute();
    }

    public List<Book> getBooks() throws SQLException {
        Connection conn = Database.getInstance().getConn();
        List<Book> list = new ArrayList<>();
        String selectSql = "select * from libdb.books";
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(selectSql);
        while (result.next()) {
            String id = result.getString("id");
            String title = result.getString("title");
            String author = result.getString("author");
            String publisher = result.getString("publisher");
            boolean is_Available = result.getBoolean("is_available");
            String isAval = "";
            if (is_Available == true) {
                isAval = "Available";
            } else {
                isAval = "Unavailable";
            }
            Book book = new Book(id, title, author, publisher, isAval);
            list.add(book);
        }
        return list;
    }

    public void deleteBook(String id) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String deleteSql = "delete from libdb.books where id=?";
        PreparedStatement preState = conn.prepareStatement(deleteSql);
        preState.setString(1, id);
        preState.execute();
    }

    public Book getBook(String bookId) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String selectSql = "select * from libdb.books where id=?";
        PreparedStatement preState = conn.prepareStatement(selectSql);
        preState.setString(1, bookId);
        ResultSet result = preState.executeQuery();
        Book book = null;
        if(result.next()){
            String id = result.getString("id");
            String title = result.getString("title");
            String author = result.getString("author");
            String publisher = result.getString("publisher");
            boolean isAvailable = result.getBoolean("is_available");
            String availableStr = isAvailable ? "Available" : "Not Available";
            
            book = new Book(id,title,author,publisher,availableStr);
        }
        return book;
    }

    public void updateBook(String bookId, boolean isAvailable) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String updateSql = "update libdb.books set is_available=? where id=?";
        PreparedStatement preState = conn.prepareStatement(updateSql);
        preState.setBoolean(1, isAvailable);
        preState.setString(2, bookId);
        preState.execute();
    }
}
