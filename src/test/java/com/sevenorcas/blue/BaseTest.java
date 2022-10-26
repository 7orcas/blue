package com.sevenorcas.blue;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.postgresql.ds.PGPoolingDataSource;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.base.BaseTransfer;
import com.sevenorcas.blue.system.base.BaseUtil;
import com.sevenorcas.blue.system.conf.SConfig;
import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.SLang;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.role.ent.EntRole;

/**
 * Base test object 
 * Created July '22
 * [Licence]
 * @author John Stewart
 */
public class BaseTest extends BaseUtil implements ConfigurationI {

	static final public String DB_NAME      = "blue";
	static final public String DB_URL       = "jdbc:postgresql://localhost/blue";
	static final public String USER         = "postgres";
	static final public String PASS         = "7o";
	static final public String LANG         = "en";
	static final public Integer ORG_NR      = 99;
	static final public Integer ORG_NR_TEMP = 999; //create and delete for each test

	public SLang langSrv;	
	public UtilLabel util;
	public SConfig configSrv;
	public EntityConfig config;
	public BaseTransfer baseTransfer;
	public String dataSource;
	public CallObject callObject;
	
	public BaseTest() {
		setupDataSource();
		callObject = getCallObject();
		try {
			baseTransfer = setupEJBs(new BaseTransfer());
			configSrv = setupEJBs(new SConfig());
			config = configSrv.getConfig(getCallObject(), EntRole.class.getCanonicalName());
			langSrv = setupEJBs(new SLang());
			util = langSrv.getLabelUtil(callObject.getOrgNr(), null, callObject.getLang(), null);
		} catch (Exception x) {
			System.out.println("Instantiate BaseTest ex: " + x.getMessage());
		}
	}
	
	Hashtable<String, Object> ejbs = new Hashtable<>();

	//Don't do this
//	public Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(DB_URL, USER, PASS);
//	}
//	
//	public Statement getStatement() throws SQLException {
//		return DriverManager.getConnection(DB_URL, USER, PASS).createStatement();
//	}
	  
	public void showException (Exception e) {
		
		System.out.println("Got Exception: " + e.getClass().getSimpleName());
		System.out.println("Message: " + e.getMessage());
		if (e instanceof RedException) {
			System.out.println("Detail: " + ((RedException)e).getDetail());
		}
		
		if (e instanceof BaseException) { 
			BaseException b = (BaseException)e;
			if (b.isStackTrace()) {
				String[] s = b.stackTraceToString();
				for (int i = 0; i < s.length; i++) {
					System.out.println(s[i]);
				}
			}
		}
	}

	public <T>T setupEJBs(T entity) throws Exception {
		setupEJBsX(entity, entity.getClass());	
		return entity;
	}
	
	private void setupEJBsX(Object entity, Class<?>clazz) throws Exception {
		
		if (clazz.getSuperclass() != null) {
			setupEJBsX(entity, clazz.getSuperclass());
		}
		
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);

			if (field.isAnnotationPresent(EJB.class)) {
				if (ejbs.containsKey(field.getType().getCanonicalName())) {
					field.set(entity, ejbs.get(field.getType().getCanonicalName()));	
				}
				else {	
					String f = field.getType().getCanonicalName();
					if (f.endsWith("I")) f = f.substring(0, f.length()-1);
					Class<?> clazzX = Class.forName(f);
					Object x = clazzX.newInstance();
					ejbs.put(field.getType().getCanonicalName(), x);
					field.set(entity, x);
					setupEJBs(x); //recursive
				}
			}
			
			if (field.getType().getName().equals("javax.persistence.EntityManager")) {
				field.set(entity, new EntityManagerTest());	
			}
		}
	}
	
	public CallObject getCallObject() {
		EntOrg org = new EntOrg();
		org.setOrgNr(ORG_NR);
		CallObject o = new CallObject("").setOrg(org);
		
		ClientSession session = new ClientSession(1L);
		o.setClientSession(session);
		session.setLang(LANG);
		
		return o;
	}
	
	public <T extends BaseEntity<T>> T configEntNew (T ent) {
		ent.setId(-1L)
		   .setActive()
		   .setOrgNr(ORG_NR);
		return ent;
	}
	
	public <T extends BaseEntity<T>> T configEnt (T ent) throws Exception {
		ent.setId(nextTempId())
		   .setActive()
		   .setOrgNr(ORG_NR);
		return ent;
	}
	
	/**
	 * Predefined test records
	 */
	public void setTestData() throws Exception {
		
		
		String sql = "INSERT INTO " + BaseUtil.tableName(EntOrg.class, null) + " (id, code, org_nr, updated, updated_userid) "
				+ "VALUES (" + ORG_NR + ", 'TestOrg', " + ORG_NR + ", current_timestamp, 1)";
//		SqlExecute.executeQuery(null, sql, null);
		
		
	}
	
	public Long nextTempId() throws Exception {
		return baseTransfer.nextTempId();
	}
		
	/**
	 * Setup data source 
	 * @return datasource name
	 */
	public void setupDataSource(){
		if (dataSource != null) return; 
		
		dataSource = "java:jboss/datasources/blueDS";
		
		try {
		    InitialContext ctx = new InitialContext();
		    ctx.lookup(dataSource);
		    return;
//		    return ds;
		} catch (Exception e) {
		}
		
		try {
			
			PGPoolingDataSource source = new PGPoolingDataSource();
			source.setServerName("localhost:5432/" + DB_NAME + "?useUnicode=true&amp;characterEncoding=UTF-8&amp;characterSetResults=UTF-8");
			source.setDataSourceName("blueDS");
			source.setDatabaseName(DB_NAME);
			source.setUser("postgres");
			source.setPassword("7o");
			source.setMaxConnections(10);
	
			// Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");   
			InitialContext ic = new InitialContext();
			ic.createSubcontext("java:");
            ic.createSubcontext("java:jboss");
            ic.createSubcontext("java:jboss/datasources");
			ic.bind(dataSource, source);
			
//			return ds;
		} catch (Exception e) {
			e.printStackTrace();
//			return null;
		}
			
	}
	
}
