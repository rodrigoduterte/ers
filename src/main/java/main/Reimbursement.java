package main;

import java.util.Date;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonFormat;

import dao.ReimbursementDAOImpl;

public class Reimbursement {
	protected final static Logger ibis = Logger.getLogger(User.class);
	@JsonFormat(pattern = "MMM dd yyyy")
	private Date created;
	@JsonFormat(pattern = "MMM dd yyyy")
	private Date resolved;
	private int id;
	private double ammount;
	private String description;
	private String receipt;
	private User author;
	private User resolver;
	private String type;
	private String status;
	ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();

	public Reimbursement(int id) {
		this.id = id;
	}

	public Reimbursement(double ammount, String description, String receipt, User author, String type) {
		/**
		 * Used to Instantiate a newly created ticket and add it to the database
		 */
		super();
		this.ammount = ammount;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.type = type.toUpperCase();
		rdao.create(this, author);
		this.id = rdao.retrieve();
		ibis.info("New Reimbursement ticket opened by " + this.author.getName() + "\n\tNumber: " + this.id
				+ "\n\tAmmount: $" + this.ammount + "\n\tType: " + this.type + "\n\tDescription: " + this.description);
	}

	public Reimbursement(Date created, int id, double ammount, String description, String receipt, User author,
			String type, String status) {
		super();
		this.created = created;
		this.id = id;
		this.ammount = ammount;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.type = type;
		this.status = status;
	}

	public Reimbursement(Date created, Date resolved, int id, double ammount, String description, String receipt,
			User author, User resolver, String type, String status) {
		super();
		this.created = created;
		this.resolved = resolved;
		this.id = id;
		this.ammount = ammount;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.type = type;
		this.status = status;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
	public Date getCreated() {
		return created;
	}

	public Date getResolved() {
		return resolved;
	}

	public int getId() {
		return id;
	}

	public double getAmmount() {
		return ammount;
	}

	public String getDescription() {
		return description;
	}

	public String getReceipt() {
		return receipt;
	}

	public User getAuthor() {
		return author;
	}

	public User getResolver() {
		return resolver;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Reimbursement [created=" + created + ", resolved=" + resolved + ", id=" + id + ", ammount=" + ammount
				+ ", description=" + description + ", receipt=" + receipt + ", author=" + author + ", resolver="
				+ resolver + ", type=" + type + ", status=" + status + "]";
	}

}
