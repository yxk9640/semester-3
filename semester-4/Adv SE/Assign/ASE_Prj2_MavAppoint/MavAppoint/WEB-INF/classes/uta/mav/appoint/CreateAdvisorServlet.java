package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import uta.mav.appoint.login.Department;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.team3.controller.CreateAdvisorController;
import uta.mav.appoint.db.DatabaseManager;

/*
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
*/

/**
 * Servlet implementation class ViewAppointmentServlet
 */
public class CreateAdvisorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private ArrayList<Department> departments;
    private HttpSession session;   
    private String header;
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		
		try {
			DatabaseManager dbm = new DatabaseManager();
			departments = dbm.getDepartments();
			session.setAttribute("departments", departments);
		} catch(Exception e){
			//System.out.println(e+" RegisterServlet");
		}
		
		if (user == null){
				user = new LoginUser();
				session.setAttribute("user", user);
				response.sendRedirect("/WEB-INF/jsp/views/login.jsp");		
		}
		else{
			try{
				header = "templates/" + user.getHeader() + ".jsp";
			}
			catch(Exception e){
				//System.out.printf(e.toString());
			}
		}
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/create_advisor.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginUser user = (LoginUser)session.getAttribute("user");
		
		try{
			
			String emailAddress = request.getParameter("emailAddress");
			String pName = request.getParameter("pname");
			
			ArrayList<String> departmentsSelected = new ArrayList<String>();
			String departmentFound = departments.get(Integer.valueOf(request.getParameter("drp_department"))).getName();
			departmentsSelected.add(departmentFound);
			
			ArrayList<String> majorsSelected = departments.get(Integer.valueOf(request.getParameter("drp_department"))).getMajors();
			String msg = "";
			java.net.URL appoitnmentTypePath = getClass().getResource("/DefaultAppointmentType");
			msg = CreateAdvisorController.createAdvisor(emailAddress, pName, departmentsSelected, majorsSelected, appoitnmentTypePath);
			user.setMsg(msg);
			
			final String displayMsg = msg;
			String expectedMsg = "Advisor created successfully. An email has been sent to the advisor's account with his/her temporary password";
			if(displayMsg.equals(expectedMsg))
			{
				Thread t = new Thread(new Runnable(){
			        public void run(){
			        	final JDialog dialog = new JDialog();
			        	dialog.setAlwaysOnTop(true); 
			        	JOptionPane.showMessageDialog(dialog, displayMsg);
			        }
			    });
				t.start();	
			}
		}
		catch(Exception e){
			user.setMsg("Unable to create advisor");
			
		} 
		
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(user.getMsg());
		out.flush();
		out.close();
	}
}

