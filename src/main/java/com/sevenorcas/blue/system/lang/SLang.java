package com.sevenorcas.blue.system.lang;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lang.ent.DtoLabel;
import com.sevenorcas.blue.system.lang.ent.EntLangLabel;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lang.json.JResLangLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
 * Language Service Interface 
 * Created July '22
 * [Licence]
 * @author John Stewart
 */

@Local
public interface SLang extends BaseServiceI {
	public JsonRes languagesJson(CallObject callObj) throws Exception;
	public List<DtoLabel> langPackage(Integer orgNr, String pack, String lang, String loadFlag) throws Exception;
	public JsonRes langPackageJson(Integer orgNr, String pack, String lang, String loadFlag) throws Exception;
	public UtilLabel getLabelUtil (Integer orgNr, String pack, String lang, String loadFlag) throws Exception;
	public Response langPackageExcel(Integer orgNr, String pack, String lang, String loadFlag) throws Exception;
	public JsonRes updateLabels (Integer orgNr, String pack, String lang, String loadFlag, String filename) throws Exception;
	public List<JResLangLabel> getLabelJson(CallObject callObj, String langKey) throws Exception;
	public List<EntLangLabel> getLabel(CallObject callObj, String langKey) throws Exception;
	public void updateLabel (CallObject callObj, List<JResLangLabel> list) throws Exception;
}
