package com.sevenorcas.blue.system.sql;

/**
* Created 22 July '22
* 
* Sql helper class to transfer sql parameters
*   
* TODO Expand Module Description
* 
* [Licence]
* @author John Stewart
*/

public class SqlParm {

	protected boolean activeOnly = false;

	public boolean isActiveOnly() {
		return activeOnly;
	}
	public SqlParm setActiveOnly() {
		this.activeOnly = true;
		return this;
	}
	
	
}
