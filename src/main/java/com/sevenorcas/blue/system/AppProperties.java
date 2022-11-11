package com.sevenorcas.blue.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.sevenorcas.blue.system.log.AppLog;

/**
 * @ToDo
 * 
 * [Licence]
 * @author John Stewart
 */

public class AppProperties {

	static private AppProperties self;
	private Properties properties;
	
	
	/**
	 * This is a Singleton class
	 * @return
	 */
	static public AppProperties getInstance(){
		if (self == null){
			self = new AppProperties();
		}
		return self;
	}
	
	/**
	 * Create and initialize
	 */
	private AppProperties (){
	
		String homePath = System.getProperty("jboss.home.dir");
		
		if (homePath == null){
			homePath = "/opt/wildfly/";
			AppLog.info("jboss.home.dir is Null, setting to '" + homePath + "'");
		}

		
		if (!homePath.endsWith(File.separator)){
			homePath = homePath + File.separator;
		}

		homePath = homePath + "standalone" + File.separator + "deployments" + File.separator;
	
		try {
            //load a properties file
			properties = new Properties();
    		properties.load(new FileInputStream(homePath + "blue.properties"));
		
		} catch (IOException ex) {
    		ex.printStackTrace();
    		AppLog.error("Reading in App properties exception " + ex.getMessage());
		}
	}

	/**
	 * Return a value listed in the application properties file
	 * @param key
	 * @return
	 */
	public String get (String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * Return a value listed in the application properties file
	 * @param key
	 * @return
	 */
	public Integer getInteger (String key) {
		try {
			return Integer.parseInt(get(key));
		} catch (Exception x) {
			
		}
		return null;
	}
	
	/**
	 * Return a value listed in the application properties file as a string array
	 * It must be delimited by a comma
	 * @param key
	 * @return
	 */
	public String [] getArray (String key) {
		String x = properties.getProperty(key);
		if (x == null) {
			return null;
		}
		return x.split(",");
	}
	

	/**
	 * Return a boolean value listed in the application properties file
	 * @param key
	 * @return
	 */
	public boolean is (String key) {
		String p = properties.getProperty(key);
		return p != null && p.toLowerCase().equals("true");
	}

}
