package com.sevenorcas.blue.app.ref.ent;

import javax.persistence.Entity;

import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Type1 Reference entity
 * TESTING 
 * 
 * Created 02.12.22 
 * [Licence]
 * @author John Stewart
 */

@Entity
public class EntType1 extends BaseEntityRef<EntType1> {
	static final private long serialVersionUID = 1L;
	
	public EntType1() {}
	
	public EntType1(BaseEntityRef<?> ent) {
		init(ent);
	}
	
	public JsonType1 toJson(EntOrg org, boolean fullEntity) throws Exception {
		JsonType1 j = super.toJson(new JsonType1());
		return j;
	}
	
}
