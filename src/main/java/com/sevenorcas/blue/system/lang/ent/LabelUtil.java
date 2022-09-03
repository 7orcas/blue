package com.sevenorcas.blue.system.lang.ent;

import java.util.Hashtable;

/**
* Label utility file for use inside java objects 
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public class LabelUtil {

	private Integer orgNr;
	private String lang;
	private Hashtable<String, LabelDto> labels;
	
	public LabelUtil (
			Integer orgNr,
			String lang, 
			Hashtable<String, LabelDto> labels) {
		this.orgNr = orgNr;
		this.lang = lang;
		this.labels = labels;
	}

	
	public Integer getOrgNr() {
		return orgNr;
	}

	public String getLanguage () {
		return lang;
	}
	
	public String getLabel(String langKey) {
		LabelDto l = labels.get(langKey);
		return l != null? l.getLabel() : langKey + "?";
	}
}
