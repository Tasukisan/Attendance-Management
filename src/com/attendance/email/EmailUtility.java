package com.attendance.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtility
{
	private static String fromEmail = "meetingattendance@tartarus.us";
	private static String fromPassword = "UWyNsAL3fPHnWzdBjrZr";
	private static String fromHost = "smtp.dreamhost.com";
	private static String fromPort = "587";

	private static final Logger log = LoggerFactory.getLogger(EmailUtility.class);

	/**
	 * @param username   The user name of the new user.
	 * @param password   The password of the new user.
	 * @param email      The email of the new user.
	 * @param iFirstName The instructors first name.
	 * @param iLastName  The instructors last name.
	 * @param sFirstname The students first name.
	 * @param sLastName  The students last name.
	 * @param courseCode The course code of the assigned course.
	 * @return Returns true if the email was sent.
	 */
	public static boolean SMTPEmail(String username, String password, String email, String iFirstName, String iLastName,
			String sFirstname, String sLastName, String courseCode)
	{
		log.info("Creating SMTPEmail.");

		Properties pro = new Properties();
		pro.put("mail.smtp.host", fromHost);
		pro.put("mail.smtp.port", fromPort);
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.starttls.enable", "true");

		Authenticator auth = new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(fromEmail, fromPassword);
			}
		};
		Session session = Session.getInstance(pro, auth);
		return sendEmail(session, username, password, email, iFirstName, iLastName, sFirstname, sLastName, courseCode);
	}

	/**
	 * @param session    The SMTP session.
	 * @param username   The user name of the new user.
	 * @param password   The password of the new user.
	 * @param email      The email of the new user.
	 * @param iFirstName The instructors first name.
	 * @param iLastName  The instructors last name.
	 * @param sFirstname The students first name.
	 * @param sLastName  The students last name.
	 * @param courseCode The course code of the assigned course.
	 * @return Returns true if the email was sent.
	 */
	private static boolean sendEmail(Session session, String username, String password, String email, String iFirstName,
			String iLastName, String sFirstName, String sLastName, String courseCode)
	{
		try
		{
			log.info("Creating message.");

			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("meetingattendance@tartarus.us", "Meeting Attendance"));
			msg.setReplyTo(InternetAddress.parse("meetingattendance@tartarus.com"));
			msg.setSubject("You have been enrolled in Attendance Management", "UTF-8");
			msg.setText(body(username, password, iFirstName, iLastName, sFirstName, sLastName, courseCode), "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			log.info("Sending email to {}.", email);
			Transport.send(msg);
			log.info("Email sent.");
			return true;
		}
		catch (Exception e)
		{
			log.info("Email failed to send: {}", e.toString());
			e.printStackTrace(System.err);
			return false;
		}

	}

	/**
	 * @param username   The user name of the new user.
	 * @param password   The password of the new user.
	 * @param iFirstName The instructors first name.
	 * @param iLastName  The instructors last name.
	 * @param sFirstname The students first name.
	 * @param sLastName  The students last name.
	 * @param courseCode The course code of the assigned course.
	 */
	private static String body(String username, String password, String iFirstName, String iLastName, String sFirstName,
			String sLastName, String courseCode)
	{
		String body = "Welcome to Attendance Management " + sFirstName + " " + sLastName + "!\n" + "Your instructor "
				+ iFirstName + " " + iLastName + " has enrolled you in " + courseCode + ".\n"
				+ "Your provided username is " + username + " and password is " + password + ".";
		String logBody = "Welcome to Attendance Management " + sFirstName + " " + sLastName + "!\n" + "Your instructor "
				+ iFirstName + " " + iLastName + " has enrolled you in " + courseCode + ".\n"
				+ "Your provided username is " + username + " and password is " + "<Omitted>" + ".";
		log.info("Body content: {}", logBody);
		return body;
	}
}
