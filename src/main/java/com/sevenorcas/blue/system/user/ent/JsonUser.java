package com.sevenorcas.blue.system.user.ent;

import java.util.List;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* User list entity Json 
* 
* Created 01.11.22
* [Licence]
* @author John Stewart
*/

public class JsonUser extends BaseJsonRes{
	public Integer attempts;
	public String password;
	public String orgs;
	public List<JsonUserRole>roles;
}