package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

import uta.mav.appoint.beans.Appointment;

public class GetAppointment extends SQLCmd{
	String date;
	String email;
	
	public GetAppointment(String d, String e){
		date=d;
		email=e;
	}
	
	public void queryDB(){
		try{
		String command = "SELECT date,start,end,type FROM appointments a,User u WHERE a.student_userId=u.userId AND u.email=? AND a.date >=? ORDER BY a.date,a.start LIMIT 1";
		PreparedStatement statement = conn.prepareStatement(command);
		statement.setString(1,email);
		statement.setString(2,date);
		res = statement.executeQuery();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public void processResult(){
		Appointment app = new Appointment();
		try{
			while (res.next()){
				app.setAdvisingDate(res.getString(1));
				app.setAdvisingStartTime(res.getString(2));
				app.setAdvisingEndTime(res.getString(3));
				app.setAppointmentType(res.getString(4));
				result.add(app);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
