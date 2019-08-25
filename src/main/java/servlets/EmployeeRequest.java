package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.msgpack.MessagePack;
import org.stringtree.json.JSONWriter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;

import dao.ReimbursementDAOImpl;
import dao.UserDAOImpl;
import main.Reimbursement;
import main.User;
import utility.ds.JSON;
import utility.ds.Maps;
import flexjson.JSONSerializer;
import flexjson.transformer.Transformer;


/**
 * Servlet implementation class EmployeeRequest
 */
public class EmployeeRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Know if the user wants a specific request is tied to Employee
		// Know if the user wants to pull out all requests
		User user;
		HttpSession session = request.getSession();
		ReimbursementDAOImpl redao = new ReimbursementDAOImpl();
		Map maps = Maps.getQueryMap(request.getQueryString());
		ArrayList<Reimbursement> reims = new ArrayList<Reimbursement>();
		String all = (String) maps.get("all");

//		if (session == null) {
			//user = (User) session.getAttribute("user");
			if (all.equals("1")) {  // load open requests
				reims = redao.getPending();
			} else if (all.equals("2")) {  // load request history
				reims = redao.getAll();
			}
			
//			if (user.getRole().equals("EMPLOYEE")) {
//				if (all.equals("0")) {
//					reims = redao.getAll(user);
//				}
//			} else {
//				if (all.equals("1")) {  // load open requests
//					reims = redao.getPending();
//				} else if (all.equals("2")) {  // load request history
//					reims = redao.getAll();
//				}
//			}
//		}

		////// Convert ArrayList of Reimbursements into JSON Array
		response.setContentType("application/json;charset=UTF-8");
		ObjectMapper om = new ObjectMapper();
		PrintWriter out = response.getWriter();
		
		
		out.write(om.writeValueAsString(reims));
		out.flush();
		//out.close();
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
	
	public static void main(String[] args) {
		ReimbursementDAOImpl redao = new ReimbursementDAOImpl();
		ArrayList<Reimbursement> reims = redao.getPending();
		//System.out.println( reims.size() );
		JSONWriter jsow = new JSONWriter();
		System.out.println( jsow.write(reims) );
//		System.out.println( reims.get(0) );
		
		//System.out.println( JsonStream.serialize(Any.wrap(reims.get(0))) );
		//System.out.println(JsonStream.serialize(new int[]{1,2,3}));
	}
}
