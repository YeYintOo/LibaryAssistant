/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import library.assistant.database.Database;

/**
 *
 * @author ChanMyaeOo
 */
public class IssueDAO {

    public void issueBook(String book_id,String member_id) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String insertSql = "insert into libdb.issue (member_id,book_id,issue_date,renew_count) values (?,?,curdate(),0)";
        PreparedStatement preState = conn.prepareStatement(insertSql);
        preState.setString(1,member_id);
        preState.setString(2,book_id);
        //preState.setLong(3, issue_date);
        //preState.setInt(4, renew_count);
        preState.execute();
    }

    public boolean checkBook(String bookId) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String checkSql = "select count(*) as count from libdb.issue where book_id=?";
        PreparedStatement preState = conn.prepareStatement(checkSql);
        preState.setString(1, bookId);
        ResultSet result = preState.executeQuery();
        boolean check = true;
        if(result.next()){
            int count = result.getInt("count");
            if(count == 0){
                check = false;
            }
        }
        return check;
    }

    public IssueInfo getIssueInfo(String BookId) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String selectSql = "select * from libdb.issue where book_id = ?";
        PreparedStatement preStmt = conn.prepareStatement(selectSql);
        preStmt.setString(1, BookId);
        ResultSet result = preStmt.executeQuery();
        IssueInfo issueInfo = null;
        if(result.next()){
            String memberId = result.getString("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");
            issueInfo = new IssueInfo(BookId,memberId,issueDate,renewCount);
        }
        return issueInfo;
    }

    public void deleteIssueInfo(String bookId) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String deleteSql = "delete from libdb.issue where book_id = ?";
        PreparedStatement preStmt = conn.prepareStatement(deleteSql);
        preStmt.setString(1, bookId);
        preStmt.execute();
    }

    public void updateIssueInfo(String bookId) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String updateSql = "update libdb.issue set renew_count = renew_count+1 where book_id = ?";
        PreparedStatement preStmt = conn.prepareStatement(updateSql);
        preStmt.setString(1, bookId);
        preStmt.execute();
    }
}
