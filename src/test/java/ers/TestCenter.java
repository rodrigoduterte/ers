package ers;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import dao.Info;
import dao.ReimbursementDAOImpl;
import dao.UserDAOImpl;
import main.Reimbursement;
import main.User;

public class TestCenter {
	static UserDAOImpl udao;
	static ReimbursementDAOImpl rdao;
	static User jd;
	static int unum;
	static Reimbursement rmb;
	static int rnum;

	@BeforeClass
	public static void setup() {
		System.out.println("Setting up");
		udao = new UserDAOImpl();
		rdao = new ReimbursementDAOImpl();
		System.out.println("\tDAOs setup");
		jd = new User("jdoe", "Jane", "Doe", "devonjv@gmail.com", "Employee");
		System.out.println("\tUser setup");
		unum = jd.getId();
		System.out.println("\tunum set: " + unum);
		rmb = new Reimbursement(68.42, "junit Reasons", null, jd, "OTHER");
		System.out.println("\tReimbursement setup");
		rnum = rmb.getId();
		System.out.println("\trnum set: " + rnum + "\nSet up complete, running tests\r");
	}

	@Test
	public void getUserIdTest() {
		User u = udao.getUser(unum);
		assertEquals("Checking the user was saved to the database and retrieved by ID", true,
				u.toString().equals(jd.toString()));
	}

	@Test
	public void getUsernameTest() {
		User u = udao.getUser("jdoe");
		assertEquals("Checking the user was saved to the database and retrieved by Username", true,
				u.toString().equals(jd.toString()));
	}

	@Test
	public void checkIdTest() {
		assertEquals("Checking the user ID exists in the database", true, udao.exists(unum));
	}

	@Test
	public void checkUsernameTest() {
		assertEquals("Checking the username exists in the database", true, udao.exists("jdoe"));
	}

	@Test
	public void checkEmailTest() {
		assertEquals("Checking the email exists in the database", true, udao.checkEmail("devonjv@gmail.com"));
	}

	@Test
	public void changeFirstNameTest() {
		jd.changeFirstName("Marry");
		User u = udao.getUser(unum);
		assertEquals("Changes the name, saves it to the database, pulls it out, and checks that it matches", true,
				u.toString().equals(jd.toString()));
	}

	@Test
	public void changeLastNameTest() {
		jd.changeLastName("Sue");
		User u = udao.getUser(unum);
		assertEquals("Changes the name, saves it to the database, pulls it out, and checks that it matches", true,
				u.toString().equals(jd.toString()));
	}

	@Test
	public void changeUsernameTest() {
		jd.changeUsername("V53rn4m3");
		User u = udao.getUser(unum);
		assertEquals("Changes the email, saves it to the database, pulls it out, and checks that it matches", true,
				u.toString().equals(jd.toString()));
	}

	@Test
	public void changeEmailTest() {
		jd.changeEmail("devonjvirden@gmail");
		User u = udao.getUser(unum);
		assertEquals("Changes the email, saves it to the database, pulls it out, and checks that it matches", true,
				u.toString().equals(jd.toString()));
	}

	@Test
	public void changePasswordTest() {
		jd.changePassword("p455W0rd");
		User u = udao.getUser(unum);
		assertEquals(
				"Changes the password, encrypts it, saves it to the database, pulls it out, decrypts it, and checks that it matches",
				true, u.toString().equals(jd.toString()));
	}

	@Test
	public void getReimbursementTest() {
		Reimbursement r = rdao.getReimbursement(rnum);
		System.out.println(r);
		System.out.println(rmb);
		assertEquals("Checks that the Reimbursment was saved to the database and retrieved", true,
				r.toString().toUpperCase().equals(rmb.toString().toUpperCase()));
	}

	@Test
	public void checkReimbursementTest() {
		assertEquals("Checking the reimbursement exists in the database", true, rdao.exists(rnum));
	}

	@Test
	public void getAllTest() {
		assertEquals(
				"Checking that there are reimbursements in the database. Should be at least 1 since rmb is in the system",
				true, rdao.getAll().size() >= 1);
	}

	@Test
	public void getAllPendingTest() {
		assertEquals(
				"Checking that there are pending reimbursements in the database. Should be at least 1 since rmb is in the system",
				true, rdao.getPending().size() >= 1);
	}

	@Test
	public void getAllUserTest() {
		assertEquals(
				"Checking that there are reimbursements in the database tied to a user. Should be 1 since rmb is in the system, and is tied to our test user",
				true, rdao.getAll(jd).size() == 1);
	}

	@Test
	public void isPendingTest() {
		assertEquals("Checking that rmb is pending, should be true", true, rdao.isPending(rnum));
	}

	@AfterClass
	public static void sweep() {
		System.out.println("\rSweeping up");
		try {
			Connection con = DriverManager.getConnection(Info.getUrl(), Info.getUser(), Info.getPass());
			PreparedStatement prepr = con
					.prepareStatement("DELETE FROM reimbursement WHERE reimb_description = 'junit Reasons'");
			prepr.executeUpdate();
			prepr.cancel();
			PreparedStatement prep1 = con.prepareStatement("COMMIT");
			prep1.execute();
			prep1.cancel();
			System.out.println("\tTest reimbursement removed");
			PreparedStatement prepu = con.prepareStatement("DELETE FROM reimb_user WHERE user_id = ?");
			prepu.setInt(1, unum);
			prepu.executeUpdate();
			prepu.cancel();
			System.out.println("\tTest User removed\nTests complete");
			PreparedStatement prep2 = con.prepareStatement("COMMIT");
			prep2.execute();
			prep2.cancel();
			System.out.println("\rCommitted");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
