package com.sevenorcas.blue.system.lang.json;

import com.sevenorcas.blue.system.base.BaseJsonRes;
import com.sevenorcas.blue.system.lang.ent.EntLangLabel;

/**
* Created 23 August '22
* Language Label Json
* 
* [Licence]
* @author John Stewart
*/

public class JResLangLabel extends BaseJsonRes{
	public Long idLangKey;
	public String langKey;
	
	public JResLangLabel () {
		
	}
	
	/**
	 * JSON string constructor
	 * @param String JSON object
	 */
	public JResLangLabel(String json) throws Exception{

	}
	
	public JResLangLabel (EntLangLabel ent) {
		this.initialise(ent);
		langKey = ent.getLangKey();
		idLangKey = ent.getLangKeyId();	
	}
}
