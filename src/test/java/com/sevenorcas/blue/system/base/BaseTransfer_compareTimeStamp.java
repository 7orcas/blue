package com.sevenorcas.blue.system.base;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.role.ent.EntRole;

/**
 * Base data transfer bean test - compare time stamps.
 * Created 25.10.2022
 * [Licence]
 * @author John Stewart
 */
public class BaseTransfer_compareTimeStamp extends BaseTransfer_ {
	
	public BaseTransfer_compareTimeStamp () {
		super();
	}
		
	@Before
	public void setup() throws Exception {
		super.setup();
	}
	
	@Test
	public void compareTimeStamp_new () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		EntRole ent = configEntNew(new EntRole());
		baseTransfer.compareTimeStamp(ent, config, errors);
		assertTrue(!errors.hasErrors());
	}
	
	@Test
	public void compareTimeStamp_unchanged () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		baseTransfer.compareTimeStamp(getRole(ROLE), config, errors);
		assertTrue(!errors.hasErrors());
	}
	
	@Test
	public void compareTimeStamp_changed () throws Exception {
		ValidationErrors errors = new ValidationErrors();
		EntRole ent = getRole(ROLE);
		baseTransfer.updateTimestampUserid(ent, 1L);
		baseTransfer.compareTimeStamp(ent, config, errors);
		assertTrue(errors.getErrors().get(0).type == VAL_ERROR_TS_DIFF);
	}
	
	
	
}
