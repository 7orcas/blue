package com.sevenorcas.blue.system.base;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.excel.SExcel;
import com.sevenorcas.blue.system.file.SFile;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.lang.SLang;
import com.sevenorcas.blue.system.lifecycle.SrvAroundInvoke;
import com.sevenorcas.blue.system.util.JsonResponseI;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({SrvAroundInvoke.class})
public class BaseService extends BaseUtil implements IntHardCodeLangKey, JsonResponseI, ConfigurationI {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	@EJB
	protected SExcel excelSrv;

	@EJB
	protected SFile fileSrv;

	@EJB
	protected SLang langSrv;
	
	public String cleanParam (String s) {
		return s == null? "" : s;
	}
}
