package com.sevenorcas.blue.system.lang;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.app.Label;
import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;

@Stateless
public class LangDao extends BaseDao {

	static final String DS = "java:jboss/datasources/blueDS";
	
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	
	public List<LangData> langPackage(
    		CallObject callObj,
    		String pack,
    		String lang) throws Exception {
		
		String sql;
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
		
		List<LangData> r = new ArrayList<>();
		
		try {
    		DataSource ds = (DataSource) new InitialContext().lookup(DS);
    		conn = ds.getConnection();
    		stmt = conn.createStatement();
    		
    		sql = "SELECT k._code AS key, l._code AS label, l._id, l._org " +
    				"FROM cntrl.lang_key AS k " + 
    				    "LEFT JOIN cntrl.lang_label AS l ON (k._id = l._id_lang_key) " +
    				"WHERE l.lang = '" + lang + "' ";
    	
    		if (pack != null && pack.length() > 0) {
    			sql += "AND k.pack LIKE '%" + pack + "%' ";
    		}
    		
System.out.println(sql);    		
    		rs = stmt.executeQuery(sql);
    		
    		
           // Extract data from result set
           while (rs.next()) {
        	   LangData d = new LangData();
        	   r.add(d);
        	   d.setId(rs.getLong("_id"))
        	    .setOrg(rs.getInt("_org"))
        	    .setKey(rs.getString("key"))
        	    .setLabel(rs.getString("label"))
        	    ;
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
	
	/**
     * Save new / update record
     * @param UserParam object
     * @param LangKey entity
     * @return
     */
    public LangKeyEnt save (LangKeyEnt entity) throws Exception{
    	em.merge(entity);
    	return entity;
    }
	
	
}
