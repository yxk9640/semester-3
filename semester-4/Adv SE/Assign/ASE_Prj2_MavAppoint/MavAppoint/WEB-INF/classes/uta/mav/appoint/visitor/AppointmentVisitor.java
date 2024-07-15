package uta.mav.appoint.visitor;

import java.util.ArrayList;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.AdminUser;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.login.StudentUser;

public class AppointmentVisitor extends Visitor{
	
	@Override
	public ArrayList<Object> check(AdvisorUser user,Object o){
		try{
			DatabaseManager dbm = new DatabaseManager();
			ArrayList<Object> appointments = dbm.getAppointments(user);
			if(appointments==null) {
				System.out.println("no appointments - appointment visitor - advisor");
			}
			return appointments;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ArrayList<Object> check(StudentUser user,Object o){
		try{
			DatabaseManager dbm = new DatabaseManager();
			ArrayList<Object> appointments = dbm.getAppointments(user);
			if(appointments==null) {
				System.out.println("no appointments - appointment visitor - student");
			}
			return appointments;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ArrayList<Object> check(AdminUser user,Object o){
		try{
			DatabaseManager dbm = new DatabaseManager();
			ArrayList<Object> appointments = dbm.getAppointments(user);
			if(appointments==null) {
				System.out.println("no appointments - appointment visitor - admin");
			}
			return appointments;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ArrayList<Object> check(LoginUser user,Object o){
		try{
			DatabaseManager dbm = new DatabaseManager();
			ArrayList<Object> appointments = dbm.getAppointments(user);
			if(appointments==null) {
				System.out.println("no appointments - appointment visitor - login");
			}
			return appointments;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
