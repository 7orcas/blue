package com.sevenorcas.blue.system.sql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;

/**
* Helper class to manage a JDBC result set
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
public class SqlResultSet implements IntHardCodeLangKey {

	protected Hashtable<String, Integer> columns;
	protected List<Object[]> rows;
	
	public SqlResultSet() {
		columns = new Hashtable<String, Integer>();
		rows = new ArrayList<Object[]>();
	}
	
	private Integer index (String column) {
		return columns.get(column.toLowerCase());
	}
	
	public int size() {
		return rows.size();
	}
		
	@SuppressWarnings("unchecked")
	public <T>T get (Integer row, String column, T dvalue) throws Exception{
		try {
			Object o = getObject(row, column);
			if (o == null) {
				return dvalue;
			}
			return (T)o;
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}

	public Object getObject (Integer row, String column) throws Exception{
		try {
			Integer index = index (column);
			Object o = getObject (row, index);
			return o;
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
	/**
	 * Uses the Java SQL for starting columns at 1
	 */
	public Object getObject (Integer row, Integer index) throws Exception{
		try {
			Object[] o = rows.get(row);
			return o[index-1];
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
	public Long getLong (Integer row, String column) throws Exception{
		try {
			return (Long)getObject(row, column);
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
	
	public Integer getInteger (Integer row, String column) throws Exception{
		try {
			return (Integer)getObject(row, column);
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
	public String getString (Integer row, String column) throws Exception{
		return getString (row, column, null);
	}
	public String getString (Integer row, String column, String dvalue) throws Exception{
		try {
			String v = (String)getObject(row, column);
			return v == null? dvalue : v;
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
	public Boolean getBoolean (Integer row, String column) throws Exception{
		return getBoolean(row, column, null);
	}
	
	public Boolean getBoolean (Integer row, String column, Boolean dvalue) throws Exception{
		try {
			Boolean v = (Boolean)getObject(row, column);
			return v == null? dvalue : v;
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
	public Timestamp getTimestamp (Integer row, String column) throws Exception{
		return getTimestamp(row, column, null);
	}
	
	public Timestamp getTimestamp (Integer row, String column, Timestamp dvalue) throws Exception{
		try {
			Timestamp v = (Timestamp)getObject(row, column);
			return v == null? dvalue : v;
		} catch (Exception e) {
			throw new RedException(LK_UNKNOWN_ERROR, e.getMessage());
		}
		
	}
	
}
