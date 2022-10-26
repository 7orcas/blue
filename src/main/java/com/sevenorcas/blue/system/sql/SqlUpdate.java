package com.sevenorcas.blue.system.sql;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.exception.RedException;

/**
* Helper class to manage a normal and prepared statement JDBC call
* 
* Created 24.10.2022
* [Licence]
* @author John Stewart
*/
public class SqlUpdate {

	static public void executeQuery(String sql) throws Exception {
		executeQuery(null, sql, null);	
	}
	
	static public void executeQuery(
    		SqlParm sqlParms,
    		String sql,
    		Logger log) throws Exception {
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement stmtP = null;
    	
		try {
			if (log != null) log.info(sql);    		
			
    		DataSource ds = (DataSource) new InitialContext().lookup(SqlExecute.DS);
    		conn = ds.getConnection();
    		
    		//No parameters so normal sql
    		if (sqlParms != null && sqlParms.parameters == null) {
    			stmt = conn.createStatement();
    			stmt.executeUpdate(sql);
    		}
    		else {
    		    		
	    		stmtP = conn.prepareStatement(sql);
	    	
	    		for (int i=0; sqlParms!=null && i<sqlParms.parameters.size(); i++) {
	    			switch (sqlParms.parameters.get(i).getClass().getSimpleName()) {
	    			
	    				case "String":
	    					stmtP.setString(i+1, (String)sqlParms.parameters.get(i)); break;
	    				case "Integer":
	    					stmtP.setInt(i+1, (Integer)sqlParms.parameters.get(i)); break;
	    				case "Long":
	    					stmtP.setLong(i+1, (Long)sqlParms.parameters.get(i)); break;
	    				case "Double":
	    					stmtP.setDouble(i+1, (Double)sqlParms.parameters.get(i)); break;
	    				case "Float":
	    					stmtP.setFloat(i+1, (Float)sqlParms.parameters.get(i)); break;
	    				case "Boolean":
	    					stmtP.setBoolean(i+1, (Boolean)sqlParms.parameters.get(i)); break;
	    				case "Date":
	    					stmtP.setDate(i+1, (Date)sqlParms.parameters.get(i)); break;
	    				default:
	    					throw new RedException("errunk", "Invalid parameter passed to " + MethodHandles.lookup().lookupClass().getName());
	    			}
	    		}
	    		stmtP.executeUpdate();
    		}
           
    	} catch (Exception x) {
    		if (log != null) log.error(x);
    		x.printStackTrace();
    		
    	} finally {
    		if (stmt != null) stmt.close();
    		if (stmtP != null) stmtP.close();
    		if (conn != null) conn.close();
    	}
    	
    }
	
}
