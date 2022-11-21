package com.sevenorcas.blue.system.login;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.field.Encode;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.mail.SMailI;
import com.sevenorcas.blue.system.org.SOrgI;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.TUserI;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
* Initial Login module
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Stateless
public class SLogin extends BaseService implements SLoginI {

	@EJB private TLoginI dao;
	@EJB private TUserI userDao;
	@EJB private SOrgI orgService;
	
	/**
	 * Test the given parameters to return a valid user object (assuming they are valid).
	 * To be valid the user must:
	 * - supply a valid userid and password
	 * - not exceed the maximum login attempts (once exceeded the user is effectively shut out)
	 * - they are allowed to access the passed in org (unless the passed in org is null, then the user default is used)
	 *  
	 * @param userid
	 * @param pw
	 * @param orgNr
	 * @param language from client
	 * @return User object with valid flag (or null if no valid user id) 
	 */
	public EntUser getUserAndValidate (String userid, String pw, Integer orgNr, String lang) throws Exception {
		
		EntUser user = dao.getUser (userid); 
		String rtnMessage = null;
		
		//Validate user
		if (user != null) {
			Encode encode = user.encoder();
			user.incrementAttempts();
	
			//Determine the orgNr for this login
			if (orgNr == null) {
				user.setDefaultOrg();
			}
			else if (user.containsOrg(orgNr)){
				user.setOrgNrLogin(orgNr);
			}
			
			//Determine language
			if (lang != null && !lang.isEmpty()) {
				//use it
			}
			else if (encode.isString("lang")) {
				lang = encode.getString("lang");
			}
			else {
				lang = appProperties.get("LanguageDefault");
			}
			user.setLangLogin(lang);
			
			
			if (rtnMessage == null && !user.isOrgNrLoginValid()) {
				rtnMessage = "invorg";
			}
			
			EntOrg org = rtnMessage == null? orgService.getOrgCache(user.getOrgNrLogin()) : null;
			
			//Test attempts has not exceed maximum
			try {
				if (rtnMessage == null && org.isMaxLoginAttempts(user.getAttempts())) {
					rtnMessage = "maxatt";
				}
			} catch (Exception x) {}
			
			if (rtnMessage == null && !user.isPassword(pw)) {
				
				//Test for temporary password
				String tPw = encode.getString("tempPW");
				if (tPw != null && tPw.equals(pw)) {
					String valid = encode.getString("tempPWValid");
					LocalDateTime d = LocalDateTime.parse(valid);
					if (d.isAfter(LocalDateTime.now())) {
						user.setChangePassword();
					}
				}
				
				if (!user.isChangePassword()) {
					rtnMessage = "invpw" + LK_APPEND + user.getAttempts() + LK_APPEND_SPLIT + org.getMaxLoginAttemptsIncludeDefault();
				}
			}
			
			if (rtnMessage == null && !user.isActive()) {
				rtnMessage = "inarec";
			}

			if (rtnMessage != null) {
				UtilLabel labels = langSrv.getLabelUtil(user.getOrgNrLogin(), null, lang, null);	
				rtnMessage = labels.getLabel(rtnMessage, true);
				user.setInvalidMessage("_" + rtnMessage);
				return user;	
			}
			
			//Remove temporary password
			encode.set("tempPW", null)
				  .set("tempPWValid", null)
			      .encode();
			
			user.setValidUser()
			    .setAttempts(0)
			    .setLastlogin(Timestamp.valueOf(LocalDateTime.now()));
		}
		
		return user;
	}

	/**
	 * Detached the entity from JPA
	 * @param EntUser
	 * @param ent
	 */
	public void detach (EntUser ent) throws Exception {
		dao.detach(ent);
	}
	
	/**
	 * Return userid for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getUserid (Long userId) throws Exception {
		return dao.getUserid(userId, new SqlParm());
	}
	
	
	/**
	 * Return list of roles for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<String> getUserRoles (Long userId) throws Exception {
		return dao.getUserRoles(userId, new SqlParm().setActiveOnly());
	}
	
	/**
	 * Return comma separated list of roles for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public String getUserRolesAsString (Long userId)  throws Exception {
		List<String> roles = getUserRoles(userId);
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<roles.size();i++) {
			sb.append((sb.length()>0?",":"") + roles.get(i));
		}
		return sb.toString();
	}
	
	/**
     * Persist the user entity 
     * @param entity
     * @return
     */
    public EntUser persistAfterLogin (EntUser ent) throws Exception {
    	return dao.persistAfterLogin(ent);
	}
    
    
    /**
	 * Get a user's permission list
	 * Process CRUD values for duplicate urls
	 * 
	 * @param User ID
	 * @return
	 * @throws Exception
	 */
	public List<EntPermission> permissionList(Long userId) throws Exception {
		return userDao.permissionList(null, userId);				
	}
	
	
    
    /**
     * Send a reset password
     * This valid for 24 hours
     * 
     * ToDo Use https://www.baeldung.com/java-generate-secure-password to generate pw
     * 
     * @param user email to send
     * @param client language
     * @return
     */
    public JsonRes emailTempPassword (String email, String lang) throws Exception {	
		
    	EntUser user = null;
    	String rtnMessage = null;
    	
		//Check valid email and user
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();

			user = dao.getUser (email);
			if (user == null) {
				rtnMessage = "emailInv";	
			}
		} catch (AddressException ex) {
		   	rtnMessage = "emailInv";
		}
		
		
		UtilLabel labels = langSrv.getLabelUtil(
    			appProperties.getInteger("OrgNrDefault"), 
    			null, 
    			lang != null? lang : appProperties.get("LanguageDefault"),
    			null);
		
		if (rtnMessage != null) {
			rtnMessage = labels.getLabel(rtnMessage, true);
			return new JsonRes().setData(LK_SERVER_MESSAGE + rtnMessage);
		}
		
		
		String newPW = "TestPw";
		LocalDateTime d = LocalDateTime.now();
		d = d.plusHours(24L);
//		d = d.plusMinutes(5L);
		
		user.encoder()
			.set("tempPW", newPW)
			.set("tempPWValid", d.toString()) 
		    .encode();
		
		Context initialContext = new InitialContext();
		SMailI mail = (SMailI)initialContext.lookup("java:module/SMail"); 
		
		String header = "Password Reset Request";
		String message = "A temporary password has been requested.\n"
				+ "If you didn't request this please contact your system administrator as soon as possible.\n"
				+ "\n"
				+ "The temporary password is: " + newPW + "\n" 
				+ "This password is valid for 24 hours."
				;
		
		
		mail.send(email, header, message);
				
		rtnMessage = labels.getLabel("emailSent", true);
		return new JsonRes().setData(LK_SERVER_MESSAGE + rtnMessage);
	}
    
    /**
     * Logout a user
     * @param httpRequest
     * @param callOb
     * @return
     */
    public JsonRes logout(CallObject callOb) throws Exception {	
    	UtilLabel labels = langSrv.getLabelUtil(
    			callOb.getOrgNr(), 
    			null, 
    			callOb.getLang(),
    			null);
    	
    	callOb.getHttpSession().invalidate();
    	return new JsonRes().setData(labels.getLabel(LK_LOGOUT_MESSAGE, true));
	}
	
}
