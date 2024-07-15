package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.team3.security.HashPassword;

public class UpdatePassword extends SQLCmd{

	String password;
	String email;
	Boolean b = false;

	public UpdatePassword(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	public void queryDB(){
		try{
			String command = "UPDATE User SET password=?, validated = 0 where email=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, HashPassword.hashpass(this.password));
			statement.setString(2, this.email);
			statement.executeUpdate();
			b = true;
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
	}
	
	public void processResult(){
		result.add(b);
	}
}
