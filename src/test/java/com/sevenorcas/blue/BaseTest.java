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
import com.sevenorcas.blue.system.base.BaseUtil;
import com.sevenorcas.blue.system.conf.ent.ConfigurationI;
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.ent.ClientSession;
import com.sevenorcas.blue.system.org.ent.EntOrg;

public class BaseTest extends BaseUtil implements ConfigurationI {

	static final public String DB_NAME = "blue";
	static final public String DB_URL = "jdbc:postgresql://localhost/blue";
	static final public String USER = "postgres";
	static final public String PASS = "7o";
	static final public String LANG = "en";
	static final public Integer ORG_NR = 99;

	public String dataSource;
	public CallObject callObject;
	
	public void setup() throws Exception {
		callObject = getCallObject();
	}
	
	Hashtable<String, Object> ejbs = new Hashtable<>();

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
	
	public Statement getStatement() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS).createStatement();
	}
	  
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
		setupDataSource();
		for (Field field : entity.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(EJB.class)) {
				field.setAccessible(true);
				if (ejbs.containsKey(field.getType().getCanonicalName())) {
					field.set(entity, ejbs.get(field.getType().getCanonicalName()));	
				}
				else {	
					String f = field.getType().getCanonicalName();
					if (f.endsWith("I")) f = f.substring(0, f.length()-1);
					Class<?> clazz = Class.forName(f);
					Object x = clazz.newInstance();
					ejbs.put(field.getType().getCanonicalName(), x);
					field.set(entity, x);
					setupEJBs(x); //recursive
				}
			}
		}
		return entity;
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
	
	public <T extends BaseEntity<T>> T configNewEnt (T ent) {
		ent.setId(-1L)
		   .setActive()
		   .setOrgNr(ORG_NR);
		return ent;
	}
	
	/**
	 * Predefined test records
	 */
	public void setTestData() throws Exception {
		setupDataSource();
		
		String sql = "INSERT INTO " + BaseUtil.tableName(EntOrg.class, null) + " (id, code, org_nr, updated, updated_userid) "
				+ "VALUES (" + ORG_NR + ", 'TestOrg', " + ORG_NR + ", current_timestamp, 1)";
//		SqlExecute.executeQuery(null, sql, null);
		
		
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
