package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetDepartmentNames extends SQLCmd{
	
	public GetDepartmentNames(){
		super();
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT name FROM department";
			PreparedStatement statement = conn.prepareStatement(command);
			res = statement.executeQuery();	
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
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
			System.out.println(sq.toString());
		}
		
	}
}
