package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import main.Reimbursement;
import main.User;

public class ReimbursementDAOImpl implements ReimbursementDAO {//{
	protected final static Logger ibis = Logger.getLogger(User.class);
	@Override
	public Reimbursement getReimbursement(int id) {
		/**
		 * Returns the ticket with the specified ticket #
		 */
		try {
			UserDAOImpl udao = new UserDAOImpl();
			Reimbursement reimb = null;
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimbursement FULL OUTER JOIN reimb_status ON reimbursement.reimb_status_id=reimb_status.reimb_status_id FULL OUTER JOIN reimb_type ON reimbursement.reimb_type_id=reimb_type.reimb_type_id WHERE reimb_id=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				reimb = new Reimbursement(res.getDate("reimb_submit"), res.getDate("reimb_resolve"),
						res.getInt("reimb_id"), res.getDouble("reimb_ammount"), res.getString("reimb_description"),
						res.getBlob("reimb_receipt"), udao.getUser(res.getInt("reimb_author")),
						udao.getUser(res.getInt("reimb_resolver")), res.getString("reimb_type"),
						res.getString("reimb_status"));
			}
			prep.cancel();
			return reimb;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void approve(User user, Reimbursement reimb) {
		/**
		 * Approves specified ticket, and labels the specified user as the one who
		 * resolved it
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"UPDATE reimbursement SET reimb_status_id=2, reimb_resolver=?, reimb_resolve=CURRENT_TIMESTAMP WHERE reimb_id=?");
			prep.setInt(2, reimb.getId());
			prep.setInt(1, user.getId());
			prep.executeUpdate();
			prep.cancel();
			ibis.info("Ticket #" + reimb.getId() + " approved by " + user.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deny(User user, Reimbursement reimb) {
		/**
		 * Denies specified ticket, and labels the specified user as the one who
		 * resolved it
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"UPDATE reimbursement SET reimb_status_id=3, reimb_resolver=?, reimb_resolve=CURRENT_TIMESTAMP WHERE reimb_id=?");
			prep.setInt(2, reimb.getId());
			prep.setInt(1, user.getId());
			prep.executeUpdate();
			prep.cancel();
			ibis.info("Ticket #" + reimb.getId() + " denied by " + user.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void create(Reimbursement reimb, User user) {
		/**
		 * Adds a new ticket to the database
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con
					.prepareStatement("INSERT INTO reimbursement VALUES (0,?,CURRENT_TIMESTAMP,null,?,?,?,null,1,?)");
			prep.setDouble(1, reimb.getAmmount());
			prep.setString(2, reimb.getDescription());
			prep.setBlob(3, reimb.getReceipt());
			prep.setInt(4, user.getId());
			switch (reimb.getType()) {
			case "LODGING":
				prep.setInt(5, 1);
				break;
			case "TRAVEL":
				prep.setInt(5, 2);
				break;
			case "FOOD":
				prep.setInt(5, 3);
				break;
			case "OTHER":
				prep.setInt(5, 4);
				break;
			}
			prep.executeUpdate();
			prep.cancel();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean exists(int id) {
		/**
		 * Checks whether the specified ticket number exists
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimbursement FULL OUTER JOIN reimb_status ON reimbursement.reimb_status_id=reimb_status.reimb_status_id FULL OUTER JOIN reimb_type ON reimbursement.reimb_type_id=reimb_type.reimb_type_id WHERE reimb_id=?");
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
	public boolean isPending(int id) {
		/**
		 * Returns whether or not the specified account is pending
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimbursement FULL OUTER JOIN reimb_status ON reimbursement.reimb_status_id=reimb_status.reimb_status_id FULL OUTER JOIN reimb_type ON reimbursement.reimb_type_id=reimb_type.reimb_type_id WHERE reimb_id=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				if (res.getString("reimb_status").equalsIgnoreCase("PENDING")) {
					return true;
				}
			}
			prep.cancel();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Reimbursement> getAll() {
		/**
		 * Gets all tickets
		 */
		try {
			UserDAOImpl udao = new UserDAOImpl();
			ArrayList<Reimbursement> reimb = new ArrayList<Reimbursement>();
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimbursement FULL OUTER JOIN reimb_status ON reimbursement.reimb_status_id=reimb_status.reimb_status_id FULL OUTER JOIN reimb_type ON reimbursement.reimb_type_id=reimb_type.reimb_type_id WHERE reimb_id != 0");
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				reimb.add(new Reimbursement(res.getDate("reimb_submit"), res.getDate("reimb_resolve"),
						res.getInt("reimb_id"), res.getDouble("reimb_ammount"), res.getString("reimb_description"),
						res.getBlob("reimb_receipt"), udao.getUser(res.getInt("reimb_author")),
						udao.getUser(res.getInt("reimb_resolver")), res.getString("reimb_type"),
						res.getString("reimb_status")));
			}
			prep.cancel();
			return reimb;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Reimbursement> getAll(User user) {
		/**
		 * Gets all tickets tied to A specific user
		 */
		try {
			UserDAOImpl udao = new UserDAOImpl();
			ArrayList<Reimbursement> reimb = new ArrayList<Reimbursement>();
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimbursement FULL OUTER JOIN reimb_status ON reimbursement.reimb_status_id=reimb_status.reimb_status_id FULL OUTER JOIN reimb_type ON reimbursement.reimb_type_id=reimb_type.reimb_type_id WHERE reimb_author=?");
			prep.setInt(1, user.getId());
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				reimb.add(new Reimbursement(res.getDate("reimb_submit"), res.getDate("reimb_resolve"),
						res.getInt("reimb_id"), res.getDouble("reimb_ammount"), res.getString("reimb_description"),
						res.getBlob("reimb_receipt"), udao.getUser(res.getInt("reimb_author")),
						udao.getUser(res.getInt("reimb_resolver")), res.getString("reimb_type"),
						res.getString("reimb_status")));
			}
			prep.cancel();
			return reimb;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Reimbursement> getPending() {
		/**
		 * Gets all pending tickets
		 */
		try {
			UserDAOImpl udao = new UserDAOImpl();
			ArrayList<Reimbursement> reimb = new ArrayList<Reimbursement>();
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement(
					"SELECT * FROM reimbursement FULL OUTER JOIN reimb_status ON reimbursement.reimb_status_id=reimb_status.reimb_status_id FULL OUTER JOIN reimb_type ON reimbursement.reimb_type_id=reimb_type.reimb_type_id WHERE reimbursement.reimb_status_id=1");
			ResultSet res = prep.executeQuery();
			while (res.next()) {
				reimb.add(new Reimbursement(res.getDate("reimb_submit"), res.getInt("reimb_id"),
						res.getDouble("reimb_ammount"), res.getString("reimb_description"),
						res.getBlob("reimb_receipt"), udao.getUser(res.getInt("reimb_author")),
						res.getString("reimb_type"), res.getString("reimb_status")));
			}
			prep.cancel();
			return reimb;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<Reimbursement>();
	}

	@Override
	public int retrieve() {
		/**
		 * Gets the ticket number of the most recently created ticket
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prep = con.prepareStatement("SELECT MAX(reimb_id) FROM reimbursement");
			ResultSet res = prep.executeQuery();
			prep.cancel();
			while (res.next()) {
				return res.getInt("MAX(reimb_id)");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void approveAll(User user) {
		/**
		 * Approves all pending tickets
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement call = con.prepareCall("{call approve_all(?)}");
			call.setInt(1, user.getId());
			call.execute();
			call.cancel();
			ibis.info("All pending tickets approved by " + user.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void denyAll(User user) {
		/**
		 * Denies all pending tickets
		 */
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement call = con.prepareCall("{call deny_all(?)}");
			call.setInt(1, user.getId());
			call.execute();
			call.cancel();
			ibis.info("All pending tickets denied by " + user.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
