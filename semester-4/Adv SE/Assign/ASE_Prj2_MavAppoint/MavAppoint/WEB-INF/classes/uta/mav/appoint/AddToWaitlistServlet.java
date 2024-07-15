package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.Visitor;

/**
 * Servlet implementation class AddToWaitlistServlet
 */
@WebServlet("/AddToWaitlistServlet")
public class AddToWaitlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession session;   
    String header;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToWaitlistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
				
				request.setAttribute("includeHeader", header);
				request.getRequestDispatcher("/WEB-INF/jsp/views/addToWaitlist.jsp").forward(request, response);
			}
			catch(Exception e){
				PrintWriter out = response.getWriter();
				out.write("Error while loading page");
			}
		}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
