package utility.ds;

import java.util.HashMap;
import java.util.Map;

public class Maps {
	public static Map<String, String> getQueryMap(String query) {
		String[] params = {query};
		if (query.contains("&")) {
			params = query.split("&");
		} 
		

		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(getQueryMap("n=0&type=m").get("type"));
		System.out.println(getQueryMap("lo=1"));
	}
}
