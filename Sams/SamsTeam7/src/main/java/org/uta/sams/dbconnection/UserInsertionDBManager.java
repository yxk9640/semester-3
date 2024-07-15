package org.uta.sams.dbconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInsertionDBManager {

	public UserInsertionDBManager() {
	}
	
    public static void insertUser(String name,String email, String phone, String address) throws ClassNotFoundException, SQLException{
        DBConnection connection= DBConnection.getInstance();
        Connection con=connection.getConnection();
        String query = "insert into samsdb.user_form values ("+name+","+email+","+phone+","+address+")";
        System.out.println(query);
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        con.close();
//        return userBean;
        }
	
}

