package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ReimbursementDAOImpl;
import main.Reimbursement;
import main.User;
import utility.ds.JSON;
import utility.ds.Maps;


/**
 * Servlet implementation class Requesting
 */
public class Requesting extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Requesting() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// used for postman purposes
		// Know if the user wants a specific request is tied to Employee
		// Know if the user wants to pull out all requests
		ReimbursementDAOImpl redao = new ReimbursementDAOImpl();
		Map maps = Maps.getQueryMap(request.getQueryString());
		ArrayList<Reimbursement> reims = new ArrayList<Reimbursement>();
		String all = (String) maps.get("all");
		String t = (String) maps.get("t");

		ObjectMapper mapper = new ObjectMapper( new MessagePackFactory());
		byte[] messagePackBytes = null;
		
		if (all.equals("0"))	{
			reims = redao.getAll(new User(1));
		} else if (all.equals("1")) {  // load open requests
			reims = redao.getPending();
		} else if (all.equals("2")) {  // load request history
			reims = redao.getAll();
		} 
		
		if (t.equals("j")) {
		////// Convert ArrayList of Reimbursements into JSON Array
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			ObjectMapper om = new ObjectMapper();
			PrintWriter out = response.getWriter();
			
			out.write(om.writeValueAsString(reims));
			out.flush();
		} else if (t.equals("m")) {
			OutputStream out = response.getOutputStream();
			response.setContentType("text/plain");
			response.setHeader("Access-Control-Allow-Origin", "*");
			messagePackBytes = mapper.writeValueAsBytes(reims);
			out.write(messagePackBytes);
			out.flush();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReimbursementDAOImpl redao = new ReimbursementDAOImpl();
		Reimbursement reimb = null;
		User user = null;
		HttpSession session = request.getSession();
		String ajaxUpdateResult = "";
		JSON json = new JSON();

		if (session != null) {
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						ajaxUpdateResult += "Field " + item.getFieldName() + " with value: " + item.getString()
								+ " is successfully read\n\r";
						json.addKeyToJSON(item.getFieldName(), item.getString());
					} else {
						String fileName = item.getName();
						InputStream content = item.getInputStream();
						response.setContentType("text/plain");
						response.setCharacterEncoding("UTF-8");
						// Do whatever with the content InputStream.
						// System.out.println(Streams.asString(content));
						ajaxUpdateResult += "File " + fileName + " is successfully uploaded\n\r";
					}
					System.out.println(ajaxUpdateResult);
				}
				json.addKeyToJSON("status", "PENDING");
				reimb = json.jsonToOneObject(Reimbursement.class);
				user = (User) session.getAttribute("user");
				redao.create(reimb, user);
				
			} catch (FileUploadException e) {
				throw new ServletException("Parsing file upload failed.", e);
			}
		}
		
	}
}
