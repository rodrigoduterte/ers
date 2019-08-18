package Main;

import org.apache.log4j.Logger;
import DAO.UserDAOImpl;

public class User {
	protected final static Logger ibis = Logger.getLogger(User.class);
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	UserDAOImpl udao = new UserDAOImpl();

	public User(int id, String username, String password, String firstName, String lastName, String email,
			String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	public User(String username, String password, String firstName, String lastName, String email, String role) {
		/**
		 * Used to instantiate a newly registered user and add it to the database
		 */
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role.toUpperCase();
		udao.create(this);
		this.id = udao.retrieve();
		ibis.info("New user registered:\n\tName: " + this.firstName + " " + this.lastName + "\n\tUsername: "
				+ this.username + "\n\tEmail: " + this.email + "\n\tID Number: " + this.id + "\n\tRole: " + this.role);
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public boolean checkPassword(String entry) {
		/**
		 * Tests whether the password matches the entry
		 */
		return password.equals(entry);
	}

	public void changeFirstName(String newName) {
		/**
		 * changes the user's first name
		 */
		firstName = newName;
		udao.save(this);
		ibis.info("ID # " + this.id + "changed their First Name to " + this.firstName);
	}

	public void changeLastName(String newName) {
		/**
		 * changes the user's last name
		 */
		lastName = newName;
		udao.save(this);
		ibis.info("ID # " + this.id + "changed their Last Name to " + this.lastName);
	}

	public void changeUsername(String newName) {
		/**
		 * changes the user's username
		 */
		username = newName;
		udao.save(this);
		ibis.info("ID # " + this.id + "changed their username to " + this.username);
	}

	public void changePassword(String newPassword) {
		/**
		 * changes the user's password
		 */
		password = newPassword;
		udao.save(this);
		ibis.info("ID # " + this.id + "changed their Password to " + this.password);
	}

	public void changeEmail(String newEmail) {
		/**
		 * changes the user's email
		 */
		email = newEmail;
		udao.save(this);
		ibis.info("ID # " + this.id + "changed their Email to " + this.email);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", role=" + role + "]";
	}

	public String getName() {
		/**
		 * Gets the user's full name
		 */
		return (this.firstName + " " + this.lastName);
	}
	
	public void logSignIn() {
		/**
		 * Call to log a user signing in
		 */
		ibis.info(this.getName()+" signed in");
	}
	
	public void logSignOut() {
		/**
		 * Call to log a user signing out
		 */
		ibis.info(this.getName()+" signed out");
	}
}
