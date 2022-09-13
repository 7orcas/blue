package com.sevenorcas.blue.system.org;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.OrgDto;
import com.sevenorcas.blue.system.org.json.OrgJsonRes;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Create 22 July 2022
* 
* Organisation Module service bean.
* * 
* [Licence]
* @author John Stewart
*/

@Stateless
public class OrgSrv extends BaseSrv {

	@EJB
	private OrgDao dao;
	
	public JsonRes orgListJson(
    		CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<OrgDto> x = dao.orgList(callObj, parms);
		List<OrgJsonRes> y = new ArrayList<OrgJsonRes>();
		for (OrgDto d : x) {
			y.add(d.toJSon());
		}
		
		return new JsonRes().setData(y);
    }
	
}
