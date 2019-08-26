package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user;
		Map maps = Maps.getQueryMap(request.getQueryString());
		HttpSession session = request.getSession();
		String n = (String) maps.get("n");
		ArrayList<Reimbursement> reims = new ArrayList<Reimbursement>();
		
		ObjectMapper mapper = new ObjectMapper( new MessagePackFactory());
		byte[] messagePackBytes = null;
		
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
			
			try {
				OutputStream out = response.getOutputStream();
				response.setContentType("text/plain");
				messagePackBytes = mapper.writeValueAsBytes(reims);
				out.write(messagePackBytes);
				out.flush();
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		}
	}
}
