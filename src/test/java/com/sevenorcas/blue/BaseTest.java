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
import com.sevenorcas.blue.system.exception.BaseException;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.login.ClientSession;
import com.sevenorcas.blue.system.org.ent.EntOrg;

public class BaseTest extends BaseUtil {

	static final String DB_NAME = "blue";
	static final String DB_URL = "jdbc:postgresql://localhost/blue";
	static final String USER = "postgres";
	static final String PASS = "7o";
	static final String LANG = "en";
	static final Integer ORG_NR = 1;
	
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

	
	public void setupEJBs(Object entity) throws Exception {
		for (Field field : entity.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(EJB.class)) {
				field.setAccessible(true);
				if (ejbs.containsKey(field.getType().getCanonicalName())) {
					field.set(entity, ejbs.get(field.getType().getCanonicalName()));	
				}
				else {
					Object x = field.getType().newInstance();
					ejbs.put(field.getType().getCanonicalName(), x);
					field.set(entity, x);
					setupEJBs(x); //recursive
				}
			}
		}
		setupDataSource();
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
	 * Setup data source 
	 * @return datasource name
	 */
	public String setupDataSource(){
		String ds = "java:jboss/datasources/blueDS";
		
		try {
		    InitialContext ctx = new InitialContext();
		    ctx.lookup(ds);
		    return ds;
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
			ic.bind(ds, source);
			
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
}
