package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAOImpl;
import main.User;
import utility.ds.Maps;

/**
 * Servlet implementation class UserInfo
 */
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) { // if user has logged in do this
			Map<String, String> map = new HashMap<>();
			map.put("fnameReg", user.getFirstName());
			map.put("lnameReg", user.getLastName());
			map.put("emailReg", user.getEmail());
			map.put("unReg", user.getUsername());
			JSONObject jo = new JSONObject(map);

			// Send json to client to be used for rendering form
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(jo);
			out.flush();
		} else if (user == null) { // else if user is trying to know if his/her username and email exist
			PrintWriter out = response.getWriter();
			UserDAOImpl userdao = new UserDAOImpl();
			Map maps = Maps.getQueryMap(request.getQueryString());
			boolean valueExists;

			if (maps.get("field").equals("un")) {
				System.out.println("un   " + maps.get("un").toString());
				valueExists = userdao.exists(maps.get("un").toString());
				System.out.println(valueExists);

				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				out.print(valueExists);
				out.flush();
			} else if (maps.get("field").equals("em")) {
				System.out.println("un   " + maps.get("em").toString());
				valueExists = userdao.checkEmail(maps.get("em").toString());
				System.out.println(valueExists);

				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				out.print(valueExists);
				out.flush();
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
		UserDAOImpl userdao = new UserDAOImpl();
		HttpSession session = request.getSession();

		if (session != null) { //
			String data = "";
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			data = builder.toString();
			JSONObject json = new JSONObject(data);

			User user = (User) session.getAttribute("user");
			System.out.println(user);
			System.out.println(json);
			user.changeFirstName(json.getString("fnameReg"));
			user.changeLastName(json.getString("lnameReg"));
			user.changeUsername(json.getString("unReg"));
			user.changeEmail(json.getString("emailReg"));
			user.changePassword(json.getString("pwReg"));
		} else {
			String fn = request.getParameter("fnameReg"), ln = request.getParameter("lnameReg");
			String un = request.getParameter("unReg"), em = request.getParameter("emailReg");
			String pw = request.getParameter("pwReg");

			User user = new User(un, fn, ln, em, "EMPLOYEE"); // must register an employee in the database before
																// sending an email
			userdao.create(user); // save employee to database
			request.getRequestDispatcher("/ers/out").forward(request, response);
		}

	}

}
