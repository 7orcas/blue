package com.sevenorcas.blue.system.lang;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lifecycle.CallObject;
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

	private AppProperties appProperties = AppProperties.getInstance();
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
		
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	public List<LangDto> languages(
    		CallObject callObj,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql;
		sql = "SELECT l.id, l.org, l.code, l.descr " +
				"FROM cntrl.lang AS l ";
		
		if (parms.isActiveOnly()) {
			sql += "WHERE l.active = TRUE"; 	
		}

		List<Object[]> r = Sql.executeQuery(callObj, parms, sql, log);
		List<LangDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			LangDto d = new LangDto();
			list.add(d);
			Object[] row = r.get(i);
						
			d.setId((Long)row[0])
			 .setOrg((Integer)row[1])
			 .setCode((String)row[2])
			 .setDescr((String)row[3])
			 .setDefaultValue(appProperties.get("LanguageDefault").equals((String)row[2]));
		}
		return list;
    }

	
	public List<LabelDto> langPackage(
    		CallObject callObj,
    		SqlParm parms,
    		String pack,
    		String lang) throws Exception {
		
		parms = validateParms(parms);
		
		String sql;
		sql = "SELECT l.id, l.org, k.code AS code, l.code AS label %1 " +
				"FROM cntrl.lang_key AS k " + 
				"LEFT JOIN cntrl.lang_label AS l ON (k.id = l.id_lang_key AND l.lang = '" + lang + "') " +
				"%2";
		
		//Filter by language pack
		if (pack != null && pack.length() > 0) {
			sql += "WHERE k.pack LIKE '%" + pack + "%' ";
		}
		
		//Load default labels as well
		String dlang = appProperties.get("LanguageDefault");
		if (!dlang.equals(lang)) {
			sql = sql.replace("%1", ", x.code as dcode");
			sql = sql.replace("%2", "LEFT JOIN cntrl.lang_label AS x ON (k.id = x.id_lang_key AND x.lang = '" + dlang + "') ");
		}
		else {
			sql = sql.replace("%1", "");
			sql = sql.replace("%2", "");	
		}
		
		sql += "ORDER BY k.code ";

		List<Object[]> r = Sql.executeQuery(callObj, parms, sql, log);
		List<LabelDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			LabelDto d = new LabelDto();
			list.add(d);
			Object[] row = r.get(i);

			d.setId(row[0] != null? (Long)row[0] : -1L)
			 .setOrg(row[1] != null? (Integer)row[1] : -1)
			 .setCode((String)row[2])
			 .setLabel(!dlang.equals(lang) && (String)row[3] == null? (String)row[4] : (String)row[3])
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
