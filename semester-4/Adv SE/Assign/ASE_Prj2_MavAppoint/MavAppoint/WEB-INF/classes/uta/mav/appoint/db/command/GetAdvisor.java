package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetAdvisor extends SQLCmd{
	private String email;
	private int userId;
	
	public GetAdvisor(String email){
		super();
		this.email = email;
	}
	
	
	@Override
	public void queryDB(){
		try{
			SQLCmd cmd = new GetUserIDByEmail(email);
			cmd.execute();
			userId = (int)cmd.getResult().get(0);
			
			String command = "SELECT password,validated,pName,name_low,name_high,degree_types,department_user.name,Major_User.name,cutOffTime FROM User,User_Advisor,department_user,Major_User WHERE User.userId=? and User_Advisor.userId=? and department_user.userId=? and Major_User.userId=?";
			PreparedStatement statement = conn.prepareStatement(command);
			int i=1;
			statement.setInt(i,userId);
			i++;
			statement.setInt(i,userId);
			i++;
			statement.setInt(i,userId);
			i++;
			statement.setInt(i,userId);
			res = statement.executeQuery();
		}
		catch(SQLException sq){
			sq.printStackTrace();
		}
	}
	
	@Override
	public void processResult(){
		try{
			int row = 1;
			while (res.next()){
				for(int i=1; i<=10; i++)
					if(row==1 || i==10)
						result.add(res.getString(i));
				row++;
			}
		}
		catch(SQLException sq){
			//System.out.println(sq.toString());
		}
		
	}
}
