package com.sevenorcas.blue.system.mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.log.AppLog;
import com.sevenorcas.blue.system.login.ent.ClientSession;

/**
* Mail Module service bean.
*  
* Create 26.10.2022
* [Licence]
* @author John Stewart
*/
@Stateless
public class SMail extends BaseService implements SMailI {

	@Resource(name = "java:jboss/mail/Default")
	private Session session;
	
	@Resource(name = "DefaultManagedExecutorService")
	ManagedExecutorService executor;
	
	/**
	 * Send a message via email 
	 */
	public void send(String addresses, String subject, String text) {
		try {
			final Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
			message.setSubject(subject);
			message.setText(text);
	 
			Transport.send(message);
	    } catch (MessagingException e) {
	    	AppLog.exception("Cannot send mail", e);
	    }
	 }

	/**
	 * E-Mail an exception  
	 */
	public void send(Exception ex) {
		try {
			final Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(appProperties.get("EmailExceptionTo")));
			message.setSubject("Exception : " + ex.getMessage());
			
			BaseException x = null;
			StringBuffer sb = new StringBuffer();
			if (ex instanceof BaseException) {
				x = (BaseException)ex;
				x.emailMe(false);
				ex = x.getOriginalException();
				
				sb.append("Site: " + appProperties.get("Site") + "\n"); 
				sb.append("BaseException Timestamp: " + new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss").format(x.getTimestamp()) + "\n");
				
				if (x.getDetail() != null) {
					sb.append("Detail: " + x.getDetail() + "\n");
				}
				if (x.getCallObject() != null) {
					sb.append(callDetails(x.getCallObject()));	
				}
			}

			message.setText(sb.toString()
					+ "\n"
					+ stackTraceToString(ex));
	 
			executor.execute(new EmailTask(message));
			
	    } catch (Exception e) {
	    	AppLog.exception("Cannot send mail", e);
	    }
	}

	/**
	 * Runnable class to wrap transport in 
	 */
	public class EmailTask implements Runnable {
		private Message message;
		
		public EmailTask (Message m) {
			message = m;
		}
		
        @Override
        public void run() {
        	try {
        		Transport.send(message);
        	} catch (MessagingException e) {
    	    	AppLog.exception("Cannot send mail", e);
    	    }
        }
	}
	
	public String callDetails (CallObject callObj) { 
		ClientSession ses = callObj.getClientSession();
		return "OrgNr: " + ses.getOrgNr() + "\n"
				+ "User: " + ses.getUser() + "\n";
	}
	
	public String stackTraceToString(Exception ex) {
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw);
//		ex.printStackTrace(pw);
		
		ArrayList<String> l = new ArrayList<>();
		
		StackTraceElement[] stack = ex.getStackTrace();
		
		for (int i = 0; stack!=null && i<stack.length && i<10; i++) {
			l.add(stack[i].toString());
		}
		l.add("...");
		for (int i = 10; stack!=null && i<stack.length; i++) {
			String s = stack[i].toString();
			if (s.indexOf(CLASS_PATH_PREFIX) > -1) {
				l.add(s);
			}
		}
		
		
		if (l.size() == 0) return "No Stack Trace";
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < l.size(); i++) {
			sb.append(l.get(i) + "\n");
		}
		sb.append("\nSee log for full stack trace\n");
		
		return sb.toString();
	}
	
}
