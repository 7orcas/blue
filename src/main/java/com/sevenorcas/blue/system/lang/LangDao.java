package com.sevenorcas.blue.system.lang;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.lang.ent.LabelDto;
import com.sevenorcas.blue.system.lang.ent.LangKeyEnt;
import com.sevenorcas.blue.system.lang.ent.LangLabelEnt;
import com.sevenorcas.blue.system.sql.SqlExecute;
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

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
		
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	public List<LangDto> languages(SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql;
		sql = "SELECT l.id, l.org, l.code, l.descr " +
				"FROM cntrl.lang AS l ";
		
		if (parms.isActiveOnly()) {
			sql += "WHERE l.active = TRUE"; 	
		}

		List<Object[]> r = SqlExecute.executeQuery(parms, sql, log);
		List<LangDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			LangDto d = new LangDto();
			list.add(d);
			Object[] row = r.get(i);
						
			d.setId((Long)row[0])
			 .setOrgNr((Integer)row[1])
			 .setCode((String)row[2])
			 .setDescr((String)row[3])
			 .setDefaultValue(appProperties.get("LanguageDefault").equals((String)row[2]));
		}
		return list;
    }

	
	public List<LabelDto> langPackage(
			Integer org,
    		String pack,
    		String lang,
    		Boolean loadAll,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		parms.addParameter(lang);
		
		String sql;
		sql = "SELECT l.id, l.org, l.lang, k.code AS code, l.code AS label %1 " +
				"FROM cntrl.lang_key AS k " + 
				"LEFT JOIN cntrl.lang_label AS l ON (k.id = l.id_lang_key AND l.lang = ?) " +
				"%2";
		
		//Load default labels as well
		String dlang = appProperties.get("LanguageDefault");
		if (!dlang.equals(lang)) {
			parms.addParameter(dlang);
			sql = sql.replace("%1", ", x.code as dcode");
			sql = sql.replace("%2", "LEFT JOIN cntrl.lang_label AS x ON (k.id = x.id_lang_key AND x.lang = ?) ");
		}
		else {
			sql = sql.replace("%1", "");
			sql = sql.replace("%2", "");	
		}
		
		if (!loadAll) {
			sql += "WHERE " + (org == 0? "l.org = 0" : "(l.org = 0 OR l.org = " + org + ")");
		}
		
		//Filter by language pack
		if (pack != null && pack.length() > 0) {
			parms.addParameter("%" + pack + "%");
			sql += (!loadAll?"AND":"WHERE") + " k.pack LIKE ? ";
		}
				
		sql += "ORDER BY k.code, l.org " + (!loadAll?"DESC":"");

		List<Object[]> r = SqlExecute.executeQuery(parms, sql, log);
		List<LabelDto> list = new ArrayList<>();
		Set<String> set = new HashSet<String> ();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			
			Object[] row = r.get(i);
			String code = (String)row[3];

			if (loadAll || !set.contains(code)) {
				
				LabelDto d = new LabelDto();
				list.add(d);
	
				d.setId(row[0] != null? (Long)row[0] : -1L)
				 .setOrgNr(row[1] != null? (Integer)row[1] : -1)
				 .setLang((String)row[2])
				 .setCode(code)
				 .setLabel(!dlang.equals(lang) && (String)row[4] == null? (String)row[5] : (String)row[4])
				 ;

				if (!loadAll) set.add(code);
			}
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
	
    /**
     * Return the <code>LangKeyEnt</code> entity 
     * @param parms
     * @param langKey
     * @return
     */
    public LangKeyEnt getLangKey (
    		String langKey) throws Exception {
    	TypedQuery<LangKeyEnt> tq = em.createQuery(
				"FROM com.sevenorcas.blue.system.lang.LangKeyEnt "
				+ "WHERE code = :langKey", 
				LangKeyEnt.class);
		return tq.setParameter("langKey", langKey)
				.getSingleResult();
	}
    
    
    
	/**
	 * Return a list of <code>LangLabelEnt</code> entities
	 * A language label may exist for mulitple orgs
	 * 
	 * @param idLangKey
	 * @param lang
	 * @return
	 */
	public List<LangLabelEnt> getLangLabel (Long idLangKey, String lang) {
		TypedQuery<LangLabelEnt> tq = em.createQuery(
				"FROM com.sevenorcas.blue.system.lang.LangLabelEnt "
				+ "WHERE id_lang_key = :idLangKey "
				+ "AND lang = :lang", 
				LangLabelEnt.class);
		return tq.setParameter("idLangKey", idLangKey)
				.setParameter("lang", lang)
				.getResultList();
	
	}

    
    
}
