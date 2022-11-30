package com.sevenorcas.blue.system.role;

/**
* Permission Configuration
* 
* [Licence]
* Created 30.11.22
* @author John Stewart
*/
public interface PermissionI {
	static final public Character PERM_WILDCARD = '*';
	static final public Character PERM_CREATE   = 'C';
	static final public Character PERM_READ     = 'R';
	static final public Character PERM_UPDATE   = 'U';
	static final public Character PERM_DELETE   = 'D';
	static final public Character PERM_NONE     = '-';
}

