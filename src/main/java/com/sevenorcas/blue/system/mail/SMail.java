package com.sevenorcas.blue.system.mail;

import java.lang.invoke.MethodHandles;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseService;
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

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@Resource(name = "java:jboss/mail/Default")
	private Session session;
	
	/**
	 * Send a email 
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
    
}
