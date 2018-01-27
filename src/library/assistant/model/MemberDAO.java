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
import java.util.ArrayList;
import java.util.List;
import library.assistant.database.Database;

/**
 *
 * @author ChanMyaeOo
 */
public class MemberDAO {

    public static Member getMember(String memberId) throws SQLException {
        Connection conn = Database.getInstance().getConn();
        String selectSql = "select * from libdb.members where id=?";
        PreparedStatement preState = conn.prepareStatement(selectSql);
        preState.setString(1, memberId);
        ResultSet result = preState.executeQuery();
         Member member = null;
        if(result.next()){
            String id = result.getString("id");
            String name = result.getString("name");
            String mobile = result.getString("mobile");
            String address = result.getString("address");
            member = new Member(id,name,mobile,address);
        }
        return member;
        
    }
    public void saveMember(Member member) throws SQLException{
        Connection conn = Database.getInstance().getConn();
        String insertMember = "insert into libdb.members (id,name,mobile,address) values (?,?,?,?)";
        PreparedStatement preState = conn.prepareStatement(insertMember);
        preState.setString(1, member.getId());
        preState.setString(2,member.getName());
        preState.setString(3, member.getMobile());
        preState.setString(4,member.getAddress());
        preState.execute();
    }
    
    public List<Member> getMemberList() throws SQLException{
         Connection conn = Database.getInstance().getConn();
        List<Member> list = new ArrayList<>();
        String selectSql = "select * from libdb.members";
        PreparedStatement preState = conn.prepareStatement(selectSql);
        ResultSet result = preState.executeQuery();
        while(result.next()){
            String id = result.getString("id");
            String name = result.getString("name");
            String mobile = result.getString("mobile");
            String address = result.getString("address");
            Member member = new Member(id,name,mobile,address);
            list.add(member);
        }
        return list;
    }

    public void deleteMember(String memberId) throws SQLException {
         Connection conn = Database.getInstance().getConn();
        String deleteSql = "delete from libdb.members where id=?";
        PreparedStatement preState = conn.prepareStatement(deleteSql);
        preState.setString(1, memberId);
        preState.execute();
    }
}
