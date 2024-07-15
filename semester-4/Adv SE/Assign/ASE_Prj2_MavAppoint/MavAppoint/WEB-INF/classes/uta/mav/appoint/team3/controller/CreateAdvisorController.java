package uta.mav.appoint.team3.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.team3.security.RandomPasswordGenerator;
import uta.mav.appoint.team3fall.util.Util;

/**
 * Controller for creating advisor
 * 
 * @author SDP Team 3
 *
 */
public class CreateAdvisorController {

	public static String createAdvisor(String emailAddress, String pName, ArrayList<String> departmentsSelected,
			ArrayList<String> majorsSelected, java.net.URL urlPath) throws SQLException {

		if (!Util.validateEmail(emailAddress))
			return "Email Address Invalid!!";

		AdvisorUser advisorUser = new AdvisorUser();
		advisorUser.setEmail(emailAddress);
		advisorUser.setPname(pName);
		advisorUser.setDepartments(departmentsSelected);
		advisorUser.setPassword(RandomPasswordGenerator.genpass());
		advisorUser.setNotification("yes");
		advisorUser.setMajors(majorsSelected);
		advisorUser.setNameLow("A");
		advisorUser.setNameHigh("Z");
		advisorUser.setDegType(7);

		DatabaseManager dbm = new DatabaseManager();
		if (dbm.createAdvisor(advisorUser)) {

			String toEmail = advisorUser.getEmail();			
			Email userEmail = new Email("MavAppoint Account Created - User Information",
					"<p>An advisor account has been created for your email address! Your login information is:</p>"
					+"<p>Role: Advisor </p>"
					+"<p>Username: " + advisorUser.getPname() +"</p>"
					+"<p>Password: "+advisorUser.getPassword()+"</p>"
					+"<br><br>Click here to login <a href='http://bartsimpson:8080/MavAppoint/login'>Login</a>",
					toEmail);
			userEmail.sendMail();			

			File file = new File(urlPath.getPath());
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				String x;
				while ((x = br.readLine()) != null) {
					String[] parts = x.split(":");
					String courseName = parts[0];
					String duration = parts[1];

					AppointmentType at = new AppointmentType();
					at.setDuration(Integer.parseInt(duration));
					at.setEmail(advisorUser.getEmail());
					at.setType(courseName);
					dbm.addAppointmentType(advisorUser, at);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "Advisor created successfully. An email has been sent to the advisor's account with his/her temporary password";
	}

}
