package com.sevenorcas.blue.system.sql;

import java.util.ArrayList;
import java.util.List;

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
	protected List<Object> parameters;
	
	public SqlParm addParameter(Object o) {
		if (parameters == null) {
			parameters = new ArrayList<>();	
		}
		parameters.add(o);
		return this;
	}
	
	public boolean isActiveOnly() {
		return activeOnly;
	}
	public SqlParm setActiveOnly() {
		this.activeOnly = true;
		return this;
	}
	
	
}
