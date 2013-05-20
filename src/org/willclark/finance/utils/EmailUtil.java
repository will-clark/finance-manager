package org.willclark.finance.utils;

import java.io.IOException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

public class EmailUtil {

	private static Logger log = Logger.getLogger(EmailUtil.class);
	
	private static String host;
	private static String username;
	private static String password;
	private static String from;
	private static int port = 25;
	
	private static boolean debug = false;
	private static boolean secure = false;
	
	static {
		loadDefaults();
	}
	
	
	public static void send(String to, String subject, String body) {
		send(host, port, secure, username, password, to, from, subject, body);
	}

    public static void send(String host, int port, boolean secure, final String username, final String password, String to, String from, String subject, String body) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.debug", Boolean.toString(debug));
        properties.setProperty("mail.smtp.port", Integer.toString(port));

        Session session = null;
        
        if (secure) {
        	properties.setProperty("mail.smtp.starttls.enable", "true");
        	properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));
        	properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        }
        
        if (username == null) {
        	session = Session.getDefaultInstance(properties);
        }
        else {
        	properties.setProperty("mail.smtp.auth", "true");
        	
	        session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
        }

        session.setDebug(debug);
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        }
        catch (MessagingException e) {
            log.error(e);
        }	        
    }
    
    private static void loadDefaults() {
    	try {    		
	    	Properties properties = new Properties();
	    	properties.load(EmailUtil.class.getResourceAsStream("/email.properties"));
	    	
	    	host = properties.getProperty("host");
	    	port = properties.getProperty("port") != null ? Integer.parseInt(properties.getProperty("port")) : port;
	    	secure = properties.getProperty("secure") != null ? Boolean.parseBoolean(properties.getProperty("secure")) : secure;
	    	username = properties.getProperty("username");
	    	password = properties.getProperty("password");
	    	from = properties.getProperty("from");
	    	
	    	debug = properties.getProperty("debug") != null ? Boolean.parseBoolean(properties.getProperty("debug")) : debug;
    	}
    	catch (IOException e) {
    		log.error("Error loading email.properties file", e);
    	}
    }
}
