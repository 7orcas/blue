package com.sevenorcas.blue.system.field;

/**
 * Utility Class to encode multiple fields into a string for saving to a single database column.
 * The encoded field can then be decoded and used to populate the fields.
 * 
 * The purpose is to avoid adding multiple database columns that aren't used in sql statements.
 * It also avoids database updates when adding a field.
 * 
 * For example a true/false configuration field that is only used in code can be encoded.
 * 
 * The encodeFlag can be stored in an adjacent field to indicate if special data is encoded.
 * It contains the following values:
 *  0 = no special value
 *  1 = contains a foreign key
 *   
 * 
 * Created 20.05.22
 * [Licence]
 * @author John Stewart
 */

import java.util.Enumeration;
import java.util.Hashtable;

import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;

public class Encode implements IntHardCodeLangKey{

	static private String REF_SCHEMA = "__FKS";
	static private String REF_TABLE = "__FKT";
	static private String REF_FIELD = "__FKF";
	
	private Hashtable<String, Object> hash;
	private int encodeFlag = 0;
	
	public Encode () {
		hash = new Hashtable<>();
	}
	
	/**
	 * Add an object to be encoded
	 * @param key
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public Encode add(String key, Object o) throws Exception {
		if (hash.containsKey(key)) 
			throw new RedException(LK_UNKNOWN_ERROR, "Duplicate key passed to Encode");
		hash.put(key, o);
		return this;
	}
	
	/**
	 * Add / Update an object to be encoded
	 * @param key
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Encode update(String key, Object o) throws Exception {
		hash.put(key, o);
		return this;
	}
	
	/**
	 * Add a foreign key object to be encoded
	 * @param ForeignKeyField object
	 * @return
	 * @throws Exception
	 */
	public Encode addForeignKey(ForeignKeyField f) throws Exception {
		if (hash.containsKey(f.getField())) 
			throw new RedException("errunk", "Duplicate key passed to Encode");
		
		hash.put(f.getField(), f.getValue());
		hash.put(f.getField() + REF_SCHEMA, f.getSchema());
		hash.put(f.getField() + REF_TABLE, f.getTable());
		hash.put(f.getField() + REF_FIELD, f.getColumn());

		encodeFlag = 1;
		return this;
	}
	
	
	/**
	 * Encode this object into a string
	 * @return
	 * @throws Exception
	 */
	public String encode() throws Exception {
		StringBuffer b = new StringBuffer();
		
		Enumeration<String> keys = hash.keys();
		while (keys.hasMoreElements()) {
			if (b.length() > 0) {
				b.append(";");
			}
			String key = keys.nextElement();
			b.append(key + "=" + EncodeField.encode(hash.get(key)));
		}
		
		//Insert version and field length
		return "V0001-" + padLeftZeros("" + (b.length() + 13), 6) + ";" + b.toString();
	}
	
	/**
	 * Decode this object from a previously encoded string
	 * @param string to decode
	 * @throws Exception
	 */
	public Encode decode (String s) throws Exception{
		if (s == null) return this;
		
		String [] fields = s.split(";");
		
		long l = Long.parseLong(fields[0].substring(6));
		if (l != s.length()) {
			throw new RedException("errunk", "Invalid ?");
		}
		
		for (int i=1; i<fields.length; i++) {
			String [] field = fields[i].split("=");
			hash.put(field[0], EncodeField.decode(field[1]));
		}
		
		return this;
	}
	
	/**
	 * Return 1 if this object contains a foreign key within the encoding.
	 * Otherwise return 0 
	 * @return
	 */
	public int encodeFlag() {
		return encodeFlag;
	}
	
	/**
	 * Set this object's encode flag
	 * @param flag
	 * @return
	 */
	public Encode setEncodeFlag(int flag) {
		encodeFlag = flag;
		return this;
	}
	
	/**
	 * Return the value of a field passed into this object
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return hash.get(key);
	}
	
	/**
	 * Return the value of a field passed into this object
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		return (Integer)get(key);
	}
	
	/**
	 * Return the ForeignKeyField for the passed in key
	 * @param key
	 * @return
	 */
	public ForeignKeyField getForeignKey(String key) {
		ForeignKeyField f = new ForeignKeyField()
				.field(key)
				.value(hash.get(key))
				.schema((String)hash.get(key + REF_SCHEMA))
				.table((String)hash.get(key + REF_TABLE))
				.column((String)hash.get(key + REF_FIELD));
		return f;
	}
	
	
	/*
	 * Thanks to https://www.baeldung.com/java-pad-string
	 */
	private String padLeftZeros(String inputString, int length) {
	    if (inputString.length() >= length) {
	        return inputString;
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append('0');
	    }
	    sb.append(inputString);

	    return sb.toString();
	}
	
}
