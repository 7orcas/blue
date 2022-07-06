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
	
}
