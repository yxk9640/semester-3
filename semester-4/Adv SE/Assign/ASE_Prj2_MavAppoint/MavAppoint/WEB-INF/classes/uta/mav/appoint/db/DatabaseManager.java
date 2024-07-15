package uta.mav.appoint.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import uta.mav.appoint.TimeSlotComponent;
import uta.mav.appoint.beans.*;
import uta.mav.appoint.login.*;


//Client Interface for Bridge Pattern
public class DatabaseManager {
	private DBImplInterface imp = new RDBImpl();
	
	public Integer createUser(LoginUser loginUser) throws SQLException{
		return imp.createUser(loginUser);
	}
		
	public LoginUser checkUser(GetSet set) throws SQLException{
		return imp.checkUser(set);
	}
	
	public Boolean createStudent(StudentUser studentUser) throws SQLException{
		return imp.createStudent(studentUser);
	}
	
	public ArrayList<String> getAdvisors() throws SQLException{
		return imp.getAdvisors();
	}

	public AdvisorUser getAdvisor(String email) throws SQLException{
		return imp.getAdvisor(email);
	}
	
	public StudentUser getStudent(String email) throws SQLException{
		return imp.getStudent(email);
	}
	
	public String deleteAppointmentType(AdvisorUser user, AppointmentType at) throws SQLException{
		return imp.deleteAppointmentType(user, at);
	}
	
	public AdminUser getAdmin(String email) throws SQLException{
		return imp.getAdmin(email);
	}
	
	public Integer getUserIdByEmail(String email) throws SQLException{
		return imp.getUserIdByEmail(email);
	}
	
	public boolean updatePassword(String email, String password) throws SQLException{
		return imp.updatePassword(email, password);
	}
	
	public FacultyUser getFaculty(String email) throws SQLException{
		return imp.getFaculty(email);
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorWaitlistSchedules(ArrayList<AdvisorUser> advisorUsers){
		return imp.getAdvisorWaitlistSchedules(advisorUsers);
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorSchedule(String name) throws SQLException{
		return imp.getAdvisorSchedule(name);
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorSchedules(ArrayList<AdvisorUser> advisorUsers) throws SQLException{
		return imp.getAdvisorSchedules(advisorUsers);
	}

	public HashMap<String, String> createAppointment(Appointment a, String email) throws SQLException{
		return imp.createAppointment(a,email);
	}

	public ArrayList<Object> getAppointments(LoginUser user) throws SQLException{
		if (user instanceof AdvisorUser){
			System.out.println("advisor");
			return imp.getAppointments((AdvisorUser)user);
		}
		else if (user instanceof StudentUser){
			System.out.println("student");
			return imp.getAppointments((StudentUser)user);
		}
		else if (user instanceof AdminUser){
			System.out.println("admin");
			return imp.getAppointments((AdminUser)user);
		}
		else
			System.out.println("no type of user");
			return null;
	}

	public Boolean cancelAppointment(int id) throws SQLException{
		return imp.cancelAppointment(id);
	}
	
	public String addTimeSlot(AllocateTime at) throws SQLException{
		return imp.addTimeSlot(at);
	}
	
	public ArrayList<AppointmentType> getAppointmentTypes(String pname) throws SQLException{
		return imp.getAppointmentTypes(pname);
	}
	
	public Boolean updateAppointment(Appointment a) throws SQLException{
		return imp.updateAppointment(a);
	}

	public Boolean deleteTimeSlot(AllocateTime at) throws SQLException{
		return imp.deleteTimeSlot(at);
	}

	public Appointment getAppointment(String date, String email) throws SQLException{
		return imp.getAppointment(date,email);
	}

	public String addAppointmentType(AdvisorUser user, AppointmentType at) throws SQLException{
		return imp.addAppointmentType(user, at);
	}
	
	public ArrayList<String> getDepartmentStrings() throws SQLException{
		return imp.getDepartmentStrings();
	}
	
	public ArrayList<String> getMajor() throws SQLException{
		return imp.getMajor();
	}
	
	public Boolean createAdvisor(AdvisorUser advisorUser) throws SQLException{
		return imp.createAdvisor(advisorUser);
	}
	
	public ArrayList<AdvisorUser> getAdvisorsOfDepartment(String department) throws SQLException{
		return imp.getAdvisorsOfDepartment(department);
	}
	
	public Boolean updateAdvisors(ArrayList<AdvisorUser> advisorUsers) throws SQLException {
		return imp.updateAdvisors(advisorUsers);
	}
	
	public ArrayList<Department> getDepartments() throws SQLException {
		return imp.getDepartments();
	}
	
	public Department getDepartmentByName(String name) throws SQLException {
		return imp.getDepartmentByName(name);
	}
	
	public Boolean updateUser(LoginUser loginUser) throws SQLException {
		return imp.updateUser(loginUser);
	}
	
	public HashMap<String, ArrayList<String>> getAppointmentsUnderAdvisor(String advisor) {
		return imp.getAppointmentsUnderAdvisor(advisor);
	}
	
	public boolean deleteAdvisor(String advisorList){
		return imp.deleteAdvisor(advisorList);
	}

	public String updateUserNotification(LoginUser user, String notification) {
		if(user instanceof AdvisorUser){
			return imp.updateNotification((AdvisorUser) user, notification);
		} else if(user instanceof StudentUser){
			return imp.updateNotification((StudentUser) user, notification);
		} else
			return null;
	}
	
	public String updateCutOffTime(AdvisorUser user, String time) 
	{
		if(user instanceof AdvisorUser){
			return imp.updateCutOffTime((AdvisorUser) user, time);
		}  else
			return null;
	}
	
	public HashMap<String, String> createWaitlist(Waitlist waitlist){
		return imp.createWaitlist(waitlist);
	}
}

