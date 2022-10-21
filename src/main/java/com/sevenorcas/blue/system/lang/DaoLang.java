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

import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.lang.ent.DtoLabel;
import com.sevenorcas.blue.system.lang.ent.DtoLang;
import com.sevenorcas.blue.system.lang.ent.EntLangKey;
import com.sevenorcas.blue.system.lang.ent.EntLangLabel;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlResultSet;

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
public class DaoLang extends BaseTransfer {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
		
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	public List<DtoLang> languages(SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		
		String sql;
		sql = "SELECT l.id, l.org_nr, l.code, l.descr " +
				"FROM cntrl.lang AS l ";
		
		if (parms.isActiveOnly()) {
			sql += "WHERE l.active = TRUE"; 	
		}

		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<DtoLang> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			DtoLang d = new DtoLang();
			list.add(d);
		
			d.setId(r.getLong(i, "id"))
			 .setOrgNr(r.getInteger(i, "org_nr"))
			 .setCode(r.getString(i, "code"))
			 .setDescr(r.getString(i, "descr"))
			 .setDefaultValue(appProperties.get("LanguageDefault").equals(r.getString(i, "code")));
		}
		return list;
    }

	
	public List<DtoLabel> langPackage(
			Integer orgNr,
    		String pack,
    		String lang,
    		Boolean loadAll,
    		SqlParm parms) throws Exception {
		
		parms = validateParms(parms);
		parms.addParameter(lang);
		
		String sql;
		sql = "SELECT k.id AS langkey_id, l.id, l.org_nr, l.lang, k.code AS code, l.code AS label %1 " +
				"FROM cntrl.lang_key AS k " + 
				"LEFT JOIN cntrl.lang_label AS l ON (k.id = l.lang_key_id AND l.lang = ?) " +
				"%2";
		
		//Load default labels as well
		String dlang = appProperties.get("LanguageDefault");
		if (!dlang.equals(lang)) {
			parms.addParameter(dlang);
			sql = sql.replace("%1", ", x.code as dcode");
			sql = sql.replace("%2", "LEFT JOIN cntrl.lang_label AS x ON (k.id = x.lang_key_id AND x.lang = ?) ");
		}
		else {
			sql = sql.replace("%1", "");
			sql = sql.replace("%2", "");	
		}
		
		if (!loadAll) {
			sql += "WHERE " + (orgNr == 0? "l.org_nr = 0" : "(l.org_nr = 0 OR l.org_nr = " + orgNr + ")");
		}
		
		//Filter by language pack
		if (pack != null && pack.length() > 0) {
			parms.addParameter("%" + pack + "%");
			sql += (!loadAll?"AND":"WHERE") + " k.pack LIKE ? ";
		}
				
		sql += "ORDER BY k.code, l.org_nr " + (!loadAll?"DESC":"");

		SqlResultSet r = SqlExecute.executeQuery(parms, sql, log);
		List<DtoLabel> list = new ArrayList<>();
		Set<String> set = new HashSet<String> ();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			
			//String code = (String)row[4];
			String code = r.getString(i, "code");

			if (loadAll || !set.contains(code)) {
				
				DtoLabel d = new DtoLabel();
				list.add(d);

				d.setLangKeyId(r.getLong(i, "langkey_id"))
				 .setId(r.get(i, "id", -1L))
				 .setOrgNr(r.get(i, "org_nr", -1))
				 .setLang(r.getString(i, "lang"))
				 .setCode(code);
				
				String label = r.getString(i, "label"); 
				d.setLabel(!dlang.equals(lang) && label == null? r.getString(i, "dcode") : label);

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
    public EntLangKey save (EntLangKey entity) throws Exception{
    	em.merge(entity);
    	return entity;
    }
	
    /**
     * Return the <code>LangKeyEnt</code> entity 
     * @param parms
     * @param langKey
     * @return
     */
    public EntLangKey getLangKey (
    		String langKey) throws Exception {
    	TypedQuery<EntLangKey> tq = em.createQuery(
				"FROM " + EntLangKey.class.getCanonicalName() + " "
				+ "WHERE code = :langKey", 
				EntLangKey.class);
		return tq.setParameter("langKey", langKey)
				.getSingleResult();
	}
    
    
    
	/**
	 * Return a list of <code>LangLabelEnt</code> entities
	 * A language label may exist for mulitple orgs
	 * 
	 * @param langKeyId
	 * @param lang
	 * @return
	 */
	public List<EntLangLabel> getLangLabel (Long langKeyId, String lang) {
		TypedQuery<EntLangLabel> tq = em.createQuery(
				"FROM " + EntLangLabel.class.getCanonicalName() + " "
				+ "WHERE lang_key_id = :langKeyId "
				+ "AND lang = :lang", 
				EntLangLabel.class);
		return tq.setParameter("langKeyId", langKeyId)
				.setParameter("lang", lang)
				.getResultList();
	
	}

    
    
}
