package uta.mav.appoint.beans;

import java.io.Serializable;

public class Waitlist implements Serializable{

	private static final long serialVersionUID = -3734663824525723817L;

    String studentID;
	String studentEmail;
	String studentPhoneNum;
	String advisorName;
	String date;
	int waitlistID;
	
	public int getWaitlistID() {
		return waitlistID;
	}
	public void setWaitlistID(int waitlistID) {
		this.waitlistID = waitlistID;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentPhoneNum() {
		return studentPhoneNum;
	}
	public void setStudentPhoneNum(String studentPhoneNum) {
		this.studentPhoneNum = studentPhoneNum;
	}
	public String getAdvisorName() {
		return advisorName;
	}
	public void setAdvisorName(String advisorName) {
		this.advisorName = advisorName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
}

