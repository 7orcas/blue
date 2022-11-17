package com.sevenorcas.blue.system.login.ent;

import java.util.List;

/**
* Json Login Response entity 
* Part 2 of login process
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
public class JResLogin2 {
	/** userid                */ public String username;
	/** org number            */ public Integer orgNr;
	/** language code         */ public String lang;
	/** client theme          */ public Integer theme;
	/** Force a PW change     */ public Boolean changePW;
	
	public List<JsonUrlPermission>permissions;
}
