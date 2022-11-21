package com.sevenorcas.blue.system.login.ent;

import com.sevenorcas.blue.system.base.BaseJsonRes;

/**
* Crud Permission entity Json 
* Note that a perm maybe a url or a code used in the client
* 
* Created 17.11.22
* [Licence]
* @author John Stewart
*/

public class JsonUrlPermission extends BaseJsonRes{
	public String perm;
	public String crud;
}
