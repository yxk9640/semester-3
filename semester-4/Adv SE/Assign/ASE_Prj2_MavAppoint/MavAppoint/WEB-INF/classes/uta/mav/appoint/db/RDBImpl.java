package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import uta.mav.appoint.PrimitiveTimeSlot;
import uta.mav.appoint.TimeSlotComponent;
import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.CreateAdvisorBean;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.beans.RegisterBean;
import uta.mav.appoint.beans.Waitlist;
import uta.mav.appoint.db.command.*;
import uta.mav.appoint.flyweight.TimeSlotFlyweightFactory;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.*;
import uta.mav.appoint.team3fall.singleton.ConfigFileReader;

/**
 * Bridge Pattern Concrete class: Connect to MySQL
 * @author Ruchi.U
 *
 */
public class RDBImpl implements DBImplInterface{

	public Connection connectDB(){
		try
	    {
	    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    String jdbcUrl = "jdbc:mysql://";
	    jdbcUrl += ConfigFileReader.getInstance().getValue("MYSQL_SERVER") + ":";
	    jdbcUrl += ConfigFileReader.getInstance().getValue("MYSQL_PORT") + "/";
	    jdbcUrl += ConfigFileReader.getInstance().getValue("MYSQL_DATABASE");
	    String userid = ConfigFileReader.getInstance().getValue("MYSQL_USER");
	    String password = ConfigFileReader.getInstance().getValue("MYSQL_PASSWORD");
	    Connection conn = DriverManager.getConnection(jdbcUrl,userid,password);
	    return conn;
	    }
	    catch (Exception e){
	        System.out.println(e.toString());
	    }
	    return null;
	}
	
			
	//user login checking, check username and password against database
	//then return role if a match is found
	//using command pattern
	public LoginUser checkUser(GetSet set) throws SQLException{
		LoginUser user = null;
		try{
			SQLCmd cmd = new CheckUser(set.getEmailAddress(), set.getPassword());
			System.out.println(set.getEmailAddress());
			System.out.println(set.getPassword());
			cmd.execute();
			//System.out.println("Result = "+cmd.getResult());
			user = (LoginUser)(cmd.getResult()).get(0);
			
		}
		catch(Exception e){
			//System.out.println(e+" -- FOUND IN -- "+this.getClass().getSimpleName());
			System.out.println(e);
		}
		return user;
	}
	
	public Boolean updateAppointment(Appointment a){
		
		try{
			SQLCmd cmd = new UpdateAppointment(a);
			cmd.execute();
			//System.out.println("Finished update");

			//System.out.println("Found id " + a.getAppointmentId());
			cmd = new GetAppointmentById(a);
			cmd.execute();
			//System.out.println("Finished getting appointment");
			
			return true;
		}
		catch(Exception e){
			//System.out.println(e+"-- Found in -- "+ this.getClass().getSimpleName());
		}
		return false;
	}
	
	public Boolean createStudent(StudentUser studentUser){
		try{
			SQLCmd cmd = new CreateUser(studentUser);
			cmd.execute();
			//System.out.println("Created User"+cmd.getResult());
			
			cmd = new CreateStudent(studentUser);
			cmd.execute();
			//System.out.println(cmd.getResult());
			return (Boolean)cmd.getResult().get(0);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//using command pattern
	public ArrayList<String> getAdvisors() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetAdvisors();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			//System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorSchedule(String name){
		ArrayList<TimeSlotComponent> array = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			if (name.equals("all")){
			String command = "SELECT pname,date,start,end,id FROM user,Advising_Schedule,User_Advisor "
							+ "WHERE user.userid=User_Advisor.userid AND user.userid=Advising_Schedule.userid AND studentId is null";
			statement = conn.prepareStatement(command);
			}
			else{
				String command = "SELECT pname,date,start,end,id FROM USER,Advising_Schedule,User_Advisor "
								+ "WHERE USER.userid=User_Advisor.userid AND USER.userid=Advising_Schedule.userid AND USER.userid=Advising_Schedule.userid AND User_Advisor.pname=? AND studentId is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,name);
			}	
			ResultSet res = statement.executeQuery();
			while(res.next()){
				//Use flyweight factory to avoid build cost if possible
				PrimitiveTimeSlot set = (PrimitiveTimeSlot)TimeSlotFlyweightFactory.getInstance().getFlyweight(res.getString(1)+"-"+res.getString(2),res.getString(3));
				set.setName(res.getString(1));
				set.setDate(res.getString(2));
				set.setStartTime(res.getString(3));
				set.setEndTime(res.getString(4));
				set.setUniqueId(res.getInt(5));
				array.add(set);
			}
			array = TimeSlotHelpers.createCompositeTimeSlot(array);
			conn.close();
		}
		catch(Exception e){
			//System.out.printf(e.toString());
		}
		return array;
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorWaitlistSchedules(ArrayList<AdvisorUser> advisorUsers){
		ArrayList<TimeSlotComponent> timeSlots = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			for(int i=0; i<advisorUsers.size(); i++)
			{
				String command = "SELECT pname,date,start,end,id FROM USER,Advising_Schedule,User_Advisor "
								+ "WHERE USER.userid=User_Advisor.userid AND USER.userid=Advising_Schedule.userid AND USER.userid=Advising_Schedule.userid AND User_Advisor.pname=? AND studentId is not null";
				statement = conn.prepareStatement(command);
				statement.setString(1,advisorUsers.get(i).getPname());
				ResultSet res = statement.executeQuery();
				while(res.next()){
					if(isDateAfterToday(res.getString(2)))
					{
						//Use flyweight factory to avoid build cost if possible
						PrimitiveTimeSlot set = (PrimitiveTimeSlot)TimeSlotFlyweightFactory.getInstance().getFlyweight(res.getString(1)+"-"+res.getString(2),res.getString(3));
						set.setName(res.getString(1));
						set.setDate(res.getString(2));
						set.setStartTime(res.getString(3));
						set.setEndTime(res.getString(4));
						set.setUniqueId(res.getInt(5));
						timeSlots.add(set);
					}
				}
			}	
			timeSlots = TimeSlotHelpers.createCompositeTimeSlot(timeSlots);
			conn.close();
		}
		catch(Exception e){
			//System.out.printf(e.toString());
		}
		return timeSlots;
	}
	
	boolean isDateAfterToday(String startDateString)
	{
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date startDate;
	    try {
	        startDate = df.parse(startDateString);
	        Date today = new Date();
	        
	        Calendar cal1 = Calendar.getInstance();
        	Calendar cal2 = Calendar.getInstance();
        	cal1.setTime(startDate);
        	cal2.setTime(today);
        	
        	if(cal1.after(cal2) || cal1.equals(cal2)){
        		return true;
        	}
        	else return false;
        	
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		return false;
	}

	public ArrayList<TimeSlotComponent> getAdvisorSchedules(ArrayList<AdvisorUser> advisorUsers){
		ArrayList<TimeSlotComponent> timeSlots = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			for(int i=0; i<advisorUsers.size(); i++)
			{
				String command = "SELECT pname,date,start,end,id FROM USER,Advising_Schedule,User_Advisor "
								+ "WHERE USER.userid=User_Advisor.userid AND USER.userid=Advising_Schedule.userid AND USER.userid=Advising_Schedule.userid AND User_Advisor.pname=? AND studentId is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,advisorUsers.get(i).getPname());
				ResultSet res = statement.executeQuery();
				while(res.next()){
					//Use flyweight factory to avoid build cost if possible
					PrimitiveTimeSlot set = (PrimitiveTimeSlot)TimeSlotFlyweightFactory.getInstance().getFlyweight(res.getString(1)+"-"+res.getString(2),res.getString(3));
					set.setName(res.getString(1));
					set.setDate(res.getString(2));
					set.setStartTime(res.getString(3));
					set.setEndTime(res.getString(4));
					set.setUniqueId(res.getInt(5));
					timeSlots.add(set);
				}
			}	
			timeSlots = TimeSlotHelpers.createCompositeTimeSlot(timeSlots);
			conn.close();
		}
		catch(Exception e){
			//System.out.printf(e.toString());
		}
		return timeSlots;
	}

	public HashMap<String, String> createAppointment(Appointment appointment, String email){
		HashMap<String, String> result = new HashMap<String, String>();
		int student_id = 0;
		int advisor_id = 0;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT userid from user where email=?";
			statement=conn.prepareStatement(command);
			statement.setString(1,email);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				student_id = rs.getInt(1);
			}
			
			command = "SELECT notification from user_student where userId=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,student_id);
			rs = statement.executeQuery();
			while(rs.next()){
				result.put("student_notify", rs.getString("notification"));
			}
			
			command = "SELECT userid FROM User_Advisor WHERE User_Advisor.pname=?";
			statement=conn.prepareStatement(command);
			statement.setString(1, appointment.getPname());
			rs = statement.executeQuery();
			while(rs.next()){
				advisor_id = rs.getInt(1);
			}
			
			command = "SELECT notification from user_advisor where userId=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,advisor_id);
			rs = statement.executeQuery();
			while(rs.next()){
				result.put("advisor_notify", rs.getString("notification"));
			}
			
			command = "SELECT email from user where userId=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,advisor_id);
			rs = statement.executeQuery();
			while(rs.next()){
				result.put("advisor_email", rs.getString("email"));
			}
			
			//check for slots already taken
			command = "SELECT COUNT(*) FROM Advising_Schedule WHERE userid=? AND date=? AND start=? AND end=? AND studentId is not null";
			statement = conn.prepareStatement(command);
			statement.setInt(1, advisor_id);
			statement.setString(2, appointment.getAdvisingDate());
			statement.setString(3, appointment.getAdvisingStartTime());
			statement.setString(4, appointment.getAdvisingEndTime());
			rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) < 1){
					command = "INSERT INTO appointments (id,advisor_userid,student_userid,date,start,end,type,studentId,description,student_email,student_cell)"
							+"VALUES(?,?,?,?,?,?,?,?,?,?,?)";
					statement = conn.prepareStatement(command);
					statement.setInt(1, appointment.getAppointmentId());
					statement.setInt(2,advisor_id);
					statement.setInt(3,student_id);
					statement.setString(4,appointment.getAdvisingDate());
					statement.setString(5,appointment.getAdvisingStartTime());
					statement.setString(6,appointment.getAdvisingEndTime());
					statement.setString(7,appointment.getAppointmentType());
					statement.setInt(8,Integer.parseInt(appointment.getStudentId()));
					statement.setString(9,appointment.getDescription());
					statement.setString(10,email);
					statement.setString(11,appointment.getStudentPhoneNumber());
					
					//System.out.println("Update about to execute");
					statement.executeUpdate();
					//System.out.println("Should have set "+appointment.getStudentPhoneNumber());
					//System.out.println("Update should have executed");
					
					command = "UPDATE Advising_Schedule SET studentId=? where userid=? AND date=? and start >= ? and end <= ?";
					statement=conn.prepareStatement(command);
					statement.setInt(1,Integer.parseInt(appointment.getStudentId()));
					statement.setInt(2, advisor_id);
					statement.setString(3, appointment.getAdvisingDate());
					statement.setString(4, appointment.getAdvisingStartTime());
					statement.setString(5, appointment.getAdvisingEndTime());
					statement.executeUpdate();
					result.put("response", "success");
				}
			}
			conn.close();
		}
		catch(Exception e){
			//System.out.printf(e.toString());
		}
		return result;
	}

	public ArrayList<Object> getAppointments(AdvisorUser user){
		ArrayList<Object> Appointments = new ArrayList<Object>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
//			String command = "SELECT User_Advisor.pname,User_Advisor.email,date,start,end,type,id,Appointments.description,studentId,Appointments.student_email FROM USER,Appointments,User_Advisor "
			String command = "SELECT User_Advisor.pname, User.email, appointments.date, appointments.start, appointments.end, appointments.type, appointments.Id, appointments.description, appointments.studentId, appointments.student_email, appointments.student_cell FROM User, appointments, User_Advisor WHERE User.email=? AND User.userId=appointments.advisor_userId AND User_Advisor.userId=appointments.advisor_userId";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				set.setDescription(rs.getString(8));
				set.setStudentId(rs.getString(9));
				set.setStudentEmail(rs.getString(10));
				set.setStudentPhoneNumber(rs.getString(11));
				Appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return Appointments;
	}

	public ArrayList<Object> getAppointments(StudentUser user){
		ArrayList<Object> Appointments = new ArrayList<Object>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
//			String command = "SELECT User_Advisor.pname,User_Advisor.email,date,start,end,type,id,description,student_email FROM USER,Appointments,User_Advisor "
					String command = "SELECT User_Advisor.pname, User.email, appointments.date, appointments.start, appointments.end, appointments.type, appointments.Id, appointments.description, appointments.student_email, appointments.student_cell FROM User, appointments, User_Advisor "
						+ "WHERE User.email=? AND User.userId=appointments.student_userId AND User_Advisor.userId=appointments.advisor_userId";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				set.setDescription(rs.getString(8));
				set.setStudentId("Advisor only");
				set.setStudentEmail(rs.getString(9));
				set.setStudentPhoneNumber(rs.getString(10));
				Appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			//System.out.printf(e.toString());
		}
		
		return Appointments;
	}

	public ArrayList<Object> getAppointments(AdminUser user){
		ArrayList<Object> Appointments = new ArrayList<Object>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			
			String command = "select name from department_user where userId = ?";
			statement = conn.prepareStatement(command);
			statement.setInt(1, user.getUserId());
			ResultSet rs = statement.executeQuery();
			System.out.println(rs);
			rs.next();
			String name = rs.getString(1);
			
			command = "select department_user.userId, User_Advisor.pName, appointments.date, appointments.start, appointments.end, appointments.type, User.email, appointments.description, appointments.studentId, appointments.student_email, appointments.student_cell, appointments.Id  from department_user, User, appointments, User_Advisor where name = ? AND department_user.userId = User.userId AND appointments.advisor_userId = User.userId AND User_Advisor.userId = User.userId;		";
			statement = conn.prepareStatement(command);
			statement.setString(1, name);
			rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAdvisorEmail(rs.getString(7));
				set.setDescription(rs.getString(8));
				set.setStudentId(rs.getString(9));
				set.setStudentEmail(rs.getString(10));
				set.setStudentPhoneNumber(rs.getString(11));
				set.setAppointmentId(rs.getInt(12));
				Appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return Appointments;
	}
	
	public Boolean cancelAppointment(int id){
		Boolean result = false;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT count(*),date,start, end from appointments where Id=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) == 1){
					command = "DELETE FROM appointments where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.executeUpdate();
					command = "UPDATE Advising_Schedule SET studentId=null where date=? AND start >=? AND end <=?";
					statement=conn.prepareStatement(command);
					statement.setString(1, rs.getString(2));
					statement.setString(2,rs.getString(3));
					statement.setString(3, rs.getString(4));
					statement.executeUpdate();
					result = true;
				}
			}
			conn.close();	
		}
		catch(SQLException e){
			//System.out.printf("Error in Database: " + e.toString());
			return false;
		}
		return result;
	}
	
	public String addTimeSlot(AllocateTime at){
		SQLCmd cmd = new GetUserIDByEmail(at.getEmail());
		cmd.execute();
		int id = (int)cmd.getResult().get(0);
		cmd = new CheckTimeSlot(at,id);
		cmd.execute();
		if ((Boolean)cmd.getResult().get(0) == true){
			cmd = new AddTimeSlot(at,id);
			cmd.execute();
			return (String)cmd.getResult().get(0);
		}
		else{
			return "Unable to add time slot.";
		}
	}
	
	public Integer getUserIdByEmail(String email)
	{
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		if(cmd.getResult().size() > 0)
			return (Integer)cmd.getResult().get(0);
		else
			return null;
	}
	
	
	public AdvisorUser getAdvisor(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetAdvisorById(userId);
		cmd.execute();
		return (AdvisorUser)cmd.getResult().get(0);
	}
	
	public String deleteAppointmentType(AdvisorUser user, AppointmentType at){
		String msg = null;
		SQLCmd cmd = new GetUserIDByEmail(user.getEmail());
		cmd.execute();
		cmd = new DeleteAppointmentType(at, (int)cmd.getResult().get(0));
		cmd.execute();
		return (String)cmd.getResult().get(0);
	}
	
	public StudentUser getStudent(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetStudentById(userId);
		cmd.execute();
		return (StudentUser)cmd.getResult().get(0);
	}
	
	public AdminUser getAdmin(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetAdminById(userId);
		cmd.execute();
		return (AdminUser)cmd.getResult().get(0);
	}
	
	public FacultyUser getFaculty(String email){
		SQLCmd cmd = new GetUserIDByEmail(email);
		cmd.execute();
		Integer userId = (Integer)cmd.getResult().get(0);
		
		cmd = new GetFacultyById(userId);
		cmd.execute();
		return (FacultyUser)cmd.getResult().get(0);
	}
	
	public ArrayList<AppointmentType> getAppointmentTypes(String pname){
			ArrayList<AppointmentType> ats = new ArrayList<AppointmentType>();
			try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT type,duration,user.email FROM  Appointment_Types,User_Advisor,user WHERE Appointment_Types.userid=User_Advisor.userid AND User_Advisor.userid=user.userid AND User_Advisor.pname=?";
			statement = conn.prepareStatement(command);
			statement.setString(1,pname);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				AppointmentType at = new AppointmentType();
				at.setType(rs.getString(1));
				at.setDuration(rs.getInt(2));
				at.setEmail(rs.getString(3));
				ats.add(at);
			}
			conn.close();
		}
		catch(Exception e){
			//System.out.println(e);
		}
		return ats;
	
	}
	
	public Boolean deleteTimeSlot(AllocateTime at){
		Boolean b;
		SQLCmd cmd = new DeleteTimeSlot(at);
		cmd.execute();
		b = (Boolean)(cmd.getResult()).get(0);
		return b;
	}
	
	public Appointment getAppointment(String d, String e){
		Appointment app = null;
		try{
			SQLCmd cmd = new GetAppointment(d,e);
			cmd.execute();
			if (cmd.getResult().size() > 0){
				app = (Appointment)(cmd.getResult()).get(0);
			}
		}
		catch(Exception ex){
			//System.out.println(ex);
		}
		return app;
	}
	
	public Integer createUser(LoginUser loginUser){
		Integer userId = -1;
		try{
			SQLCmd cmd = new CreateUser(loginUser);
			cmd.execute();
			if ((Boolean)cmd.getResult().get(0)){
				cmd = new GetUserIDByEmail(loginUser.getEmail());
				cmd.execute();
				userId = (int)cmd.getResult().get(0);
			}
			else{
				//System.out.println("User not created"+"RDBImpl");
			}
		}
		catch(Exception e){
			//System.out.println(e+"RDBImpl");
		}

		return userId;
	}
	
	public Boolean createAdvisor(AdvisorUser advisorUser){
		advisorUser.setRole("advisor");
		
		try{
			SQLCmd cmd = new CreateUser(advisorUser);
			cmd.execute();
			cmd = new CreateAdvisor(advisorUser);
			cmd.execute();
			//System.out.println("Created Advisor");
				return (Boolean)cmd.getResult().get(0);
		}
		catch(Exception e){
			//System.out.println(e+this.getClass().getName());
			return false;
		}
	}
	
	public String addAppointmentType(AdvisorUser user, AppointmentType at){
		String msg = null;
		SQLCmd cmd = new GetUserIDByEmail(user.getEmail());
		cmd.execute();
		cmd = new AddAppointmentType(at, (int)cmd.getResult().get(0));
		cmd.execute();
		return (String)cmd.getResult().get(0);
	}
	
	//using command pattern
	public ArrayList<String> getDepartmentStrings() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetDepartmentNames();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			//System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	//using command pattern
	public ArrayList<String> getMajor() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetMajors();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			sq.printStackTrace();
		}
		return arraylist;
	}
	
	public ArrayList<AdvisorUser> getAdvisorsOfDepartment(String department) throws SQLException {
		
		ArrayList<AdvisorUser> advisorUsers = new ArrayList<AdvisorUser>();
		SQLCmd sqlCmd = new GetAdvisorIdsOfDepartment(department);
		sqlCmd.execute();
		//System.out.println("User Ids "+sqlCmd.getResult());
		
		for(int i=0; i<sqlCmd.getResult().size(); i++)
		{
			Integer userId = Integer.valueOf((String)sqlCmd.getResult().get(i));
			//System.out.println("User Id "+userId);
			SQLCmd sqlCmd2 = new GetAdvisorById(userId);
			sqlCmd2.execute();
			//System.out.println("Advisor "+sqlCmd2.getResult());
			AdvisorUser advisorUser = (AdvisorUser)sqlCmd2.getResult().get(0);
			advisorUsers.add(advisorUser);
		}
		
		return advisorUsers;
	}
	
	public Boolean updateAdvisors(ArrayList<AdvisorUser> advisorUsers) throws SQLException {
		
		for(int i=0; i<advisorUsers.size(); i++)
		{
			SQLCmd sqlCmd = new UpdateAdvisor(advisorUsers.get(i));
			sqlCmd.execute();
		}	
		return true;
	}
	
	public ArrayList<Department> getDepartments() throws SQLException {
		SQLCmd sqlCmd = new GetDepartmentNames();
		sqlCmd.execute();
		Department department;
		
		ArrayList<Department> departments = new ArrayList<Department>();
		for(int depIndex=0; depIndex<sqlCmd.getResult().size(); depIndex++)
		{
			String depName = (String)sqlCmd.getResult().get(depIndex);
			SQLCmd sqlCmd2 = new GetDepartmentByName(depName);
			sqlCmd2.execute();
		
			department = (Department)sqlCmd2.getResult().get(0);
			departments.add(department);
		}
		return departments;
	}
	
	public Department getDepartmentByName(String name) throws SQLException {
		SQLCmd sqlCmd2 = new GetDepartmentByName(name);
		sqlCmd2.execute();
		return (Department)sqlCmd2.getResult().get(0);
	}
	
	public Boolean updateUser(LoginUser loginUser) throws SQLException {
		SQLCmd sqlCmd = new UpdateUser(loginUser);
		sqlCmd.execute();
		return true;
	}
	
	public HashMap<String, ArrayList<String>> getAppointmentsUnderAdvisor(String advisorList){
		
		Connection conn = this.connectDB();
		ResultSet rs = null;
		HashMap<String, ArrayList<String>> advisorNameStudentMap = new HashMap<String, ArrayList<String>>();
		Statement statement = null;
		try {
			String command = "SELECT distinct adv.pName, app.student_email FROM appointments app, User_Advisor adv WHERE adv.userId = app.advisor_userId AND adv.userId IN (" + advisorList + ")";
			statement = conn.createStatement();
			rs = statement.executeQuery(command);
			while(rs.next()){
				String advisor = rs.getString("pName");
				String studentEmail = rs.getString("student_email");
				ArrayList<String> studentEmails = new ArrayList<String>();
				if(advisorNameStudentMap.containsKey(advisor)){
					studentEmails = advisorNameStudentMap.remove(advisor);
				}
				studentEmails.add(studentEmail);
				advisorNameStudentMap.put(advisor, studentEmails);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
		return advisorNameStudentMap;
	}
	
	public boolean deleteAdvisor(String advisorList){
		
		Connection conn = this.connectDB();
		Statement statement = null;
		boolean response = false;
		try {
			conn.setAutoCommit(false);
			String command = "DELETE FROM appointments WHERE advisor_userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			command = "DELETE FROM advising_schedule WHERE userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			command = "DELETE FROM department_user WHERE userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			command = "DELETE FROM major_user WHERE userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			command = "DELETE FROM User_Advisor WHERE userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			command = "DELETE FROM user WHERE userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			command = "DELETE FROM appointment_types WHERE userId IN ("+advisorList+")";
			statement = conn.createStatement();
			statement.executeUpdate(command);
			
			conn.commit();
			response = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			response = false;
		} finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
		return response;
	}


	@Override
	public String updateNotification(StudentUser user, String notification) {
		
		Connection conn = this.connectDB();
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement;
			String command = "UPDATE user_student SET notification = ? WHERE userId = ?";
			statement=conn.prepareStatement(command);
			statement.setString(1, notification);
			statement.setInt(2, user.getUserId());
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				if(conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Updated successfully";
	}


	@Override
	public String updateNotification(AdvisorUser user, String notification) {
		
		Connection conn = this.connectDB();
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement;
			String command = "UPDATE User_Advisor SET notification = ? WHERE userId = ?";
			statement=conn.prepareStatement(command);
			statement.setString(1, notification);
			statement.setInt(2, user.getUserId());
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				if(conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Updated successfully";
	}
	
	public boolean updatePassword(String email, String password)
	{
		try
		{
			SQLCmd cmd = new UpdatePassword(email, password);
			cmd.execute();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}	
	}
	
	
	public HashMap<String, String> createWaitlist(Waitlist waitlist)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		int advisor_id = 0;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT userid FROM User_Advisor WHERE User_Advisor.pname=?";
			statement=conn.prepareStatement(command);
			statement.setString(1, waitlist.getAdvisorName());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				advisor_id = rs.getInt(1);
			}
			
			command = "SELECT email from user where userId=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,advisor_id);
			rs = statement.executeQuery();
			while(rs.next()){
				result.put("advisor_email", rs.getString("email"));
			}
			
			//check for slots already taken
			command = "SELECT COUNT(*) FROM waitlist_schedule WHERE userid=? AND date=? AND studentID=?";
			statement = conn.prepareStatement(command);
			statement.setInt(1, advisor_id);
			statement.setString(2, waitlist.getDate());
			statement.setString(3, String.valueOf(waitlist.getStudentID()));
	        int rowCount = 0;
			rs = statement.executeQuery();
			while(rs.next()){
	            rowCount = Integer.parseInt(rs.getString("count(*)"));
			}
			
			if(rowCount == 0)
			{
				command = "INSERT INTO waitlist_schedule (id,userid,date,student_phoneNo,student_email,studentId)"
						+"VALUES(?,?,?,?,?,?,?)";
				statement = conn.prepareStatement(command);
				statement.setInt(1, waitlist.getWaitlistID());
				statement.setInt(2,advisor_id);
				statement.setString(3,waitlist.getDate());
				statement.setString(4,waitlist.getStudentPhoneNum());
				statement.setString(5,waitlist.getStudentEmail());
				statement.setString(6,waitlist.getStudentID());
				statement.executeUpdate();
				result.put("response", "success");
			}
			conn.close();
		}
		catch(Exception e){
			//System.out.printf(e.toString());
		}
		return result;
	}
	
	
	@Override
	public String updateCutOffTime(AdvisorUser user, String time) 
	{
		Connection conn = this.connectDB();
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement;
			String command = "UPDATE User_Advisor SET cutOffTime = ? WHERE userId = ?";
			statement=conn.prepareStatement(command);
			statement.setString(1, time);
			statement.setInt(2, user.getUserId());
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				if(conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Updated successfully";
	}
	
}


