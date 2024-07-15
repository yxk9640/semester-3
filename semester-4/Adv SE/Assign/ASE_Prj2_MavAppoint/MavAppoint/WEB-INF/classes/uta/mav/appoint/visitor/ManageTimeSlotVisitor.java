package uta.mav.appoint.visitor;

import java.util.ArrayList;
import java.util.HashMap;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.StudentUser;
import uta.mav.appoint.team3.command.Email;
import uta.mav.appoint.team3.command.NotificationCommand;

public class ManageTimeSlotVisitor extends Visitor{
	
	@Override
	public ArrayList<Object> check(AdvisorUser user,Object at){
		try{
			AllocateTime a = (AllocateTime)at;
			DatabaseManager dbm = new DatabaseManager();
			ArrayList<Object> appts = dbm.getAppointments(user);
			for(Object apptObj : appts)
			{
				Appointment appt = (Appointment)apptObj;
				
				if(appt.getAdvisingDate().equals(a.getDate())){
					dbm.cancelAppointment(appt.getAppointmentId());
					
					String subject = "Advising Appointment with <PERSON> cancelled"; 
					String text = "Your appointment on " + appt.getAdvisingDate() + " from " + appt.getAdvisingStartTime() + " to " + appt.getAdvisingEndTime() + " has been cancelled."; 
					if(a.getReasons()!=null)
						text += "\n\nReason:\n"+ a.getReasons();
					
					if("yes".equalsIgnoreCase(user.getNotification())){
						Email email = new Email(user.getEmail(), subject.replaceAll("<PERSON>", "Student Id:" + appt.getStudentid()), text);
							
						NotificationCommand notify = email;
						notify.execute();
					}
					
					StudentUser studentUser = dbm.getStudent(appt.getStudentEmail());
					if("yes".equalsIgnoreCase(studentUser.getNotification())){
						Email email = new Email(appt.getStudentEmail(), subject.replaceAll("<PERSON>", user.getPname()), text);
						NotificationCommand notify = email;
						notify.execute();
					}	
				}
			}
			dbm.deleteTimeSlot(a);
			user.setMsg("Time slot deleted.");
		}
		catch(Exception e){
			user.setMsg("Unable to delete time slot.");
		}
		return null;
	}
}
