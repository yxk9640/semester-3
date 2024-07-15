package uta.mav.appoint;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.team3.security.RandomPasswordGenerator;

/**
 * Servlet implementation class ForgotPasswordServlet
 */
@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		session.setAttribute("message", "");
		request.getRequestDispatcher("/WEB-INF/jsp/views/forgotPassword.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddress = request.getParameter("emailAddress");
		DatabaseManager dbm = new DatabaseManager();
		try {
			Integer id = dbm.getUserIdByEmail(emailAddress);
			if(id != null)
			{
				String password = RandomPasswordGenerator.genpass();
				dbm.updatePassword(emailAddress, password);

				String sub = "MavAppoint - Forgot password";
				String message = "The temporary password for your account is:: "+ password+". \nPlease log in to your account to change password!";
				Email newMail = new Email(sub,message,emailAddress);
				newMail.sendMail();
				
				session.setAttribute("message", "Please check your email for further details!");
				request.getRequestDispatcher("/WEB-INF/jsp/views/forgotPassword.jsp").forward(request,response);
			}
			else
			{
				session.setAttribute("message", "No account exists for this email address!");
				request.getRequestDispatcher("/WEB-INF/jsp/views/forgotPassword.jsp").forward(request,response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
