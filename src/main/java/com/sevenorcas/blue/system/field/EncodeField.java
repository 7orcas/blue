package com.sevenorcas.blue.system.field;

/**
 * Created 20 May 2022
 * 
 * Utility Class to help the encode class.
 * Encodes and decodes specific objects.
 * 
 * There is no Unit Test for this class as it is covered in the Encode Unit Test. 
 * 
 * [Licence]
 * @author John Stewart
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import com.sevenorcas.blue.system.exception.RedException;

public class EncodeField {

	static private String ENCODE = "##%%";
	
	static protected String encode (Object o) throws Exception {
		if (o == null) throw new RedException("Invalid Object passed to be encoded");
		
		if (o instanceof String) return "S" + encodeString(o.toString());
		if (o instanceof Integer) return "I" + o.toString();
		if (o instanceof Long) return "L" + o.toString();
		if (o instanceof Double) return "D" + o.toString();
		if (o instanceof Float) return "F" + o.toString();
		if (o instanceof LocalDate) {
			LocalDate d = (LocalDate)o;
			return "Z" + d.toString();
		}
		if (o instanceof LocalTime) {
			LocalTime d = (LocalTime)o;
			return "T" + d.toString();
		}
		if (o instanceof Date) {
			Date d = (Date)o;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return "X" + df.format(d);
		}
		
		throw new RedException("Unknown Object passed to be encoded");
	}
	
	static protected Object decode (String s) throws Exception {
		
		Character c = s.charAt(0);
		String x = s.substring(1);
		
		if (c == 'S') return decodeString(x);
		if (c == 'I') return Integer.parseInt(x);
		if (c == 'L') return Long.parseLong(x);
		if (c == 'D') return Double.parseDouble(x);
		if (c == 'F') return Float.parseFloat(x);
		if (c == 'Z') return LocalDate.parse(x);
		if (c == 'T') return LocalTime.parse(x);
		if (c == 'X') {
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return f.parse(x);
		}
		
		throw new RedException("Unknown Object passed to be decoded");
	}
	
	static private String decodeString (String s) {
		s = s.replace(ENCODE + "2C", ",");
		s = s.replace(ENCODE + "3D", "=");
		s = s.replace(ENCODE + "3B", ";");
		s = s.replace(ENCODE + "22", "\"");
		s = s.replace(ENCODE + "27", "'");
		return s;
	}
	
	static private String encodeString (String s) {
		s = s.replace(",", ENCODE + "2C");
		s = s.replace("=", ENCODE + "3D");
		s = s.replace(";", ENCODE + "3B");
		s = s.replace("\"", ENCODE + "22");
		s = s.replace("'", ENCODE + "27");
		return s;
	}
	
}
