package com.sevenorcas.blue.system.conf;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseTransferI;

/**
* 
* Data transfer methods for validation interface
* 
* Created 19.10.22
* [Licence]
* @author John Stewart
*/

@Local
public interface TValidationI extends BaseTransferI {
	public List<Object> fields(String field, Integer orgNr, String parentColumn, Long parentId, Long entityId, Class<?> clazz) throws Exception;
}
