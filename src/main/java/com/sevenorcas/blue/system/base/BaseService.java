package com.sevenorcas.blue.system.base;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sevenorcas.blue.system.conf.SConfigI;
import com.sevenorcas.blue.system.conf.SValidateI;
import com.sevenorcas.blue.system.excel.SExcelI;
import com.sevenorcas.blue.system.file.SFileI;
import com.sevenorcas.blue.system.lang.SLangI;
import com.sevenorcas.blue.system.lifecycle.SrvAroundInvoke;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({SrvAroundInvoke.class})
public class BaseService extends BaseUtil implements BaseServiceI {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
	@EJB protected SExcelI excelSrv;
	@EJB protected SFileI fileSrv;
	@EJB protected SLangI langSrv;
	@EJB protected SValidateI validateSrv;
	@EJB protected SConfigI configSrv;
		
	public String cleanParam (String s) {
		return s == null? "" : s;
	}
}
