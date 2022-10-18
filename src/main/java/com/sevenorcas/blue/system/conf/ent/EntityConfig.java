package com.sevenorcas.blue.system.conf.ent;

import java.util.Hashtable;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;

import com.sevenorcas.blue.system.base.BaseUtil;
import com.sevenorcas.blue.system.base.JsonRes;

/**
 * Configuration for an entity.
 * 
 * Created 24.09.2022
 * [Licence]
 * @author John Stewart
 */

public class EntityConfig extends BaseUtil {

	@JsonbTransient
	public String tableName;
	
	public Hashtable<String, FieldConfig > fields;
	
	public EntityConfig () {
		fields = new Hashtable<>();
	}
	
	public EntityConfig put (FieldConfig fc) {
		fields.put(fc.name, fc);
		return this;
	}
	
	/**
	 * Is the field unused?
	 * @param field
	 * @return
	 */
	public boolean isUnused (String field) {
		FieldConfig c = fields.get(field);
		return c != null && c.isUnused();
	}
	
	public JsonRes toJSon() throws Exception {
		return new JsonRes().setData(list());
	}
	
	public List<FieldConfig> list() throws Exception {
		return hashtableToListCode(fields);
	}
}
