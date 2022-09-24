package com.sevenorcas.blue.system.conf;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for an entity.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */

public class EntityConfig {

	public List <FieldConfig >fields;
	
	public EntityConfig () {
		fields = new ArrayList<FieldConfig>();
	}
}
