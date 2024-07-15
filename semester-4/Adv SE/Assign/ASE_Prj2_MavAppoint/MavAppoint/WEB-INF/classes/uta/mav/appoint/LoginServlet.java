package uta.mav.appoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();

		session.setAttribute("message", "");
		request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String emailAddress = request.getParameter("emailAddress");
		String password = request.getParameter("password");
		String signIn = request.getParameter("signIn");
		String fP = request.getParameter("forgotPassword");

		if(signIn != null && signIn.equals("Sign in"))
		{
			GetSet sets = new GetSet();
			sets.setEmailAddress(emailAddress);
			sets.setPassword(password);
			System.out.println(sets.getEmailAddress());
			System.out.println(sets.getPassword());
			
			try{
				//call db manager and authenticate user, return value will be 0 or
				//an integer indicating a role
				DatabaseManager dbm = new DatabaseManager();
				LoginUser user = dbm.checkUser(sets);
				if(user != null){
					session.setAttribute("user", user);
					if(user.getValidated().equals(0)){
						response.sendRedirect("changePassword");
					}
					else{
						response.sendRedirect("index");
					}
				}
				else{
					//redirect back to login if authentication fails
					//need to add a "invalid username or password" response
					session.setAttribute("message", "Username or Password Invalid");
					request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request,response);
				}
			}
			catch(Exception e){
				System.out.println(e);
				request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request,response);
			}
		}
		else if(fP != null && fP.equals("Forgot Password"))
		{
			response.sendRedirect("forgotPassword");
		}	
	}
}
