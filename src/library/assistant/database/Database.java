/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import library.assistant.model.Member;

/**
 *
 * @author YeYintOo
 */
public class Database {
    private static Database db;
     private Connection conn;
   
    private static String host;
    private static int port;
    private static String username;
    private static String password;
    
    private Database() throws SQLException{
        connect();
        createDb();
        createTable1();
        createTable2();
        createIssueTable();
    }
    
    public static Database getInstance() throws SQLException{
        if(db == null){
            db = new Database();
        }
        return db;
    }
    public void connect() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver found");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver not found");
        }
       
            LoadFormPreferences();
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" , username, password);
//            System.out.println("Connected");
//        
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Error to connect");
        
    }
    
    public void createDb() throws SQLException{
        String createDbSql = "create database if not exists libdb";
        Statement state = conn.createStatement();
        state.execute(createDbSql);
    }
    public void createTable1() throws SQLException{
        String createTb = "create table if not exists libdb.books (id varchar(255) primary key,title varchar(255),author varchar(255),publisher varchar(255),is_available boolean default true)";
        Statement state1 = conn.createStatement();
        state1.execute(createTb);
    }
    
    public void createIssueTable() throws SQLException{
        String createIssueTb = "create table if not exists libdb.issue (member_id varchar(255),book_id varchar(255),issue_date timestamp,renew_count int,foreign key (member_id) references libdb.members(id),foreign key (book_id) references libdb.books(id))";
        Statement state = conn.createStatement();
        state.execute(createIssueTb);
    }
    public void disconnect(){
        if(conn != null){
            try {
                conn.close();
                System.out.println("Closed");
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error to close");
            }
        }
    }

      
    public void createTable2() throws SQLException{
        String createTb = "create table if not exists libdb.members (id varchar(255) primary key,name varchar(255),mobile varchar(255),address varchar(255))";
        Statement state = conn.createStatement();
        state.execute(createTb);
    }
    

    public Connection getConn(){
        return conn;
    }   

    private void LoadFormPreferences() {
        Preferences prefs = Preferences.userRoot().node("libdb");
         host = prefs.get("host", "localhost");
         port = prefs.getInt("port", 3306);
        username = prefs.get("name", "root");
        password = prefs.get("password", "");

    }
}
