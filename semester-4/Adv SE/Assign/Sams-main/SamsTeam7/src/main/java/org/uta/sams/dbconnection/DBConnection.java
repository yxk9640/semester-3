/*
 * DBConnection.java
 *
 * Created on March 1, 2006, 10:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user-yxk9640
 */
public class DBConnection {
    private static DBConnection dbconnection=null;
    
    /** Creates a new instance of DBConnection */
    private DBConnection() {
    }
    
    public static DBConnection getInstance(){
        if(dbconnection==null)dbconnection= new DBConnection();
        return dbconnection;
    }
    
    Connection getConnection() throws ClassNotFoundException, SQLException{
        Statement stmt;
        Connection con=null;
             Class.forName("com.mysql.jdbc.Driver");
             String url =
            "jdbc:mysql://localhost:3306/samsdb?characterEncoding=latin1&autoReconnect=true&useSSL=false";
            con =DriverManager.getConnection(
                                 url,"root", "yogesh9999");
        return con;
    }
}
