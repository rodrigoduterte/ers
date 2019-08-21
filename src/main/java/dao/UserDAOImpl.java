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
					"SELECT * FROM ERS_USERS FULL OUTER JOIN ERS_USER_ROLES ON ERS_USERS.USER_ROLE_ID = ERS_USER_ROLES.ERS_USER_ROLE_ID WHERE ERS_USERS_ID=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				user = new User(res.getInt("ERS_USERS_ID"), res.getString("ERS_USERNAME"), res.getString("ERS_PASSWORD"),
						res.getString("USER_FIRST_NAME"), res.getString("USER_LAST_NAME"), res.getString("USER_EMAIL"),
						res.getString("USER_ROLE_ID"));
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
					"SELECT * FROM ERS_USERS FULL OUTER JOIN ERS_USER_ROLES ON ERS_USERS.USER_ROLE_ID = ERS_USER_ROLES.ERS_USER_ROLE_ID WHERE ERS_USERNAME=?");
			prep.setString(1, username);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				user = new User(res.getInt("ERS_USERS_ID"), res.getString("ERS_USERNAME"), res.getString("ERS_PASSWORD"),
						res.getString("USER_FIRST_NAME"), res.getString("USER_LAST_NAME"), res.getString("USER_EMAIL"),
						res.getString("USER_ROLE_ID"));
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
		System.out.println(  udao.getUser("pgolding0") );
	}
}
