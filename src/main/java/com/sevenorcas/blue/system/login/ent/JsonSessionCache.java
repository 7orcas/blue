package com.sevenorcas.blue.system.login.ent;

import java.sql.Timestamp;

/**
 * List of cached http sessions  
 * 
 * Created 22.11.22 
 * [Licence]
 * @author John Stewart
 */
public class JsonSessionCache {
	public Long userId;
	public String username;
	public Integer clientNr;
	public String sessionId;
	public Timestamp created;
	public Timestamp lastActivity;
}
