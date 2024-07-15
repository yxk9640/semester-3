package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetDepartmentsByUserId  extends SQLCmd{
	private Integer userId;
	
	public GetDepartmentsByUserId(Integer userId){
		super();
		this.userId = userId;
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "select name from department_user where userId =?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1, userId);
			res = statement.executeQuery();	
		}
		catch(SQLException sq){
			//System.out.printf(sq.toString());
		}
	}
	
	
	
	@Override
	public void processResult(){
		try{
			while (res.next()){
				result.add(res.getString(1));
			}
		}
		catch(SQLException sq){
			//System.out.println(sq.toString());
		}
		
	}
}
