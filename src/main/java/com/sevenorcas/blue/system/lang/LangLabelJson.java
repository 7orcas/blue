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
	
	public LangLabelJson () {
		
	}
	
	/**
	 * JSON string constructor
	 * @param String JSON object
	 */
	public LangLabelJson(String json) throws Exception{
System.out.println(json);		
	}
	
	public LangLabelJson (LangLabelEnt ent) {
		this.initialise(ent);
		idLangKey = ent.getIdLangKey();	
	}
}
