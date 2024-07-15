package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.team3.controller.ScheduleAppointmentController;

public class ScheduleAppointmentServlet extends HttpServlet{
	private static final long serialVersionUID = -5925080374199613248L;
	HttpSession session;
	String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user == null){
			response.sendRedirect("/WEB-INF/jsp/views/login.jsp");
			return;
		}
		else {
			try{
				header = "templates/" + user.getHeader() + ".jsp";
				int id = Integer.parseInt(request.getParameter("id1"));
				ArrayList<TimeSlotComponent> array = (ArrayList<TimeSlotComponent>)session.getAttribute("schedules");
				DatabaseManager dbm = new DatabaseManager();
				ArrayList<AppointmentType> ats = dbm.getAppointmentTypes(request.getParameter("pname"));
				session.setAttribute("timeslot", array.get(id));
				session.setAttribute("appointmenttypes", ats);
				String a = ats.get(0).getEmail();
				AdvisorUser adv = dbm.getAdvisor(a);
				String cutOffTimeStr = adv.getCutOffPreference();
				if(adv != null && !cutOffTimeStr.equals("0"))
				{
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					TimeSlotComponent ts = array.get(id);
					String tsdate = ts.getDate();
					String startTime = ts.getStartTime();
					String tsTime = tsdate+ " " + startTime;
					Calendar cal = Calendar.getInstance();
					System.out.println();
					Date startDate = dateFormat.parse(tsTime);
					long difference = startDate.getTime() - Calendar.getInstance().getTimeInMillis();
					long hours = TimeUnit.MILLISECONDS.toHours(difference);
					long cutOffTime = Long.parseLong(cutOffTimeStr);
					if(hours < cutOffTime)
					{
						request.setAttribute("includeHeader", header);
						request.getRequestDispatcher("/WEB-INF/jsp/views/schedule_warning.jsp").forward(request, response);					}
					else
					{
						request.setAttribute("includeHeader", header);
						request.getRequestDispatcher("/WEB-INF/jsp/views/schedule_appointment.jsp").forward(request, response);
					}
					
					System.out.println();
				}
				else
				{
					request.setAttribute("includeHeader", header);
					request.getRequestDispatcher("/WEB-INF/jsp/views/schedule_appointment.jsp").forward(request, response);
				}
				
			}
			catch(Exception e){
				PrintWriter out = response.getWriter();
				out.write("Error while loading page");
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		String phoneNumber = request.getParameter("phoneNumber");
		String appointmentId = request.getParameter("id2");
		String studentId = request.getParameter("studentid");
		String description = request.getParameter("description");
		String appType = request.getParameter("apptype");
		String pName = request.getParameter("pname");
		String duration = request.getParameter("duration");
		String start = request.getParameter("start");
		String email = request.getParameter("email");
	
		try{
			ScheduleAppointmentController.scheduleAppointment(phoneNumber, appointmentId, studentId,
					description, appType, pName, duration, start, email);
			response.setHeader("Refresh","2; URL=advising");
			request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
			return;
		}
		catch(Exception e){
			response.setHeader("Refresh","2; URL=advising");
			request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
			return;
		}
	}
}

