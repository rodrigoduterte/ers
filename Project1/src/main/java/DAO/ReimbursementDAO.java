package DAO;

import java.util.ArrayList;
import Main.Reimbursement;
import Main.User;

public interface ReimbursementDAO {

	public Reimbursement getReimbursement(int id);

	public void approve(User user, Reimbursement reimb);

	public void deny(User user, Reimbursement reimb);

	public void create(Reimbursement reimb, User user);

	public boolean exists(int id);

	public boolean isPending(int id);

	public ArrayList<Reimbursement> getAll();

	public ArrayList<Reimbursement> getAll(User user);

	public ArrayList<Reimbursement> getPending();

	public int retrieve();

	public void approveAll(User user);

	public void denyAll(User user);
}
