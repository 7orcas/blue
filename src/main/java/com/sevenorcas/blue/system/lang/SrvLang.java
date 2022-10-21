package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.excel.ent.ExcelImport;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.ent.DtoLabel;
import com.sevenorcas.blue.system.lang.ent.DtoLang;
import com.sevenorcas.blue.system.lang.ent.EntLangKey;
import com.sevenorcas.blue.system.lang.ent.EntLangLabel;
import com.sevenorcas.blue.system.lang.ent.ExcelLabel;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lang.json.JResLabel;
import com.sevenorcas.blue.system.lang.json.JResLang;
import com.sevenorcas.blue.system.lang.json.JResLangLabel;
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
public class SrvLang extends BaseService {

	@EJB
	private DaoLang dao;
	
	
	public JsonRes languagesJson(
    		CallObject callObj) throws Exception{
		
		List<DtoLang> x = dao.languages(new SqlParm ().setActiveOnly());
		List<JResLang> y = new ArrayList<JResLang>();
		for (DtoLang d : x) {
			y.add(d.toJSon());
		}
		return new JsonRes().setData(y);
    }
	
	/**
	 * Return specific labels for the package, orgNr and language
	 * @param orgnr
	 * @param package
	 * @param language
	 * @param load All flag
	 * @return
	 * @throws Exception
	 */
	public List<DtoLabel> langPackage(
			Integer orgNr,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception {
		
		pack = cleanParam(pack);
		loadFlag = cleanParam(loadFlag);
		
		if (lang == null) {
			throw new RedException ("errunk", "Call must include valid lang");
		}
		
		return dao.langPackage(orgNr, pack, lang, isSameNonNull(loadFlag,"All"), null);
    }
	
	public JsonRes langPackageJson(
    		Integer orgNr,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception{
		
		List<DtoLabel> x = langPackage(orgNr, pack, lang, loadFlag);
		List<JResLabel> y = new ArrayList<JResLabel>();
		for (DtoLabel d : x) {
			y.add(d.toJSon());
		}
		return new JsonRes().setData(y);
    }

	/**
	 * Label utility object
	 * @param orgNr
	 * @param pack
	 * @param lang
	 * @param loadFlag
	 * @return
	 * @throws Exception
	 */
	public UtilLabel getLabelUtil (
			Integer orgNr,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception {
		List<DtoLabel> x = langPackage(orgNr, pack, lang, loadFlag);
		return new UtilLabel(orgNr, lang, listToHashtableCode (x));
	}
	
	/**
	 * Export language package to excel
	 * @param orgNr
	 * @param pack
	 * @param lang
	 * @param loadFlag
	 * @return
	 * @throws Exception
	 */
	public Response langPackageExcel(
    		Integer orgNr,
    		String pack,
    		String lang,
    		String loadFlag) throws Exception {
		
		List<DtoLabel> x = langPackage(orgNr, pack, lang, loadFlag);
		UtilLabel labels = new UtilLabel(orgNr, lang, listToHashtableCode (x));
		ExcelLabel excel = new ExcelLabel(labels, x);
		
		String fn = excelSrv.createListFile("LabelList", orgNr, excel);
		return fileSrv.getFile(fn, "LabelList.xlsx", false);
    }
	
	/**
	 * Persist the label list
	 * @param filename
	 * @throws Exception
	 */
	public JsonRes updateLabels (
			Integer orgNr,
    		String pack,
    		String lang,
    		String loadFlag,
			String filename) throws Exception {

		//Get labels
		List<DtoLabel> list = langPackage(orgNr, pack, lang, loadFlag);
		UtilLabel labels = new UtilLabel(orgNr, lang, listToHashtableCode (list));
		
		//Read in file
		ExcelImport xImport = excelSrv.readListFile(filename, labels);
		ExcelLabel excel = new ExcelLabel(labels, xImport);	
		excel.updateList(list);

		
		if (excel.isChanged()) {
			
			//Create file with invalid comments
			if (excel.isInvalid()) {
				excel = new ExcelLabel(labels, list);
				excel.setIsImportComment(true);
				String fn = excelSrv.createListFile("LabelList-Error", orgNr, excel);
				return new JsonRes()
						.setData(fn)
						.setError("invchanges")
						.setReturnCode(JS_INVALID);
			}
			//Persist updates
			else {
				for (int i=0;i<list.size();i++) {
					DtoLabel dto = list.get(i);
					if (dto.isChanged()) {
						List<EntLangLabel> listX = dao.getLangLabel(dto.getLangKeyId(), dto.getLang());
						
						for (int j=0;j<listX.size();j++) {
							if (isSameNonNull(dto.getId(), listX.get(j).getId())) {
								listX.get(j).setCode(dto.getLabel()); //note code = label in the entity
							}
						}
					}
				}
				return new JsonRes()
						.setData("updated")
						.setReturnCode(JS_UPLOADED);
			}
		}
		
		return new JsonRes()
				.setData("nochanges")
				.setReturnCode(JS_NO_CHANGE);
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
	public List<JResLangLabel> getLabelJson(
    		CallObject callObj,
    		String langKey) throws Exception {
	
		List<EntLangLabel> x = getLabel(callObj, langKey);
		List<JResLangLabel>	y = new ArrayList<>();	
		for (EntLangLabel l : x) {
			y.add(new JResLangLabel(l));
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
	public List<EntLangLabel> getLabel(
    		CallObject callObj,
    		String langKey) throws Exception {
		
		EntLangKey key = dao.getLangKey(langKey);
		List<EntLangLabel> list = dao.getLangLabel(key.getId(), callObj.getLang());
		for (int i=0;i<list.size();i++) {
			EntLangLabel e = list.get(i);
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
			List<JResLangLabel> list) throws Exception {
		
		List<EntLangLabel> listX = dao.getLangLabel(list.get(0).idLangKey, callObj.getLang());
		
		for (int i=0;i<list.size();i++) {
			for (int j=0;j<listX.size();j++) {
				if (isSameNonNull(list.get(i).id, listX.get(j).getId())) {
					listX.get(j).setCode(list.get(i).code);
				}
			}
		}

	}
	
	
}
