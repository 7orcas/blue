package com.sevenorcas.blue.system.base;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sevenorcas.blue.system.conf.ConfigurationI;
import com.sevenorcas.blue.system.excel.SrvExcel;
import com.sevenorcas.blue.system.file.SrvFile;
import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.lang.SrvLang;
import com.sevenorcas.blue.system.lifecycle.SrvAroundInvoke;
import com.sevenorcas.blue.system.util.JsonResponseI;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({SrvAroundInvoke.class})
public class BaseSrv extends BaseUtil implements IntHardCodeLangKey, JsonResponseI, ConfigurationI {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	@EJB
	protected SrvExcel excelSrv;

	@EJB
	protected SrvFile fileSrv;

	@EJB
	protected SrvLang langSrv;
	
	public String cleanParam (String s) {
		return s == null? "" : s;
	}
}
