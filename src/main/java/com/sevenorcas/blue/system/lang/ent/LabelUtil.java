package com.sevenorcas.blue.system.lang.ent;

import java.util.Enumeration;
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
	private Hashtable<String, LabelDto> labelsX;
	
	public LabelUtil (
			Integer orgNr,
			String lang, 
			Hashtable<String, LabelDto> labels) {
		this.orgNr = orgNr;
		this.lang = lang;
		this.labels = labels;
	
		//Build list using actual label
		labelsX = new Hashtable<>();
		Enumeration<String> keys = labels.keys();
		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			LabelDto d = labels.get(k);
			labelsX.put(d.getLabel(), d);
		}
		
	}

	
	public Integer getOrgNr() {
		return orgNr;
	}

	public String getLanguage () {
		return lang;
	}
	
	public String getLabel(String langKey) {
		return getLabel(langKey, false);
	}
	
	public String getLabel(String langKey, boolean ignoreMissing) {
		LabelDto l = labels.get(langKey);
		return l != null? l.getLabel() : langKey + (ignoreMissing?"":"?");
	}
	
	/**
	 * Return true if valid label
	 * @param label
	 * @return
	 */
	public boolean isLabel(String label) {
		LabelDto l = labelsX.get(label);
		return l != null;	
	}
	public String getLangKey(String label) {
		LabelDto l = labelsX.get(label);
		return l != null? l.getCode() : null;	
	}
}
