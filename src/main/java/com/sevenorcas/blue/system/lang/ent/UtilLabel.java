package com.sevenorcas.blue.system.lang.ent;

import java.util.Enumeration;
import java.util.Hashtable;

import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;

/**
* Label utility file for use inside java objects 
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public class UtilLabel implements IntHardCodeLangKey {

	private Integer orgNr;
	private String lang;
	private Hashtable<String, DtoLabel> labels;
	private Hashtable<String, DtoLabel> labelsX;
	
	public UtilLabel (
			Integer orgNr,
			String lang, 
			Hashtable<String, DtoLabel> labels) {
		this.orgNr = orgNr;
		this.lang = lang;
		this.labels = labels;
	
		//Build list using actual label
		labelsX = new Hashtable<>();
		Enumeration<String> keys = labels.keys();
		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			DtoLabel d = labels.get(k);
			labelsX.put(d.getLabel(), d);
		}
		
	}

	public String stripLabelAppend(String langKey) {
		if (langKey == null) return langKey;
		int i = langKey.indexOf(LK_APPEND);
		if (i == -1) return langKey;
		return langKey.substring(0, i);
	}

	private String getLabelAppend(String langKey) {
		if (langKey == null) return null;
		int i = langKey.indexOf(LK_APPEND);
		if (i == -1) return null;
		return langKey.substring(i+1);
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
		String key = stripLabelAppend(langKey);
		
		DtoLabel l = labels.get(key);
		String label = l != null? l.getLabel() : key + (ignoreMissing?"":"?");
		
		String appends = getLabelAppend(langKey);
		if (appends != null) {
			String [] s1 = appends.split(LK_APPEND_SPLIT);
			for (int i=0;i<s1.length;i++) {
				label = label.replace("%" + (i+1), s1[i]);		
			}
		}
		return label;
	}
	
	/**
	 * Return true if valid label
	 * @param label
	 * @return
	 */
	public boolean isLabel(String label) {
		DtoLabel l = labelsX.get(label);
		return l != null;	
	}
	public String getLangKey(String label) {
		DtoLabel l = labelsX.get(label);
		return l != null? l.getCode() : null;	
	}
}
