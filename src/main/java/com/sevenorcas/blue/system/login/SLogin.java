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

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.mail.SMailI;
import com.sevenorcas.blue.system.org.SOrgI;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlParm;
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
	 * @param language
	 * @return User object with valid flag (or null if no valid user id) 
	 */
	public EntUser getUserAndValidate (String userid, String pw, Integer orgNr, String lang) throws Exception {
		
		EntUser user = dao.getUser (userid); 
		String rtnMessage = null;
		
		//Validate user
		if (user != null) {
			user.incrementAttempts();
	
			//Determine the orgNr for this login
			if (orgNr == null) {
				user.setDefaultOrg();
			}
			else if (user.containsOrg(orgNr)){
				user.setOrgNrLogin(orgNr);
			}
			
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
			
			if (rtnMessage == null && !user.getPassword().equals(pw)) {
				rtnMessage = "invpw" + LK_APPEND + user.getAttempts() + LK_APPEND_SPLIT + org.getMaxLoginAttemptsIncludeDefault();
			}
			
			if (rtnMessage == null && !user.isActive()) {
				rtnMessage = "inarec";
			}

			if (rtnMessage != null) {
				UtilLabel labels = langSrv.getLabelUtil(orgNr, null, lang, null);	
				rtnMessage = labels.getLabel(rtnMessage, true);
				user.setInvalidMessage("_" + rtnMessage);
				return user;	
			}
			
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
	public List<String> getUserRoles (Long userId) throws Exception {
		return dao.getUserRoles(userId, new SqlParm().setActiveOnly());
	}
	
	/**
	 * Return comma separated list of roles for the passed in user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
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
			return new JsonRes().setData("_" + rtnMessage);
		}
		
		
		String newPW = "TestPw";
		LocalDateTime d = LocalDateTime.now();
//		d = d.plusHours(24L);
		d = d.plusMinutes(5L);
		
		user.encoder()
			.update("tempPW", newPW)
			.update("tempPWValid", d.toString());
		user.encode();
		
		Context initialContext = new InitialContext();
		SMailI mail = (SMailI)initialContext.lookup("java:module/SMail"); 
		mail.send(email, "Password Reset", newPW);
				
		rtnMessage = labels.getLabel("emailSent", true);
		return new JsonRes().setData("_" + rtnMessage);
	}
    
}
