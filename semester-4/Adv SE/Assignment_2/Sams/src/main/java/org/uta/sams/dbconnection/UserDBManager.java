/*
 * UserDBManager.java
 *
 * Created on March 6, 2006, 5:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.uta.sams.dbconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.uta.sams.beans.UserBean;

/**
 *
 * @author Administrator
 */
public class UserDBManager {
    
    /** Creates a new instance of UserDBManager */
    public UserDBManager() {
    }
    
    public UserBean getUser(String username) throws ClassNotFoundException, SQLException{
        DBConnection connection= DBConnection.getInstance();
        Connection con=connection.getConnection();
        String query = "select u.username,ur.roles,u.password from user u,user_roles ur where u.username='"+username+"' and ur.username=u.username";
        System.out.println(query);
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        String password=null;
        UserBean userBean = new UserBean();
        if(rs.next()){
           userBean.setRole(rs.getString("roles"));
           userBean.setPassword(rs.getString("password"));
           userBean.setUsername(username);
           
        }else
        {
            con.close();
            return null;
        }
        con.close();
        return userBean;
    }
    
}
