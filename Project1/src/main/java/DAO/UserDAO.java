package DAO;

import Main.User;

public interface UserDAO {
	
	public User getUser(int id);
	
	public User getUser(String username);
	
	public void save(User user);
	
	public void create(User user);
	
	public int retrieve();
	
	public boolean exists(int id);
	
	public boolean exists(String username);
}
