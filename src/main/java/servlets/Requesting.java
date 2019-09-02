package servlets;

import java.io.IOException;
import java.io.InputStream;
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

import com.amazonaws.services.s3.model.ObjectMetadata;

import dao.ReimbursementDAOImpl;
import main.Reimbursement;
import main.User;
import utility.ds.JSON;
import utility.ds.Maps;
import utility.file.S3;

/**
 * Servlet implementation class Requesting
 */
public class Requesting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ReimbursementDAOImpl redao = new ReimbursementDAOImpl();
	
	public Requesting() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// used for postman purposes
		// Know if the user wants a specific request is tied to Employee
		// Know if the user wants to pull out all requests
		Map maps = Maps.getQueryMap(request.getQueryString());
		ArrayList<Reimbursement> reims = new ArrayList<Reimbursement>();
		String n = (String) maps.get("n");
		String type = (String) maps.get("type");

		if (n.equals("0")) {
			reims = redao.getAll(new User(1));
		} else if (n.equals("1")) { // load open requests
			reims = redao.getPending();
		} else if (n.equals("2")) { // load request history
			reims = redao.getAll();
		}

		if (type.equals("j")) {
			////// Send JSON to client as JSON
			JSON.sendJSONtoClient("j", response, reims);		
		} else if (type.equals("m")) {
			////// Send JSON to client as MessagePack
			JSON.sendJSONtoClient("m", response, reims);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		Reimbursement reimb = null; User user = null;
		String objectKey = "";
		HttpSession session = request.getSession();
		JSON json = new JSON();

		if (session != null) {
			
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						json.addKeyToJSON(item.getFieldName(), item.getString());
					} else {
						InputStream content = item.getInputStream();
						metadata.setContentType(item.getContentType());
						metadata.setContentLength(item.getSize());
						objectKey = S3.uploadFile(content, metadata);
					}
				}
				if ( !objectKey.equals("") ) {
					json.addKeyToJSON("status", "PENDING");
					reimb = json.jsonToOneObject(Reimbursement.class);
					reimb.setReceipt(objectKey);
					user = (User) session.getAttribute("user");
					redao.create(reimb, user);
				}
			} catch (FileUploadException e) {
				//throw new ServletException("Parsing file upload failed.", e);
			}
		}

	}
}
