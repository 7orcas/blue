package com.sevenorcas.blue.app.ref;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.app.ref.ent.Country;
import com.sevenorcas.blue.app.ref.ent.Currency;
import com.sevenorcas.blue.app.ref.ent.JsonCountry;
import com.sevenorcas.blue.app.ref.ent.JsonCurrency;
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
		
		List<Country> list = list(callObj, parms, Country.class);
		List<JsonCountry> y = new ArrayList<>();
		for (Country d : list) {
			y.add(d.toJson(callObj.getOrg(), false));
		}
		
		return new JsonRes().setData(y);
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
		
//		ReferenceType<Currency> refType = new ReferenceType<Currency>(){};
//		
//		Reference ref = new Reference();
//		ref.setReference(refType, new Currency());
		
		List<Currency> list = list(callObj, parms, Currency.class);
		List<JsonCurrency> y = new ArrayList<>();
		for (Currency d : list) {
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
	private <T>List<T> list(
			CallObject callObj,
    		SqlParm parms,
    		Class<T> clazz) throws Exception{
		return dao.list(callObj, parms, clazz);
    }
	
		
}

