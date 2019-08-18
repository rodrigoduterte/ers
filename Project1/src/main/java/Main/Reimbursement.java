package Main;

import java.sql.Blob;
import java.sql.Date;
import org.apache.log4j.Logger;
import DAO.ReimbursementDAOImpl;

public class Reimbursement {
	protected final static Logger ibis = Logger.getLogger(User.class);
	private Date created;
	private Date resolved;
	private int id;
	private double ammount;
	private String description;
	private Blob receipt;
	private User author;
	private User resolver;
	private String type;
	private String status;
	ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();

	public Reimbursement(double ammount, String description, Blob receipt, User author, String type) {
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

	public Reimbursement(Date created, int id, double ammount, String description, Blob receipt, User author,
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

	public Reimbursement(Date created, Date resolved, int id, double ammount, String description, Blob receipt,
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

	public Blob getReceipt() {
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
