package Main;

import DAO.UserDAOImpl;

public class User {
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
	}

	public void changeLastName(String newName) {
		/**
		 * changes the user's last name
		 */
		lastName = newName;
		udao.save(this);
	}

	public void changeUsername(String newName) {
		/**
		 * changes the user's username
		 */
		username = newName;
		udao.save(this);
	}

	public void changePassword(String newPassword) {
		/**
		 * changes the user's password
		 */
		password = newPassword;
		udao.save(this);
	}

	public void changeEmail(String newEmail) {
		/**
		 * changes the user's email
		 */
		email = newEmail;
		udao.save(this);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", role=" + role + "]";
	}

}
