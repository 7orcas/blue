package com.sevenorcas.blue.system.role.json;

import java.util.List;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* Role entity Json 
* 
* Created 06.10.22
* [Licence]
* @author John Stewart
*/

public class JsonRole extends BaseJsonRes{
	public List<JsonRolePermission> permissions;
}
