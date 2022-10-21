package com.sevenorcas.blue.system.lang;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.lang.ent.DtoLabel;
import com.sevenorcas.blue.system.lang.ent.DtoLang;
import com.sevenorcas.blue.system.lang.ent.EntLangKey;
import com.sevenorcas.blue.system.lang.ent.EntLangLabel;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Data access methods to the Language Module interface
* Created July '22
* [Licence]
* @author John Stewart
*/

@Local
public interface TLang {
	public List<DtoLang> languages(SqlParm parms) throws Exception;
	public List<DtoLabel> langPackage(Integer orgNr, String pack, String lang, Boolean loadAll, SqlParm parms) throws Exception;
    public EntLangKey save (EntLangKey entity) throws Exception;
    public EntLangKey getLangKey (String langKey) throws Exception;
	public List<EntLangLabel> getLangLabel (Long langKeyId, String lang);
}
