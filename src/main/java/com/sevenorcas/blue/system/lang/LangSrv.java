package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.CallObject;

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
		
		
		List<LangData> x = dao.langPackage(callObj, pack, lang);
		List<LangJsonRes> y = new ArrayList<LangJsonRes>();
		for (LangData d : x) {
			y.add(d.toJSon());
		}
		
		return y;
    }
	
}
