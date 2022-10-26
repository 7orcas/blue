package com.sevenorcas.blue.system.mail;

import javax.ejb.Local;

/**
 * Email Module service interface
 * Create 26.10.2022
 * [Licence]
 * @author John Stewart
 */
@Local
public interface SMailI {
	public void send(String addresses, String subject, String text);
	public void send(Exception ex);
}
