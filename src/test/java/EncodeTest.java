

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.field.Compare;
import com.sevenorcas.blue.system.field.Encode;
import com.sevenorcas.blue.system.field.ForeignKeyField;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlExecute;
import com.sevenorcas.blue.system.sql.SqlResultSet;
import com.sevenorcas.blue.system.sql.SqlUpdate;

public class EncodeTest extends BaseTest {
	
	private String orgTable;
	
	@Before
	public void setup() throws Exception {
		orgTable = tableName(EntOrg.class, "");
		SqlUpdate.executeQuery("DELETE FROM " + orgTable + " WHERE id=" + ORG_NR_TEMP);
		SqlUpdate.executeQuery("INSERT INTO " + orgTable + "(id, code) VALUES (" + ORG_NR_TEMP + ",'UnitTest')");
	}
	
	/**
	 * Test different object types
	 */
	@Test
	public void testObjects() {
		try {
			Hashtable<String, Object> d = data();
			Encode e = new Encode();
			data(e, d);
			e = saveAndRestoreObjects(e, d);
			assertTrue(e.encodeFlag() == 0);
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
	}

	/**
	 * Test duplicate keys throw an exception
	 */
	@Test
	public void testDuplicateKeys() {
		try {
			Encode e = new Encode();
			e.add("a", 1);
			e.add("a", 2);
			fail("Missed a duplicate key");
		} catch (Exception x) {
			//Do nothing, its ok :-)
		}
		
	}
	
	/**
	 * Test Null values
	 */
	@Test
	public void testNull() {
		try {
			Encode e = new Encode();
			e.add("a", null);
			fail("Error on null");
		} catch (Exception x) {
			//Do nothing
		}
		
	}
	
	/**
	 * Test foreign key
	 */
	@Test
	public void testForeignKey() {
		try {
			ForeignKeyField f = new ForeignKeyField()
					.field("a")
					.value(12345L)
					.schema("public")
					.table("tableX")
					.column("columnY");
			
			Hashtable<String, Object> d = new Hashtable<>();
			d.put(f.getField(), f.getValue());
			
			Encode e = new Encode();
			e.addForeignKey(f);
			
			e = saveAndRestoreObjects(e, d);
			assertTrue(e.encodeFlag() == 1);
			
			ForeignKeyField fx = e.getForeignKey(f.getField());
			assertTrue(Compare.isSame(fx.getField(), f.getField()));
			assertTrue(Compare.isSame(fx.getColumn(), f.getColumn()));
			
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
		
	}
	
	//Populate the encode object with test data
	private void data(Encode e, Hashtable<String, Object> d) throws Exception{
		Enumeration<String>keys = d.keys();
		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			e.add(k, d.get(k));
		}
	}
	
	/**
	 * Setup test data
	 * This method will generate the following object types:
	 * - Integer
	 * - Long
	 * - Double
	 * - LocalDate
	 * - Date (should not really be used, but here just in case)
	 * - String
	 * - String (empty)
	 * - String (multiple spaces)
	 * - String with special characters
	 *  
	 * @return
	 */
	private Hashtable<String, Object> data(){
		Hashtable<String, Object> d = new Hashtable<>();
		
		d.put("int", 34);
		d.put("long", -1L);
		d.put("double", 0.123);
		d.put("ld", LocalDate.now());
		d.put("lt", LocalTime.now());
		d.put("date", new Date());
		d.put("s1", "the boss");
		d.put("s2", "");
		d.put("s3", "   ");
		d.put("s4", ",\"';:!@#$%^&*()_-=+<>?[]{}~` ");
		
		return d;
	}

	/**
	 * Save and restore encoded field and test objects are the same value
	 */
	private Encode saveAndRestoreObjects(Encode e, Hashtable<String, Object> d) throws Exception{
		SqlUpdate.executeQuery("UPDATE " + orgTable + " SET encoded = '" + e.encode() + "', " + 
				"encoded_flag = " + e.encodeFlag() + " WHERE id=" + ORG_NR_TEMP);

		SqlResultSet rs = SqlExecute.executeQuery(null, "SELECT * FROM " + tableName(EntOrg.class, "") + " WHERE id=" + ORG_NR_TEMP, null);
		
		Encode x = new Encode()
    	 .decode(rs.getString(0, "encoded"))
    	 .setEncodeFlag(rs.getInteger(0, "encoded_flag"));
    	
    	Enumeration<String>keys = d.keys();
 		while (keys.hasMoreElements()) {
 			String k = keys.nextElement();
 			Object o1 = d.get(k);
 			Object o2 = e.get(k);
 			if (!Compare.isSame(o1, o2)) fail(k + " not restored correctly");		
 		}
        return x;
	}

	
}