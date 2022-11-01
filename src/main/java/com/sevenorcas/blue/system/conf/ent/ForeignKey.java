package com.sevenorcas.blue.system.conf.ent;

/**
 * Foreign key configuration for an entity.
 * 
 * Created 01.11.2022
 * [Licence]
 * @author John Stewart
 */
public class ForeignKey {
	/** Entity field config */ public FieldConfig field;
	/** ForeignKey table    */ public String tableName;
	/** ForeignKey field    */ public String foreignKey;
	/** Error langKey       */ public String errorLangKey;
	
	public ForeignKey(String table, String foreignKey, String errorLangKey) {
		this.tableName = table;
		this.foreignKey = foreignKey;
		this.errorLangKey = errorLangKey;
	}
}
