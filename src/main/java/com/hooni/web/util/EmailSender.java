package com.hooni.web.util;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class EmailSender 
{

	public EmailSender()
	{
		//Set the host smtp address
	     Properties props = new Properties();
	     props.put("mail.smtp.host", "thomas");
	     props.put("mail.smtp.port", "25");
	     
	     Authenticator authenticator = new Authenticator();
	     props.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		 props.setProperty("mail.smtp.auth", "true");
	     
	     // create some properties and get the default Session
	     _mailSession = Session.getInstance(props, authenticator);
	     _mailSession.setDebug(false);
	}
	
	public static void main(String[] argv) throws MessagingException, IOException
	{
		EmailSender esender = new EmailSender();
		esender.sendMsg("thomascai2001@gmail.com", "nobody@hooni.org", "Greeting subject", "Hi, dear, where do you up to?");
	}
	public void sendMsg(String to, String from, String subject, String message) throws MessagingException, IOException
	{
		sendMsg(new String[] { to }, from, subject, message);
	}
	
	public void sendMsg(String recipients[], String from, String subject, String message)
	throws MessagingException, IOException
	{
		Message msg = new MimeMessage(_mailSession);
		
		InternetAddress addressFrom = new InternetAddress(from);
	    msg.setFrom(addressFrom);
	    
	    InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 
	    for (int i = 0; i < recipients.length; i++)
	    {
	    	addressTo[i] = new InternetAddress(recipients[i]);
	    }

	    
	   // addressTo.add(new InternetAddress(to));
	    
	    msg.setRecipients(Message.RecipientType.TO, addressTo);
	    
	    msg.setHeader("headerName", "Greeting...");
	    
	    msg.setSubject(subject);
	    
	    msg.setDataHandler(new DataHandler(new ByteArrayDataSource(message, "text/html; charset=UTF-8")));
	   // msg.setContent(message, "text/html; charset=UTF-8");
	    
	    Transport.send(msg);
		
	}
	
	public Session getSession()
	{
		return _mailSession;
	}
	
	public static boolean isValidEmail(String email)
	{
		return Pattern.matches(EMAIL_EXP, email)?true:false;
	}
	
	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator() {
			String username = "thomas@hooni.org";
			String password = "yuanxin001";
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
	
	private Session _mailSession;
	private final static String EMAIL_EXP = "[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
}
