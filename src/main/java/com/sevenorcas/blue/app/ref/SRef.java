package com.sevenorcas.blue.app.ref;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.app.ref.ent.EntCountry;
import com.sevenorcas.blue.app.ref.ent.JsonCountry;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseJsonRef;
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
	 * Reference List 
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes listJson(
			CallObject callObj,
    		SqlParm parms,
    		Class<? extends BaseEntityRef<?>> clazz
    				) throws Exception{
		
		List<? extends BaseEntityRef<?>> list = list(callObj, parms, clazz);
		List<BaseJsonRef> y = new ArrayList<>();
		for (BaseEntityRef<?> d : list) {
			y.add(d.toJson(callObj.getOrg(), false));
		}
		
		return new JsonRes().setData(y);
    }

	/**
	 * List of simple reference objects
	 *  
	 * @param callObj
	 * @param parms
	 * @param reference class
	 * @return
	 * @throws Exception
	 */
	private List<? extends BaseEntityRef<?>> list(
			CallObject callObj,
    		SqlParm parms,
    		Class<? extends BaseEntityRef<?>> clazz) throws Exception{
		return dao.list(callObj, parms, clazz);
    }
	
		
	
	
	
	
	/**
	 * List of countries
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes listCountryJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
//		List<EntCountry> list = list(callObj, parms, EntCountry.class);
//		List<JsonCountry> y = new ArrayList<>();
//		for (EntCountry d : list) {
//			y.add(d.toJson(callObj.getOrg(), false));
//		}
//		
//		return new JsonRes().setData(y);
		return null;
    }
	
	/**
	 * Return an uncommitted entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public JsonRes newCountryJson(CallObject callObj) throws Exception {
    	EntCountry o = newCountry(callObj);
    	List<JsonCountry> y = new ArrayList<JsonCountry>();
    	y.add(o.toJson(callObj.getOrg(), false));
		return new JsonRes().setData(y);
    }
  
    /**
	 * Return an uncommitted entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public EntCountry newCountry(CallObject callObj) throws Exception {
    	EntCountry o = new EntCountry();
    	o.setId(dao.nextTempIdNegative())
    	 .setOrgNr(callObj.getOrgNr())
    	 .setActive()
    	 .setDvalue(false);
    	return o;
    }  
    
	
	
	/**
	 * List of currencies
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes listCurrencyJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
//		List<EntCurrency> list = list(callObj, parms, EntCurrency.class);
//		List<JsonCurrency> y = new ArrayList<>();
//		for (EntCurrency d : list) {
//			y.add(d.toJson(callObj.getOrg(), false));
//		}
//		
//		return new JsonRes().setData(y);
		return null;
    }
		
}

