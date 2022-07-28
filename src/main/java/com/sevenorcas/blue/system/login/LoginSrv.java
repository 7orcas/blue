package com.sevenorcas.blue.system.login;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
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
public class LoginSrv extends BaseSrv {

//	@EJB
//	private LoginDao dao;
//	
//	public JsonRes languagesJson(
//    		CallObject callObj) throws Exception{
//		
//		List<LoginDto> x = dao.languages(callObj, null);
//		List<LangJsonRes> y = new ArrayList<LangJsonRes>();
//		for (LoginDto d : x) {
//			y.add(d.toJSon());
//		}
//		return new JsonRes().setData(y);
//    }
//	
//	public JsonRes langPackageJson(
//    		CallObject callObj,
//    		String pack,
//    		String lang) throws Exception{
//		
//		pack = pack==null? "" : pack;
//		if (lang == null) {
//			throw new RedException ("Call must include valid lang");
//		}
//		if (lang.equals("es")) {
//			return new JsonRes().setError("Don't actually have this language pack :-)");
//		}
//		
//		List<LabelDto> x = dao.langPackage(callObj, null, pack, lang);
//		List<LabelJsonRes> y = new ArrayList<LabelJsonRes>();
//		for (LabelDto d : x) {
//			y.add(d.toJSon());
//		}
//		return new JsonRes().setData(y);
//    }
	
}
