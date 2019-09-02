package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ReimbursementDAOImpl;
import main.Reimbursement;
import main.User;
import utility.ds.*;

/**
 * Servlet implementation class Requests
 */
public class Requests extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReimbursementDAOImpl rei = new ReimbursementDAOImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Requests() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user;
		Map maps = Maps.getQueryMap(request.getQueryString());
		HttpSession session = request.getSession();
		String n = (String) maps.get("n"); String type = (String) maps.get("type");
		ArrayList<Reimbursement> reims = new ArrayList<Reimbursement>();

		if (session != null) {
			user = (User) session.getAttribute("user");
			if (user.getRole().equals("EMPLOYEE")) {
				if (n.equals("0")) {
					reims = rei.getAll(user);
				}
			} else {
				if (n.equals("1")) {
					reims = rei.getPending();
				} else if (n.equals("2")) {
					reims = rei.getAll();
				}
			}

			if (type.equals("j")) {
				JSON.sendJSONtoClient("j", response, reims);
			} else if (type.equals("m")) {
				JSON.sendJSONtoClient("m", response, reims);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		ObjectMapper om = new ObjectMapper();

		Map maps = Maps.getQueryMap(request.getQueryString());
		String app = (String) maps.get("app");
		String line = null;

		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
		}

		try {
			User user = (User) request.getSession().getAttribute("user");
			Integer[] numbers = om.readValue(jb.toString(), Integer[].class); // get the reimb_id numbers
			for (Integer number : numbers) {
				if (app.equals("0")) {
					rei.deny(user, new Reimbursement(number));
				} else if (app.equals("1")) {
					rei.approve(user, new Reimbursement(number));
				}
			}

		} catch (Exception e) {
		}
	}

}
