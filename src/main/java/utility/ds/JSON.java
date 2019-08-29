package utility.ds;

import java.util.ArrayList;

import org.json.JSONObject;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.ReimbursementDAOImpl;
import main.Reimbursement;
import main.User;

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

}
