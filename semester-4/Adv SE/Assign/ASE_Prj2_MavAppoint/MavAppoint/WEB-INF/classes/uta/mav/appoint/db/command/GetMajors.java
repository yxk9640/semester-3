package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetMajors extends SQLCmd{
	
	public GetMajors(){
		super();
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT name FROM major";
			PreparedStatement statement = conn.prepareStatement(command);
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

