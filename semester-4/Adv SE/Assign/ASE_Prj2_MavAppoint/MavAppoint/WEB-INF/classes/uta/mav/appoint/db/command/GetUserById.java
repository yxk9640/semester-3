package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.login.*;

public class GetUserById extends SQLCmd{
	private Integer userId;
	private LoginUser loginUser;
	
	public GetUserById(LoginUser loginUser){
		super();
		this.loginUser = loginUser;
		this.userId = loginUser.getUserId();
		
		//System.out.println("About to get majors");
		SQLCmd sqlCmd = new GetMajorsByUserId(userId);
		sqlCmd.execute();
		
		ArrayList<String> majors = new ArrayList<String>();
		for(int i=0; i<sqlCmd.getResult().size(); i++)
			majors.add((String)sqlCmd.getResult().get(i));
		
		this.loginUser.setMajors(majors);

		//System.out.println("About to get departments");
		sqlCmd = new GetDepartmentsByUserId(userId);
		sqlCmd.execute();
		
		ArrayList<String> departments = new ArrayList<String>();
		for(int i=0; i<sqlCmd.getResult().size(); i++)
			departments.add((String)sqlCmd.getResult().get(i));
		
		this.loginUser.setDepartments(departments);
		//System.out.println("Just set departments");
	}
	
	
	@Override
	public void queryDB(){
		try{
			
			String command = "SELECT email, password, role, validated from User where userid=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1,userId);
			//System.out.println("Executing query");
			res = statement.executeQuery();
		}
		catch(SQLException sq){
			//System.out.printf(sq.toString());
		}
	}
	
	@Override
	public void processResult(){
		try{
			//System.out.println("Setting restult");
			int i = 1;
			res.next();
			
			loginUser.setEmail(res.getString(i));
			i++;
			loginUser.setPassword(res.getString(i));
			i++;
			loginUser.setRole(res.getString(i));
			i++;
			loginUser.setValidated(res.getInt(i));

			//System.out.println("Set restult");
			result.add(loginUser);
			//System.out.println("Added restult");
		}
		catch(SQLException sq){
			//System.out.println(sq.toString());
		}
		
	}
}
