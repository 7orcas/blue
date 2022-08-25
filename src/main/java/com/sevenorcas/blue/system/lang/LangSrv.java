package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;

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
	
	public JsonRes languagesJson(
    		CallObject callObj) throws Exception{
		
		List<LangDto> x = dao.languages(new SqlParm ().setActiveOnly());
		List<LangJsonRes> y = new ArrayList<LangJsonRes>();
		for (LangDto d : x) {
			y.add(d.toJSon());
		}
		return new JsonRes().setData(y);
    }
	
	public JsonRes langPackageJson(
    		Integer org,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception{
		
		pack = cleanParam(pack);
		loadFlag = cleanParam(loadFlag);
		
		if (lang == null) {
			throw new RedException ("Call must include valid lang");
		}
		if (lang.equals("es")) {
			return new JsonRes().setError("Don't actually have this language pack :-)");
		}
		
		List<LabelDto> x = dao.langPackage(org, pack, lang, isSameNonNUll(loadFlag,"All"), null);
		List<LabelJsonRes> y = new ArrayList<LabelJsonRes>();
		for (LabelDto d : x) {
			y.add(d.toJSon());
		}
		return new JsonRes().setData(y);
    }
	
	/**
	 * Return the complete label entity(s)
	 * A language label may have multiple orgs
	 * 
	 * @param callObj
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public List<LangLabelJson> getLabelJson(
    		CallObject callObj,
    		String label) throws Exception {
	
		try{
			List<LangLabelEnt> x = getLabel(callObj, label);
			List<LangLabelJson>	y = new ArrayList<>();	
			for (LangLabelEnt l : x) {
				y.add(new LangLabelJson(l));
			}
			return y;

		} catch (Exception e) {
			return null;
		}
    }
	
	/**
	 * Return the complete label entity
 	 * A language label may have multiple orgs
 	 * 
	 * @param callObj
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public List<LangLabelEnt> getLabel(
    		CallObject callObj,
    		String label) throws Exception {
		
		Long id = dao.getLangKeyId(null, label);
		return dao.getLangLabel(id, callObj.getLang());
    }
	 
	/**
	 * Persist the label list
	 * @param list
	 * @throws Exception
	 */
	public void updateLabel (
			CallObject callObj,
			List<LangLabelJson> list) throws Exception {
		
		List<LangLabelEnt> listX = dao.getLangLabel(list.get(0).idLangKey, callObj.getLang());
		
		for (int i=0;i<list.size();i++) {
			for (int j=0;j<listX.size();j++) {
				if (isSameNonNUll(list.get(i).id, listX.get(j).getId())) {
					listX.get(j).setCode(list.get(i).code);
				}
			}
		}

	}
	
	
}
