package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.team3.controller.DeleteTimeSlotController;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.Visitor;

public class ManageTimeSlotServlet extends HttpServlet {
	HttpSession session;
	String header;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user != null){
			header = "templates/" + user.getHeader() + ".jsp";
			try{
				
				String date = request.getParameter("Date");
				String startTime = request.getParameter("StartTime2");
				String endTime = request.getParameter("EndTime2");
				String email = request.getParameter("pname");
				String repeat = request.getParameter("delete_repeat");
				String reason = request.getParameter("delete_reason");
				
				DeleteTimeSlotController.deleteTimeSlotController(date, startTime, endTime,email, user, repeat, reason);

				response.setHeader("Refresh","2; URL=availability");
				request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
				
				Visitor v = new AppointmentVisitor();
				ArrayList<Object> appointments = user.accept(v,null);
				session.removeAttribute("appointments");
				session.setAttribute("appointments", appointments);
				
				return;
			}
			catch(Exception e){
				response.setHeader("Refresh","2; URL=availability");
				request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
				return;
			}
		} else{
				header = "templates/header.jsp";
				request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
				return;
		}
	}
}
