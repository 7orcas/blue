package com.sevenorcas.blue.app.ref;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.app.ref.ent.EntType1;
import com.sevenorcas.blue.app.ref.ent.JsonType1;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Reference Module service bean.
*  
* Create 02.12.2022
* [Licence]
* @author John Stewart
*/

@Stateless
public class SRef extends BaseService implements SRefI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB private TRefI dao;
	
	/**
	 * List of user objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes listType1Json(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<BaseEntityRef<?>> x = list(callObj, parms, "type1");
		List<EntType1> list = new ArrayList<>();
		for (BaseEntityRef<?> r : x) {
			list.add(new EntType1(r));
		}
		
		List<JsonType1> y = new ArrayList<>();
		for (EntType1 d : list) {
			y.add(d.toJson(callObj.getOrg(), false));
		}
		
		return new JsonRes().setData(y);
    }
	
	/**
	 * List of reference objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @param reference type
	 * @return
	 * @throws Exception
	 */
	private List<BaseEntityRef<?>> list(
			CallObject callObj,
    		SqlParm parms,
    		String reftype) throws Exception{
		return dao.list(callObj, parms, reftype);
    }
	
		
}

