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
	final static public int DB_UNIQUE  = 2;
	
	final static public int VAL_ERROR_NO_RECORD   = 1;
	final static public int VAL_ERROR_TS_DIFF     = 2;
	final static public int VAL_ERROR_NULL_VALUE  = 3;
	final static public int VAL_ERROR_MIN_VALUE   = 4;
	final static public int VAL_ERROR_MAX_VALUE   = 5;
	
}
