package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

public class GetUserIDByEmail extends SQLCmd{

	String email;
	
	public GetUserIDByEmail(String e){
		email = e;
	}
	
	@Override
	public void queryDB() {
		try{
				String command = "SELECT userid FROM User WHERE email=?";
				PreparedStatement statement = conn.prepareStatement(command);
				statement.setString(1,email);
				res = statement.executeQuery();
			}
			catch(Exception e){
				System.out.println(e);
			}
	}

	@Override
	public void processResult() {
		try{
			while (res.next()){
				result.add(res.getInt(1));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
