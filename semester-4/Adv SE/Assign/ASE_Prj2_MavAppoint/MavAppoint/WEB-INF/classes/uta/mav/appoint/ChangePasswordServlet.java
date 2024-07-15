package uta.mav.appoint;

import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.team3.security.HashPassword;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		try{
			LoginUser user = (LoginUser)session.getAttribute("user");
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
		}
		catch(Exception e){
			header = "templates/header.jsp";
			//System.out.println(e);
		}
		session.setAttribute("message", "");
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/change_password.jsp").forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			session = request.getSession();
			LoginUser user = (LoginUser)session.getAttribute("user");
			DatabaseManager dbm = new DatabaseManager();
			String currentpassword = request.getParameter("currentpassword");
//			String hashedPassword = HashPassword.hashpass(currentpassword);
			String password = request.getParameter("password");
			String repeatpassword = request.getParameter("repeatpassword");
			
			if(password.length()>=8)
			{
				if(user.verifyPassword(currentpassword)){
					if(password.equals(repeatpassword))
					{
						currentpassword = repeatpassword;
						user.setPassword(currentpassword);
						user.setValidated(1);
						dbm.updateUser(user);

						session.setAttribute("user", user);
						session.setAttribute("message", "Password changed!");
						
						session.setAttribute("message", "");
						
						Thread t = new Thread(new Runnable(){
					        public void run(){
					        	final JDialog dialog = new JDialog();
					        	//dialog.setAlwaysOnTop(true);   
					        	Container c = dialog.getParent();
					        	dialog.setLocationRelativeTo(c);
					        	dialog.setModal(true);
					        	dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					        	JOptionPane.showMessageDialog(dialog, "Password changed!");
					        }
					    });
						t.start();
						request.setAttribute("includeHeader", header);
						request.getRequestDispatcher("/WEB-INF/jsp/views/index.jsp").forward(request,response);
					}
					else
					{
						session.setAttribute("message", "Passwords do not match");
						request.setAttribute("includeHeader", header);
						request.getRequestDispatcher("/WEB-INF/jsp/views/change_password.jsp").forward(request,response);
					}
				}
				else{
					session.setAttribute("message", "Password Invalid");
					request.setAttribute("includeHeader", header);
					request.getRequestDispatcher("/WEB-INF/jsp/views/change_password.jsp").forward(request,response);
				}	
			}
			else
			{
				session.setAttribute("message", "Password Must be 8 Characters long");
				request.setAttribute("includeHeader", header);
				request.getRequestDispatcher("/WEB-INF/jsp/views/change_password.jsp").forward(request,response);
			}
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
}