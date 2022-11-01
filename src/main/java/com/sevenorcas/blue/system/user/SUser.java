package com.sevenorcas.blue.system.user;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.user.ent.EntUserRole;
import com.sevenorcas.blue.system.user.ent.ExcelUser;
import com.sevenorcas.blue.system.user.json.JsonUser;

/**
* Users Module service bean.
*  
* Create 01.11.2022
* [Licence]
* @author John Stewart
*/

@Stateless
public class SUser extends BaseService implements SUserI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB private TUserI dao;
	
	/**
	 * List of user objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes userListJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<EntUser> x = userList(callObj, parms);
		List<JsonUser> y = new ArrayList<>();
		for (EntUser d : x) {
			y.add(d.toJSon());
		}
		
		return new JsonRes().setData(y);
    }
	
	/**
	 * List of user objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public List<EntUser> userList(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		return dao.userList(callObj, parms);
    }
	
	
	
    /**
	 * Return an uncommitted user entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public JsonRes newUserJson(CallObject callObj) throws Exception {
    	EntUser o = newUser(callObj);
    	List<JsonUser> y = new ArrayList<>();
    	y.add(o.toJSon());
		return new JsonRes().setData(y);
    }
  
    /**
	 * Return an uncommitted user entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public EntUser newUser(CallObject callObj) throws Exception {
    	EntUser o = new EntUser();
    	return o.setId(dao.nextTempIdNegative())
    			.setOrgNr(BASE_ORG_NR)
		    	.setActive()
		    	;
    }  
    
    /**
	 * Create / Update / Delete the user list
	 * 
	 * @param callObj
	 * @param entities to do CrUD on
	 * @throws Exception
	 */
    public JsonRes putUsers(
    		CallObject callObj,
    		List<EntUser> list) throws Exception {
				
		//Set configurations
    	EntityConfig userConfig = configSrv.getConfig(callObj, EntUser.class);
    	EntityConfig permConfig = configSrv.getConfig(callObj, EntUserRole.class);

		//Check validation errors
		ValidationErrors vals = validateSrv.validate(list, userConfig);
		for (EntUser ent : list) {
			validateSrv.validate(ent.getRoles(), ent.getCode(), ent.getId(), permConfig, vals);
		}
		if (vals.hasErrors()) {
			UtilLabel u = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
			vals.setLabels(u);
			return vals.toJSon();
		}

		//Make changes
		List<Long []> ids = new ArrayList<>();
  		try {
  			for (EntUser ent : list) {
  				//Special case
  				if (ent.isNew()) {
  					for (EntUserRole perm : ent.getRoles()) {
  						nullBaseFields(perm, permConfig);
  	  					perm.setId(null)
  	  					    .setEntUser(ent);
  	  	  			}
  				}
  				
  				dao.put(ent, userConfig, callObj);
  				
  				if (ent.isDelete()) {
  					continue;
  				}
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  					continue;
  				}
  				
  				//Children
  				for (EntUserRole perm : ent.getRoles()) {
  					perm.setEntUser(ent);
  					dao.put(perm, permConfig, callObj);
  	  			}	
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  


    /**
	 * Export users to excel
	 * @return
	 * @throws Exception
	 */
	public Response excelExport(CallObject callObj) throws Exception {
		
		List<EntUser> x = dao.userList(callObj, null);
		UtilLabel labels = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
		ExcelUser excel = new ExcelUser(labels, x);
		
		String fn = excelSrv.createListFile("UserList", callObj.getOrgNr(), excel);
		return fileSrv.getFile(fn, "UserList.xlsx", false);
    }
    
}
