package com.sevenorcas.blue.system.conf.ent;

import java.util.ArrayList;
import java.util.Enumeration;
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
		fields.put(fc.field, fc);
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
	
	public boolean isUniqueParent (String field) {
		FieldConfig c = fields.get(field);
		return c != null && c.isUniqueInParent();
	}
	
	public boolean isUniqueIgnoreOrgNr (String field) {
		FieldConfig c = fields.get(field);
		return c != null && c.isUniqueIgnoreOrgNr();
	}
	
	public boolean containsForeignKey() {
		Enumeration<String> keys = fields.keys();
		while (keys.hasMoreElements()) {	
			if (fields.get(keys.nextElement()).isForeignKey()) {
				return true;
			}
		}
		return false;
	}
	
	public List<ForeignKey> getForeignKeys() {
		List<ForeignKey> foreignKeys = new ArrayList<>();
		Enumeration<String> keys = fields.keys();
		while (keys.hasMoreElements()) {
			FieldConfig c = fields.get(keys.nextElement());
			if (c.isForeignKey()) {
				foreignKeys.addAll(c.getForeignKeys());
			}
		}
		return foreignKeys;
	}
	
	public JsonRes toJSon() throws Exception {
		List<FieldConfig> list = list();
		List<JsonFieldConfig> fields = new ArrayList<>();
		for (int i=0;list!=null && i<list.size();i++) {
			fields.add(list.get(i).toJSon());
		}
		return new JsonRes().setData(fields);
	}
	
	public List<FieldConfig> list() throws Exception {
		return hashtableToListCode(fields);
	}
}
