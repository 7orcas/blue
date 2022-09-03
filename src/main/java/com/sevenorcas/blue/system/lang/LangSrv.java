package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.excel.ExcelSrv;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.file.FileSrv;
import com.sevenorcas.blue.system.lang.ent.LabelDto;
import com.sevenorcas.blue.system.lang.ent.LabelExcel;
import com.sevenorcas.blue.system.lang.ent.LabelUtil;
import com.sevenorcas.blue.system.lang.ent.LangKeyEnt;
import com.sevenorcas.blue.system.lang.ent.LangLabelEnt;
import com.sevenorcas.blue.system.lang.json.LabelJsonRes;
import com.sevenorcas.blue.system.lang.json.LangJsonRes;
import com.sevenorcas.blue.system.lang.json.LangLabelJson;
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
	
	@EJB
	private ExcelSrv excelSrv;

	@EJB
	private FileSrv fileSrv;

	
	public JsonRes languagesJson(
    		CallObject callObj) throws Exception{
		
		List<LangDto> x = dao.languages(new SqlParm ().setActiveOnly());
		List<LangJsonRes> y = new ArrayList<LangJsonRes>();
		for (LangDto d : x) {
			y.add(d.toJSon());
		}
		return new JsonRes().setData(y);
    }
	
	/**
	 * Return specific labels for the package, orgnr and language
	 * @param orgnr
	 * @param package
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public List<LabelDto> langPackage(
			Integer org,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception {
		
		pack = cleanParam(pack);
		loadFlag = cleanParam(loadFlag);
		
		if (lang == null) {
			throw new RedException ("Call must include valid lang");
		}
		
		return dao.langPackage(org, pack, lang, isSameNonNull(loadFlag,"All"), null);
    }
	
	public JsonRes langPackageJson(
    		Integer org,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception{
		
		List<LabelDto> x = langPackage(org, pack, lang, loadFlag);
		List<LabelJsonRes> y = new ArrayList<LabelJsonRes>();
		for (LabelDto d : x) {
			y.add(d.toJSon());
		}
		return new JsonRes().setData(y);
    }

	/**
	 * Label utility object
	 * @param org
	 * @param pack
	 * @param lang
	 * @param loadFlag
	 * @return
	 * @throws Exception
	 */
	public LabelUtil getLabelUtil (
			Integer org,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception {
		List<LabelDto> x = langPackage(org, pack, lang, loadFlag);
		return new LabelUtil(org, lang, listToHashtableCode (x));
	}
	
	public Response langPackageExcel(
    		Integer org,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception {
		
		List<LabelDto> x = langPackage(org, pack, lang, loadFlag);
		LabelUtil labels = new LabelUtil(org, lang, listToHashtableCode (x));
		LabelExcel excel = new LabelExcel("Labels", labels, x);
		
		String fn = excelSrv.createListFile("LabelList", org, excel);
		return fileSrv.getFile(fn, "LabelList.xlsx", false);
    }
	
	
	/**
	 * Return the complete label entity(s)
	 * A language label may have multiple orgs
	 * 
	 * @param callObj
	 * @param langKey
	 * @return
	 * @throws Exception
	 */
	public List<LangLabelJson> getLabelJson(
    		CallObject callObj,
    		String langKey) throws Exception {
	
		List<LangLabelEnt> x = getLabel(callObj, langKey);
		List<LangLabelJson>	y = new ArrayList<>();	
		for (LangLabelEnt l : x) {
			y.add(new LangLabelJson(l));
		}
		return y;
    }
	
	/**
	 * Return the complete label entity
 	 * A language label may have multiple orgs
 	 * 
	 * @param callObj
	 * @param langKey
	 * @return
	 * @throws Exception
	 */
	public List<LangLabelEnt> getLabel(
    		CallObject callObj,
    		String langKey) throws Exception {
		
		LangKeyEnt key = dao.getLangKey(langKey);
		List<LangLabelEnt> list = dao.getLangLabel(key.getId(), callObj.getLang());
		for (int i=0;i<list.size();i++) {
			LangLabelEnt e = list.get(i);
			e.setLangKey(langKey);
		}
		return list;
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
