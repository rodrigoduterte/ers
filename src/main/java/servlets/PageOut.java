package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.User;
import utility.ds.Maps;

/**
 * Servlet implementation class PageOut
 */
public class PageOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println(request.getQueryString());
		Map maps = Maps.getQueryMap(request.getQueryString());
		String lo = maps.get("lo").toString();
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control", "no-cache"); // Forces caches to obtain a new copy of the page from the
															// origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward compatibility
		// session.removeAttribute("user");
		if (lo.equals("1")) {
			User user = (User) session.getAttribute("user");
			user.logSignOut();
			session.setAttribute("user", null);
			session.invalidate();
			request.getRequestDispatcher("html/loggedout.html").forward(request, response);
			return;
		} else if (lo.equals("0")) {
			request.getRequestDispatcher("html/registered.html").forward(request, response);
		}
		
		
	}

}
