package com.sevenorcas.blue.system.lang;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* Created 23 August '22
* Language Label Json
* 
* [Licence]
* @author John Stewart
*/

public class LangLabelJson extends BaseJsonRes{
	public Long idLangKey;
	public String langkey;
	
	public LangLabelJson () {
		
	}
	
	/**
	 * JSON string constructor
	 * @param String JSON object
	 */
	public LangLabelJson(String json) throws Exception{

	}
	
	public LangLabelJson (LangLabelEnt ent) {
		this.initialise(ent);
		langkey = ent.getLangKey();
		idLangKey = ent.getIdLangKey();	
	}
}
