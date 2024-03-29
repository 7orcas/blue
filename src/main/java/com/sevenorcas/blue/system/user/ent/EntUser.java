package com.sevenorcas.blue.system.user.ent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.FieldConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationCallbackI;
import com.sevenorcas.blue.system.conf.ent.ValidationError;
import com.sevenorcas.blue.system.login.ent.JsonUrlPermission;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.ent.EntPermission;

/**
 * User entity 
 * 
 * TODO document this bean
 * 
 * Notes: 
 * - orgNr is transient, ie changes with each login. Potentially a user could log into multiple org's 
 *   (each login attempt uses the orgNr to store the current attempt)
 * 
 * Created July '22 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="zzz", schema="cntrl")
public class EntUser extends BaseEntity<EntUser> {

	static final private long serialVersionUID = 1L;
	static final public String USERNAME = "xxx";
	static final public String PASSWORD = "yyy";
	
	@Id  
	@SequenceGenerator(name="zzz_id_seq", sequenceName="cntrl.zzz_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="zzz_id_seq")
	private Long id;
	
	@Column(name=USERNAME)
	private String userName;
	@Column(name=PASSWORD)
	private String password;
	private String orgs;
	private Integer attempts;
	@Column(name = "lastlogin")
	private Timestamp lastLogin;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entUser")
	private List <EntUserRole> roles = new ArrayList<>(); 

	@Transient
	private List <EntPermission> permissions = new ArrayList<>();
	
	/**
	 * Override field configurations
	 */
	static public EntityConfig getConfig (EntOrg org) throws Exception {
		return BaseEntity.getConfig(org)
				.put(new FieldConfig("code").max(30).uniqueIgnoreOrgNr())
				.put(new FieldConfig("password").nonNull().max(20))
				.put(new FieldConfig("orgNr").nonNull().min(0).max(0)) //Not used
				.put(new FieldConfig("attempts").nonNull().min(0))
				.put(new FieldConfig("orgs").nonNull().max(20)
	    	    		.callback(new ValidationCallbackI() {
							@Override
							public <T extends BaseEntity<T>> ValidationError validate(T ent) throws Exception {
								EntUser p = (EntUser)ent;
								try {
									String [] s = p.orgs.split(",");
									for (String o : s) {
										int x = Integer.parseInt(o);
										if (x<=BASE_ORG_NR) throw new Exception();
									}
									return null;
								} catch (Exception x) {
									return new ValidationError(VAL_ERROR_INVALID_VALUE)
											.setEntityId(ent.getId())
											.setCode(ent.getCode())	
											.setField("orgs");
								}
							}
	    	    		}));
				
	}
	
	/**
	 * To be valid, correct password and org and attempts < max
	 */
	@Transient private Integer orgNrLogin;
	@Transient private Boolean validUser;
	@Transient private String inValidMessage;
	@Transient private String langLogin;
	@Transient private Boolean changePassword;
	@Transient private Boolean loggedIn;
	@Transient private Boolean adminLoggedIn;
	@Transient private Boolean devAdmin;
	

	
	public EntUser () {}
	
	public JsonUser toJson(EntOrg org, boolean fullEntity) throws Exception {
		JsonUser j = super.toJson(new JsonUser());
		j.code = userName;
		j.attempts = attempts;
		j.loggedIn = isLoggedIn();
		j.maxAttemptsExceeded = org.isMaxLoginAttempts(attempts);
		j.lastLogin = lastLogin;
		
		if (!fullEntity) return j;
		
		j.password = password;
		j.orgs = orgs;
		if (roles != null) {
			j.roles = new ArrayList<>();
			for (EntUserRole p : roles) {
				j.roles.add(p.toJSon());
			}
		}
		if (permissions != null) {
			j.permissions = new ArrayList<>();
			for (EntPermission p : permissions) {
				j.permissions.add(p.toJSon());
			}
		}
		return j;
	}
	
	public List<JsonUrlPermission> toJsonUrlPermission(EntOrg org) throws Exception {
		List<JsonUrlPermission>list = new ArrayList<>();
		if (permissions != null) {
			for (EntPermission p : permissions) {
				list.add(p.toJsonUrlPermission());
			}
		}
		return list;
	}
	
	/**
	 * Find the most relevant permission for URL
	 * @param url
	 * @return
	 */
	public EntPermission findPermissionForUrl(String url) { 
		if (permissions == null 
				|| url == null
				|| url.length() == 0) {
			return null;
		}
		
		//find exact match
		for (EntPermission p : permissions) {
			if (p.getCode().equals(url)) {
				return p;
			}
		}	
		
		//find wild card permission (the start of the url is ignored)
		for (EntPermission p : permissions) {
			int index = p.getCode().indexOf("*/"); 
			if (index == -1) continue;
			
			String px = p.getCode().substring(index+1);
			if (url.length() < px.length()) continue;
			
			String urlx = url.substring(url.length() - px.length());
			if (px.equals(urlx)) {
				return p;
			}
		}
		
		//reduce specificity and search
		int index1 = url.lastIndexOf("/");	
		if (index1 != -1) {
			url = url.substring(0, index1);
			return findPermissionForUrl(url);
		}
				
		return null;
	}
	
	/**
	 * Is this the user's password?
	 * @param pw
	 * @return
	 */
	public boolean isPassword(String pw) {
		return pw != null && password.equals(pw);
	}
	
	
	public boolean isOrgNrLoginValid() {
		return orgNrLogin != null && orgNrLogin != BASE_ORG_NR;
	}
	public Integer getOrgNrLogin() {
		return orgNrLogin;
	}
	public EntUser setOrgNrLogin(Integer orgNrLogin) {
		this.orgNrLogin = orgNrLogin;
		return this;
	}

	public Long getId() {
		return id;
	}
	public EntUser setId(Long id) {
		this.id = id;
		return this;
	}
	
	public String getUserName() {
		return userName;
	}
	public EntUser setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public EntUser setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public String getOrgs() {
		return orgs;
	}
	public EntUser setOrgs(String orgs) {
		this.orgs = orgs;
		return this;
	}
	public boolean containsOrg(Integer org) {
		String [] s = orgs != null? orgs.split(",") : new String [] {""};
		for (String o : s) {
			if (org != null && o.equals(org.toString())) {
				return true;
			}
		}
		return false;
	}
	public void setDefaultOrg() {
		try {
			String [] s = orgs != null? orgs.split(",") : new String [] {""};
			orgNrLogin = Integer.parseInt(s[0]);
		} catch (Exception x) {
		}
	}

	public Integer getAttempts() {
		return attempts;
	}
	public EntUser setAttempts(Integer attempts) {
		this.attempts = attempts;
		return this;
	}
	public EntUser incrementAttempts() {
		attempts++;
		return this;
	}

	public Boolean isValidUser() {
		return validUser != null && validUser;
	}
	public EntUser setValidUser() {
		this.validUser = true;
		return this;
	}

	public String getInvalidMessage() {
		return inValidMessage;
	}
	public EntUser setInvalidMessage(String inValidMessage) {
		this.inValidMessage = inValidMessage;
		return this;
	}
	
	public List<EntUserRole> getRoles() {
		return roles;
	}
    public EntUser setRoles(List<EntUserRole> roles) {
		this.roles = roles;
		return this;
	}
    public EntUser add(EntUserRole r) {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		roles.add(r);
		return this;
	}

	public List<EntPermission> getPermissions() {
		return permissions;
	}
	public EntUser setPermissions(List<EntPermission> permissions) {
		this.permissions = permissions;
		return this;
	}

	public Timestamp getLastlogin() {
		return lastLogin;
	}
	public EntUser setLastlogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public boolean isChangePassword() {
		return is(changePassword);
	}
	public EntUser setChangePassword() {
		this.changePassword = true;
		return this;
	}

	public String getLangLogin() {
		return langLogin;
	}
	public EntUser setLangLogin(String lang) {
		this.langLogin = lang;
		return this;
	}

	public boolean isLoggedIn() {
		return loggedIn != null && loggedIn;
	}
	public EntUser setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
		return this;
	}

	public boolean isAdminLoggedIn() {
		return adminLoggedIn != null && adminLoggedIn;
	}
	public EntUser setAdminLoggedIn(boolean admin) {
		this.adminLoggedIn = admin;
		return this;
	}

	public boolean isDevAdmin() {
		return devAdmin != null && devAdmin;
	}
	public EntUser setDevAdmin() {
		this.devAdmin = true;
		return this;
	}	
    
	
    
}
