package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import main.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public User getUser(int id) {
		/**
		 * Returns the user with the specified id #
		 */
		try {
			User user = null;
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimb_user FULL OUTER JOIN reimb_user_roles ON reimb_user.user_role_id=reimb_user_roles.user_role_id WHERE user_id=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				user = new User(res.getInt("user_id"), res.getString("user_username"), res.getString("user_password"),
						res.getString("user_first_name"), res.getString("user_last_name"), res.getString("user_email"),
						res.getString("user_role"));
			}
			prep.cancel();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User getUser(String username) {
		/**
		 * Returns the user with the specified id #
		 */
		try {
			User user = null;
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimb_user FULL OUTER JOIN reimb_user_roles ON reimb_user.user_role_id=reimb_user_roles.user_role_id WHERE user_username=?");
			prep.setString(1, username);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				user = new User(res.getInt("user_id"), res.getString("user_username"), res.getString("user_password"),
						res.getString("user_first_name"), res.getString("user_last_name"), res.getString("user_email"),
						res.getString("user_role"));
			}
			prep.cancel();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(User user) {
		/**
		 * Updates a user in the database
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement("UPDATE reimb_user SET user_username=?,user_password=?,user_first_name=?,user_last_name=?,user_email=? WHERE user_id=?");
			prep.setInt(6, user.getId());
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getFirstName());
			prep.setString(4, user.getLastName());
			prep.setString(5, user.getEmail());
			prep.executeUpdate();
			prep.cancel();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void create(User user) {
		/**
		 * Adds a new user to the database
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement("INSERT INTO reimb_user VALUES (0,?,?,?,?,?,?)");
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getFirstName());
			prep.setString(4, user.getLastName());
			prep.setString(5, user.getEmail());
			switch (user.getRole()) {
			case "EMPLOYEE":
				prep.setInt(6, 1);
				break;
			case "FINANCE MANAGER":
				prep.setInt(6, 2);
				break;
			}
			prep.executeUpdate();
			prep.cancel();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int retrieve() {
		/**
		 * Gets the ID # of the most recently created user
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement("SELECT MAX(user_id) FROM reimb_user");
			ResultSet res = prep.executeQuery();
			prep.cancel();
			while (res.next()) {
				return res.getInt("MAX(user_id)");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public boolean exists(int id) {
		/**
		 * Returns whether or not a user with the specified id # exists
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimb_user FULL OUTER JOIN reimb_user_roles ON reimb_user.user_roll_id=reimb_user_roles.user_roll_id WHERE user_id=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				return true;
			}
			prep.cancel();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean exists(String username) {
		/**
		 * Returns whether or not a user with the specified id # exists
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimb_user FULL OUTER JOIN reimb_user_roles ON reimb_user.user_roll_id=reimb_user_roles.user_roll_id WHERE user_username=?");
			prep.setString(1, username);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				return true;
			}
			prep.cancel();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		UserDAOImpl udao = new UserDAOImpl();
		udao.create(new User("pgolding0","Pris","Golding","pgolding0@npr.org","EMPLOYEE"));
		udao.create(new User("rpettitt1","Rooney","Pettitt","rpettitt1@baidu.com","EMPLOYEE"));
		udao.create(new User("snardi2","Sarajane","Nardi","snardi2@buzzfeed.com","EMPLOYEE"));
		udao.create(new User("hdymock3","Hephzibah","Dymock","hdymock3@redcross.org","EMPLOYEE"));
		udao.create(new User("egerbel4","Elenore","Gerbel","egerbel4@angelfire.com","EMPLOYEE"));
		udao.create(new User("emoran5","Edna","Moran","emoran5@ft.com","FINANCE MANAGER"));
		udao.create(new User("graynor6","Graig","Raynor","graynor6@chron.com","FINANCE MANAGER"));
		udao.create(new User("sarkell7","Sheena","Arkell","sarkell7@instagram.com","EMPLOYEE"));
		udao.create(new User("rspringate8","Rudie","Springate","rspringate8@opensource.org","EMPLOYEE"));
		udao.create(new User("gpatterson9","Gottfried","Patterson","gpatterson9@statcounter.com","EMPLOYEE"));
	}
}
