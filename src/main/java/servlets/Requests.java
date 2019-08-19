package servlets;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReimbursementDAOImpl;
import utility.ds.*;

/**
 * Servlet implementation class Requests
 */
public class Requests extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReimbursementDAOImpl reidao = new ReimbursementDAOImpl();
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println( request.getQueryString() );
		Map<String,String> open = Maps.getQueryMap( request.getQueryString() );
		if (open.get("open") == "true") {
			// get reimbursements that are not pending
		} else {
			
		}
	}

}
