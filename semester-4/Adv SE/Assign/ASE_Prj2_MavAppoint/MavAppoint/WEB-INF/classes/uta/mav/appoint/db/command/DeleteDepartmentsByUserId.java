package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDepartmentsByUserId  extends SQLCmd {
	
	private Integer userId;
	
	Boolean b;
	
	public DeleteDepartmentsByUserId(Integer userId){
		this.userId = userId;
		b = false;
	}
	
	public void queryDB(){
		try{
			String command = "DELETE FROM department_user WHERE userId=?";
			PreparedStatement statement = conn.prepareStatement(command);
			
			statement.setInt(1,userId);
			statement.executeUpdate();
			
			b=true;
		}
		catch(SQLException sq){
			//System.out.println(sq.toString());
		}
	}
	
	public void processResult(){
		result.add(b);
	}

}
