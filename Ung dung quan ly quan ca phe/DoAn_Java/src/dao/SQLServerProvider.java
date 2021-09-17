/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author Nhan
 */
public class SQLServerProvider {
    Connection con;
    Statement statement;
    PreparedStatement preStatment;
    ResultSet rs;
    public void Open(){
        String server = "LAPTOP-1LO8U7LN\\SQLEXPRESS";
        String user ="sa";
        String password = "nguyen114814";
        String databasename = "DEAN_JAVA";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://"+server
                    + ":1433;databaseName="+databasename
                    +";user="+user
                    +";password="+password;
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public void Close(){
        try {
            this.con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public ResultSet executeQuery(String sql)
    {
        rs= null;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
    public int executeUpdate(String sql)
    {
        int n = -1;
        try {
            statement = con.createStatement();
            int kq = statement.executeUpdate(sql);
            if(kq>=0)
            {
                n = 1;
            }
            else
            {
                n = -1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return n;
    }
}
