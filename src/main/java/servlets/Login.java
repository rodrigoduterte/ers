package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAOImpl;
import main.User;

/**
 * Servlet implementation class LoginUser
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			request.getRequestDispatcher("html/login.html").forward(request, response);
		} else {
			User user = (User) request.getSession().getAttribute("user");
			if (user.getRole().equals("1")) {
				response.sendRedirect("/ers/home?user=1");
			} else if (user.getRole().equals("2")) {
				response.sendRedirect("/ers/home?user=2");
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		UserDAOImpl udao = new UserDAOImpl();
		String unLogin = request.getParameter("unLogin");
		String pwLogin = request.getParameter("pwLogin");

		try {
			User user = udao.getUser(unLogin); // get user using dao
			HttpSession session = request.getSession(); // get session
			session.setAttribute("user", user); // save user to session

			if (session.getAttribute("user") == null) {
				request.getRequestDispatcher("html/login.html").forward(request, response);
			} else {
				System.out.println(user);
				if (user.getPassword().equals(pwLogin)) {
					if (user.getRole().equals("EMPLOYEE")) {
						response.sendRedirect(
								"/ers/home?user=1&fname=" + user.getFirstName() + "&lname=" + user.getLastName());
					} else if (user.getRole().equals("FINANCE MANAGER")) {
						response.sendRedirect(
								"/ers/home?user=2&fname=" + user.getFirstName() + "&lname=" + user.getLastName());
					}
				} else {
					request.getRequestDispatcher("html/login.html").forward(request, response);
				}
			}

		} catch (Exception e) {
			request.getRequestDispatcher("html/login.html").forward(request, response);
		}
	}
}
