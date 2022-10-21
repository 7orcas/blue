package com.sevenorcas.blue.system.conf.json;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* Validation Error entity Json 
* 
* Created 18.10.22
* [Licence]
* @author John Stewart
*/

public class JValError extends BaseJsonRes{
	public Long entityId;
	public String action;
	public String updatedUser;
}
