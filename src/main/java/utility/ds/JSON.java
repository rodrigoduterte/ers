package utility.ds;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.Reimbursement;

public class JSON {
	private JSONObject jo = new JSONObject();
	private static Gson gsonBuilder = new GsonBuilder().create();

	public void addKeyToJSON(String key, String value) {
		jo.put(key, value);
	}

	public <T> T jsonToOneObject(Class<T> clas) {
		return gsonBuilder.fromJson(this.jo.toString(), clas);
	}

	public static String fatToflat(String fatjson) {
		String flatjson = JsonFlattener.flatten(fatjson);
		return flatjson;
	}

	public static String flatTofat(String flatjson) {
		String fatjson = JsonUnflattener.unflatten(flatjson);
		return fatjson;
	}

	public static <T> String collectionToFatJson(ArrayList<T> tt) {
		return gsonBuilder.toJson(tt);
	}

	public static <T> T fatJsonToCollection(String fatjson, Class<T> clas) {
		return gsonBuilder.fromJson(fatjson, clas);
	}

	public static <T> String collectionToFlatJson(ArrayList<T> tt) {
		StringBuilder arrayString = new StringBuilder("[");
		for (T t : tt) {
			arrayString.append(fatToflat(gsonBuilder.toJson(t)));
			arrayString.append(",");
		}
		arrayString.deleteCharAt(arrayString.length() - 1);
		arrayString.append("]");
		return arrayString.toString();
	}

	public static <T> T flatJsonToCollection(String flatjson, Class<T> clas) {
		return gsonBuilder.fromJson(flatTofat(flatjson), clas);
	}

	
	public static void sendJSONtoClient(String format, 
			HttpServletResponse response, ArrayList<Reimbursement> reims) throws IOException {
		ObjectMapper om;
		if (format.equals("j")) {
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			om = new ObjectMapper();
			out.write(om.writeValueAsString(reims));
			out.flush();
		} else if (format.equals("m")) {
			response.setContentType("text/plain");
			OutputStream out = response.getOutputStream();
			om = new ObjectMapper(new MessagePackFactory());
			out.write(om.writeValueAsBytes(reims));
			out.flush();
		}
	}
}
