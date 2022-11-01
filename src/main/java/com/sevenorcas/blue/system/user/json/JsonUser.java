package com.sevenorcas.blue.system.user.json;

import java.util.List;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* User entity Json 
* 
* Created 01.11.22
* [Licence]
* @author John Stewart
*/

public class JsonUser extends BaseJsonRes{
	public String password;
	public String orgs;
	public Integer attempts;
	public List<JsonUserRole> roles;
	
}
