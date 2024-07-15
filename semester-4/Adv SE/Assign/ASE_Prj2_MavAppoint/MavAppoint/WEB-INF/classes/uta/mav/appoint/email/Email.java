package uta.mav.appoint.email;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class Email {
	private String subject;
	private String text;
	private String toEmail;
	
	public Email(String sub, String txt, String destEmail)
	{
		subject = sub;
		text = txt;
		toEmail = destEmail;
	}
	public void sendMail()
	{
		final String user = "email";
        final String pass = "password";
 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
 
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
          });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("email"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
            message.setSubject(subject);
            //message.setText(text);
            message.setContent(text, "text/html");
 
            Transport.send(message);
 
            System.out.println("1. Email Sent");
 
        } 
		catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
}
