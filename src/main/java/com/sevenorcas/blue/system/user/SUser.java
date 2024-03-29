package com.sevenorcas.blue.system.user;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.field.Encode;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.CacheSession;
import com.sevenorcas.blue.system.role.SRoleI;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.user.ent.EntUserRole;
import com.sevenorcas.blue.system.user.ent.ExcelUser;
import com.sevenorcas.blue.system.user.ent.JsonChangePW;
import com.sevenorcas.blue.system.user.ent.JsonUser;

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
	@EJB private SRoleI sRole;
	@EJB private CacheSession cache;
	
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
			y.add(d.toJson(callObj.getOrg(), false));
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
		List<EntUser> list = dao.userList(callObj, parms);
		if (cache != null) {
			for (EntUser d : list) {
				d.setLoggedIn(cache.containsEntUser(d.getId()));
			}
		}
		
		return list;
    }
	
	
	/**
	 * Return a user entity
	 * 
	 * @param callObj
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonRes getUserJson(
			CallObject callObj,
			Long id) throws Exception {
		if (id == null) {
			return new JsonRes().setError("inv-id", "Invalid entity id");
		}
		EntUser e = getUser(callObj, id);
		JsonRes j = new JsonRes().setData(e.toJson(callObj.getOrg(), true));
		return j;
    }
	
	/**
	 * Return a user entity
	 * @param callObj
	 * @param id
	 * @return
	 * @throws Exception
	 */
    public EntUser getUser(CallObject callObj, Long id) throws Exception {
    	EntUser ent = dao.find(EntUser.class, id);
    	
    	for (EntUserRole r : ent.getRoles()) {
    		r.setEntRole(sRole.getRole(callObj, r.getRoleId()));
    	}
    	
    	//Roles need sorting
    	Collections.sort(ent.getRoles(), new Comparator<EntUserRole>(){
		  public int compare(EntUserRole o1, EntUserRole o2) {
		     return compareTo(o1.getEntRole(), o2.getEntRole());
		  }
		});
    	
    	//Process permissions
    	ent.setPermissions(dao.permissionList(null, ent.getId()));
    	
    	ent.setLoggedIn(cache.containsEntUser(ent.getId()));
    	return ent;
    }
	
    /**
	 * Change a user's password
	 * 
	 * @param callObj
	 * @param current password (optional)
	 * @param new password
	 * @param confirm password
	 * @return
	 * @throws Exception
	 */
	public JsonRes changePW(
			CallObject callObj,
			String passcurr,
			String passnew,
			String passconf) throws Exception {
		
		EntUser user = dao.find(EntUser.class, callObj.getUserId());
		//EntUser user = getUser(callObj, parm, callObj.getUserId()); //DELETE
		String rtnMessage = null;
		
		//Current password may not be included
		if (passcurr != null && !user.isPassword(passcurr)) {
			rtnMessage = LK_PW_INVALID_CURRENT;
			
			log.info("Attempted password change with invalid current PW: userid " + callObj.getUserId());
		}
		else if (passnew == null 
				|| passconf == null
				|| !passnew.equals(passconf)) {
			rtnMessage = LK_PW_INVALID_NEW;	
		}
		else if (!isValidPassword(passnew)) {
			rtnMessage = LK_PW_NEW_NOT_STANDARD;	
		}
		else if (passcurr.equals(passnew)) {
			rtnMessage = LK_PW_NOT_CHANGED;	
		}
		else {
			user.setPassword(passnew);
		}

		UtilLabel labels = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
		JsonChangePW rtn = new JsonChangePW();
		if (rtnMessage == null) {
			rtn.result = JS_OK;
			rtn.message = labels.getLabel(LK_PW_CHANGED);
		}
		else {
			rtn.result = JS_ERROR;
			rtn.message = labels.getLabel(rtnMessage);	
		}
		
		JsonRes j = new JsonRes().setData(rtn);
		return j;
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
    	y.add(o.toJson(callObj.getOrg(), true));
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
    			.setAttempts(0)
    			.setPassword("")
		    	.setActive();
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
  					for (EntUserRole child : ent.getRoles()) {
  						nullBaseFields(child, permConfig);
  	  					child.setId(null)
  	  					    .setEntUser(ent);
  	  	  			}
  				}
  				
  				EntUser mergedEnt = dao.put(ent, userConfig, callObj);
  				
  				if (ent.isDelete()) {
  					continue;
  				}
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  					continue;
  				}
  				
  				//Merge non base fields
  				mergedEnt.setAttempts(ent.getAttempts()) 
  				         .setOrgs(ent.getOrgs());
  				
  				//Children
  				for (EntUserRole child : ent.getRoles()) {
  					child.setEntUser(ent);
  					dao.put(child, permConfig, callObj);
  	  			}	
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  


	/**
	 * Update the user configuration
	 * @param callObj
	 * @param config item
	 * @param value
	 * @throws Exception
	 */
	public void putConfig(
    		CallObject callObj,
    		String config,
    		String value)  throws Exception {
		
		EntUser mergedEnt = dao.find(EntUser.class, callObj.getUserId());
		Encode encode = mergedEnt.encoder();
		
		switch (config) {
			case "theme":
				encode.set(config, Integer.parseInt(value));
				break;
			
			default: throw new RedException(LK_UNKNOWN_ERROR, "config:" + config + "=" + value);
		}
		encode.encode();
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

	/**
	 * Is this a valid password?
	 * @param pw
	 * @return
	 */
	public boolean isValidPassword(String pw) throws Exception {
		boolean x = pw != null && pw.length() > 2;
		return x;
	}

	
}

