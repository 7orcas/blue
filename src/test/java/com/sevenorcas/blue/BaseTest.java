package com.sevenorcas.blue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.sevenorcas.blue.system.exception.BaseException;

public class BaseTest {

	static final String DB_URL = "jdbc:postgresql://localhost/blue";
	static final String USER = "postgres";
	static final String PASS = "7o";

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
	
	public Statement getStatement() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS).createStatement();
	}
	  
	public void showException (Exception e) {
		
		System.out.println("Got Exception: " + e.getClass().getSimpleName());
		System.out.println(e.getMessage());
		
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
	
}
