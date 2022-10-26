package com.sevenorcas.blue.system.mail;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.log.AppLog;

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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("js@7orcas.com"));
			message.setSubject("Exception : " + ex.getMessage());
			
			BaseException x = null;
			if (ex instanceof BaseException) {
				x = (BaseException)ex;
				x.notifyMe(true);
				ex = x.getOriginalException();
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);

			message.setText((x != null? x.getDetail() + ": ": "") + sw.toString());
	 
			Transport.send(message);
	    } catch (MessagingException e) {
	    	AppLog.exception("Cannot send mail", e);
	    }
	 }

	
}
