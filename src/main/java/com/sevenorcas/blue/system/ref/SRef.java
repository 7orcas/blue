package com.sevenorcas.blue.system.ref;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseJsonRef;
import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.EntOrg;
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
	 * @param reference entity class
	 * @return
	 * @throws Exception
	 */
	public JsonRes listJson(
			CallObject callObj,
    		SqlParm parms,
    		Class<? extends BaseEntityRef<?>> clazz) throws Exception{
		
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
	 * @param reference entity class
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
	 * Return an uncommitted entity
	 *
	 * @param callObj
	 * @param reference entity class
	 * @return
	 * @throws Exception
	 */
    public JsonRes newJson(CallObject callObj,
    		Class<? extends BaseEntityRef<?>> clazz) throws Exception {
    	
    	BaseEntityRef<?> ent = clazz.getDeclaredConstructor().newInstance();
    	BaseEntityRef<?> entX = (BaseEntityRef<?>)ent.newEntity(callObj.getOrg(), dao.nextTempIdNegative());
    	
    	List<BaseJsonRef> y = new ArrayList<>();
    	y.add(entX.toJson(callObj.getOrg(), false));
		return new JsonRes().setData(y);
    }
	
	
    /**
	 * Create / Update / Delete the reference list
	 * 
	 * @param callObj
	 * @param entities to do CrUD on
	 * @param reference entity class
	 * @throws Exception
	 */

    public <T extends BaseEntityRef<T>> JsonRes putReference(
    		CallObject callObj,
    		List<T> list,
    		Class<? extends BaseEntityRef<?>> clazz) throws Exception {
		
		EntityConfig config = configSrv.getConfig(callObj, clazz);
		
		//Check validation errors
		ValidationErrors vals = validateSrv.validate(list, config);
		if (vals.hasErrors()) {
			UtilLabel u = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
			vals.setLabels(u);
			return vals.toJSon();
		}
		
		//Set default value
		setDvalue(list);
		
		List<Long []> ids = new ArrayList<>();
  		try {
  			for (T ent : list) {
  				T mergedEnt = dao.put(ent, config, callObj);
  				
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  				}
  				//Merge non base fields
  				else if (!ent.isDelete()){
  					mergedEnt.encoder()
  							 .encode();
  					mergedEnt.setSort(ent.getSort());
  					mergedEnt.setDvalue(ent.isDvalue());
  				}
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  
	
    /**
     * Set a unique default value
     * Use first occurrence
     * @param callObj
	 * @param entities to do CrUD on
     * @param reference entity class
     */
    private <T extends BaseEntityRef<T>> void setDvalue(
    		CallObject callObj,
    		List<T> list,
    		Class<? extends BaseEntityRef<?>> clazz) throws Exception {
    	boolean found = false;

    	for (int i=0;i<list.size();i++) {
    		T ent = list.get(i); 		
    		if (!ent.isDelete() && ent.isDvalue()) {
    			found = true;

    			for (int j=i+1;j<list.size();j++) {
    				ent = list.get(j);
    				ent.setDvalue(false);
    			}
    			break;
			}
    	}

    	if (found) {
    		dao.resetDvalues(callObj, clazz);
    	}
    	
    }
	
		
}

