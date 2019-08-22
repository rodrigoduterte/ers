package mail;

import main.User;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailMan {
	private static final String host = "smtp.live.com";
	private static final String username = "vndlMessaging@hotmail.com";
	private static final String password = "Vndl_6842";
	private static final String from = "vndlMessaging@hotmail.com";
	private static String to;
	private static final String subject = "Your Temporary Password";
	private static String message;
	private static final String messageBody = "\rThank you for registering with us.\rYour Username is: ";
	private static final String messagePassword = "\rYour Temporary Password is: ";
	private static final String messageTail = "\rPlease use this to sign in and change your password.\r\r(This message was sent automatically, replies will not be seen.)";

	private MailMan() {
		super();
	}

	public static void send(User user) {
		message = ("Hello " + user.getName() + messageBody + user.getUsername() + messagePassword + user.getPassword()
				+ messageTail);
		to = user.getEmail();
		Properties pro = new Properties();
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.starttls.enable", "true");
		pro.put("mail.smtp.host", host);
		Authorizor auth = new Authorizor(username, password);
		Session ses = Session.getInstance(pro, auth);
		Message herm = new MimeMessage(ses);
		try {
			herm.setFrom(new InternetAddress(from));
			herm.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			herm.setSubject(subject);
			herm.setText(message);
			herm.saveChanges();
			Transport bif = ses.getTransport("smtp");
			bif.connect(host, username, password);
			bif.sendMessage(herm, herm.getAllRecipients());
			bif.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
