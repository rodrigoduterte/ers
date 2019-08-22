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

import org.json.JSONException;
import org.json.JSONObject;

import main.User;


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
		Map<String, String> map = new HashMap<>();
		map.put("fnameReg", user.getFirstName());
		map.put("lnameReg", user.getLastName());
		map.put("emailReg", user.getEmail());
		map.put("unReg", user.getUsername());
		//map.put("pwReg", user.getPassword());
		JSONObject jo = new JSONObject(map);
		
		System.out.println(jo);
		
		// Send json to client to be used for rendering form
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jo);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				sb.append(line);
		} catch (Exception e) {
			/* report an error */ 
		}

		try {
			JSONObject jsonObject = new JSONObject(sb.toString());
			System.out.println(jsonObject.getString("fnameReg"));
			System.out.println(jsonObject.getString("lnameReg"));
			System.out.println(jsonObject.getString("emailReg"));
			System.out.println(jsonObject.getString("unReg"));
			System.out.println(jsonObject.getString("pwReg"));
			System.out.println(jsonObject.getString("cpwReg"));
		} catch (JSONException e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}

	}

}
