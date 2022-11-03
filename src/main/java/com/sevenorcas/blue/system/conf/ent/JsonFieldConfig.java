package com.sevenorcas.blue.system.conf.ent;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* Field Configuration entity Json 
* 
* Created 03.11.22
* [Licence]
* @author John Stewart
*/

public class JsonFieldConfig extends BaseJsonRes{
	public String field;
	public Double max = null; 
	public Double min = null; 
	public Boolean nullState = null;
	public Boolean uniqueOrgNr = null;
	public Boolean uniqueParent = null;
}
