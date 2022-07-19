package com.sevenorcas.blue.system.lang;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.java.CallObject;

@Stateless
public class LangSrv {

	public List<LangData> langPackage(
    		@QueryParam ("co") CallObject callObj) {
		
		List<LangData> r = new ArrayList<>();
		
    	return r;
    }
	
}
