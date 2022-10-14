package com.sevenorcas.blue.system.conf;

/**
* Entity and Field configuration Interface
* * 
* Create 14.10.2022
* [Licence]
* @author John Stewart
*/

public interface ConfigurationI {

	final static public int NULLABLE = 0;
	final static public int NON_NULL = 1;
	
	final static public int NON_UNIQUE = 0;
	final static public int ORG_UNIQUE = 1;
	final static public int DB_UNIQUE = 2;
}
