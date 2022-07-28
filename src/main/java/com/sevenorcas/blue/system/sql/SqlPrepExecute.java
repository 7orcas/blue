package com.sevenorcas.blue.system.sql;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* Created July '22
* 
* Helper class to manage a prepared statement JDBC call
* 
* [Licence]
* @author John Stewart
*/
public class SqlPrepExecute {

	static final String DS = "java:jboss/datasources/blueDS";
	
	static public List<Object[]> executeQuery(
    		CallObject callObj,
    		SqlParm sqlParms,
    		String sql,
    		List<Object> parameters,
    		Logger log) throws Exception {
		
		Connection conn = null;
		PreparedStatement stmt = null;
    	ResultSet rs = null;
    	
		List<Object[]> r = new ArrayList<>();
		
		try {
			log.info(sql);    		
			
    		DataSource ds = (DataSource) new InitialContext().lookup(DS);
    		conn = ds.getConnection();
    		stmt = conn.prepareStatement(sql);
    	
    		for (int i=0; i<parameters.size(); i++) {
    			switch (parameters.get(i).getClass().getSimpleName()) {
    			
    				case "String":
    					stmt.setString(i+1, (String)parameters.get(i)); break;
    				case "Integer":
    					stmt.setInt(i+1, (Integer)parameters.get(i)); break;
    				case "Long":
    					stmt.setLong(i+1, (Long)parameters.get(i)); break;
    				case "Double":
    					stmt.setDouble(i+1, (Double)parameters.get(i)); break;
    				case "Float":
    					stmt.setFloat(i+1, (Float)parameters.get(i)); break;
    				case "Boolean":
    					stmt.setBoolean(i+1, (Boolean)parameters.get(i)); break;
    				case "Date":
    					stmt.setDate(i+1, (Date)parameters.get(i)); break;
    				default:
    					throw new RedException("Invalid parameter passed to " + MethodHandles.lookup().lookupClass().getName());
    			}
    		}
    		
    		rs = stmt.executeQuery();
    	
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
    		log.error(x);
    		x.printStackTrace();
    		
    	} finally {
    		if (rs != null) rs.close();
    		if (stmt != null) stmt.close();
    		if (conn != null) conn.close();
    	}
    	
    	return r;
    }
	
}
