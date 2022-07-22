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

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.OrgDto;
import com.sevenorcas.blue.system.sql.Sql;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Created July '22
* 
* Data access methods to the Language Module
* TODO Expand Module Description
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class LangDao extends BaseDao {

	static final String DS = "java:jboss/datasources/blueDS";
	
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	
	public List<LangDto> langPackage(
    		CallObject callObj,
    		SqlParm parms,
    		String pack,
    		String lang) throws Exception {
		
		parms = validateParms(parms);
		
		String sql;
		sql = "SELECT l._id, l._org, k._code AS code, l._code AS label " +
				"FROM cntrl.lang_key AS k " + 
				"LEFT JOIN cntrl.lang_label AS l ON (k._id = l._id_lang_key) " +
				"WHERE l.lang = '" + lang + "' ";
		
		if (pack != null && pack.length() > 0) {
			sql += "AND k.pack LIKE '%" + pack + "%' ";
		}

		List<Object[]> r = Sql.executeQuery(callObj, parms, sql);
		List<LangDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			LangDto d = new LangDto();
			list.add(d);
			Object[] row = r.get(i);
			
			d.setId((Long)row[0])
			 .setOrg((Integer)row[1])
			 .setCode((String)row[2])
			 .setLabel((String)row[3])
			 ;
		}
		
    	return list;
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
