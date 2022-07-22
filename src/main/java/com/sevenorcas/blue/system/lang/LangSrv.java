package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
* Created July '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class LangSrv extends BaseSrv {

	@EJB
	private LangDao dao;
	
	public List<LangJsonRes> langPackageJson(
    		CallObject callObj,
    		String pack,
    		String lang) throws Exception{
		
		pack = pack==null? "" : pack;
		if (lang == null) {
			throw new RedException ("Call must include valid lang");
		}
		
		
		List<LangDto> x = dao.langPackage(callObj, null, pack, lang);
		List<LangJsonRes> y = new ArrayList<LangJsonRes>();
		for (LangDto d : x) {
			y.add(d.toJSon());
		}
		
		return y;
    }
	
}
