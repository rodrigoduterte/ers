package DAO;

public class Info {
	private static String user = "devonjv";
	private static String pass = "dejavir68";
	private static String url = "jdbc:oracle:thin:@devondb.cltxktjezlue.us-east-2.rds.amazonaws.com:1521:ORCL";

	private Info() {
		super();
	}

	public static String getUser() {
		return user;
	}

	public static String getPass() {
		return pass;
	}

	public static String getUrl() {
		return url;
	}
}
