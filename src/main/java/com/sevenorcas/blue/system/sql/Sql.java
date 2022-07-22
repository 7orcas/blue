package com.sevenorcas.blue.system.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* Created July '22
* 
* Helper class to manage a JDBC call
* 
* [Licence]
* @author John Stewart
*/
public class Sql {

	static final String DS = "java:jboss/datasources/blueDS";
	
	static public List<Object[]> executeQuery(
    		CallObject callObj,
    		SqlParm parms,
    		String sql) throws Exception {
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
		List<Object[]> r = new ArrayList<>();
		
		try {
    		DataSource ds = (DataSource) new InitialContext().lookup(DS);
    		conn = ds.getConnection();
    		stmt = conn.createStatement();
    		
System.out.println(sql);    		
    		rs = stmt.executeQuery(sql);
    	
    		ResultSetMetaData rsmd = rs.getMetaData();
    		
			int columns = rsmd.getColumnCount();
    		
			// Extract data from result set
			while(rs.next()){
				Object[] row = new Object[columns];
				r.add(row);
				for (int i=0; i<columns; i++){
					row[i] = rs.getObject(i+1);
				}
			}
           
    	} catch (Exception x) {
    		x.printStackTrace();
    		
    	} finally {
    		if (rs != null) rs.close();
    		if (stmt != null) stmt.close();
    		if (conn != null) conn.close();
    	}
    	
    	return r;
    }
	
}
