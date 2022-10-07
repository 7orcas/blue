package com.sevenorcas.blue.system.sql;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.exception.RedException;

/**
* Helper class to manage a normal and prepared statement JDBC call
* 
* Created July '22
* [Licence]
* @author John Stewart
*/
public class SqlExecute {

	static final public String DS = "java:jboss/datasources/blueDS";
	
	static public SqlResultSet executeQuery(
    		SqlParm sqlParms,
    		String sql,
    		Logger log) throws Exception {
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement stmtP = null;
    	ResultSet rs = null;
    	SqlResultSet r = null;
    	
		try {
			log.info(sql);    		
			
    		DataSource ds = (DataSource) new InitialContext().lookup(DS);
    		conn = ds.getConnection();
    		
    		//No parameters so normal sql
    		if (sqlParms.parameters == null) {
    			stmt = conn.createStatement();
    			rs = stmt.executeQuery(sql);
    		}
    		else {
    		    		
	    		stmtP = conn.prepareStatement(sql);
	    	
	    		for (int i=0; i<sqlParms.parameters.size(); i++) {
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
	    		rs = stmtP.executeQuery();
    		}
    		
    		r = new SqlResultSet();
    		ResultSetMetaData rsmd = rs.getMetaData();
    		
			int columns = rsmd.getColumnCount();
			for (int i=1;i<=columns;i++) {
				r.columns.put(rsmd.getColumnName(i).toLowerCase(), i);
			}
    		
			// Extract data from result set
			while(rs.next()){
				Object[] row = new Object[columns];
				r.rows.add(row);
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
    		if (stmtP != null) stmtP.close();
    		if (conn != null) conn.close();
    	}
    	
    	return r;
    }
	
}
