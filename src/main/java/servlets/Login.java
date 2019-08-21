package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDAOImpl;

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

//		while (request.getParameterNames().hasMoreElements()) {
//			String param = request.getParameterNames().nextElement().toString();
//			System.out.println(param);
//		}
//
//		System.out.println(request.getReader());
//		System.out.println( request.getParameterValues("unLogin") );
		UserDAOImpl udao = new UserDAOImpl();
		System.out.println(request.getParameter("unLogin"));
		System.out.println(request.getParameter("pwLogin"));
		String unLogin = request.getParameter("unLogin");
		System.out.println( udao.getUser(unLogin) );
		
		
		request.getRequestDispatcher("html/employee.html").forward(request, response);
		
//		response.setStatus(200);
//		response.setContentType("text/xml");
//		PrintWriter writer=response.getWriter();
//		writer.append("this is 200");

		//
//		StringBuffer jb = new StringBuffer();
//		String line = null;
//		try {
//			BufferedReader reader = request.getReader();
//			while ((line = reader.readLine()) != null)
//				jb.append(line);
//		} catch (Exception e) {
//			/* report an error */ }
//
//		try {
//			System.out.println(jb.toString());
//			JSONObject jsonObject = HTTP.toJSONObject(jb.toString());
//			System.out.println(jsonObject.getString("unLogin"));
//			System.out.println(jsonObject.getString("pwLogin"));
//		} catch (JSONException e) {
//			// crash and burn
//			throw new IOException("Error parsing JSON request string");
//		}
	}
}
