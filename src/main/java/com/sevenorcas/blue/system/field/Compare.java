package com.sevenorcas.blue.system.field;

/**
 * Created 20 May 2022
 * 
 * Utility Class to compare if the values of 2 objects are the same. 
 * If both are null then the default return is true.
 * Use the isSameButNotNull method to return is false is both are null.
 * 
 * [Licence]
 */

public class Compare {

	static public boolean isSame(Object o1, Object o2) { 
		if (o1 == null && o2 == null) return true;
		if (o1 == null) return false;
		if (o2 == null) return false;
		if (o1.toString().equals(o2.toString())) return true;
		return false;
	}
	
}
