package com.sevenorcas.blue.system.login.ent;

/**
* Json Login Response entity 
* Part 2 of login process
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
public class JResLogin2 {
	/** userid                */ public String userid;
	/** org number            */ public Integer orgNr;
	/** language code         */ public String lang;
	/** client theme          */ public Integer theme;
	/** user roles            */ public String roles;
	/** Force a PW change     */ public Boolean changePW;
}
