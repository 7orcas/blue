package com.sevenorcas.blue.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 * A simple CDI service which is able to say hello to someone
 *
 * @author Pete Muir
 *
 */
@Stateless
public class HelloService {

	static final String DS = "java:jboss/datasources/testDS";
	static final String QUERY = "SELECT label FROM testtable";
	
	@PersistenceContext(unitName="blue")
	protected EntityManager em;
	
    public String createHelloMessage(String name) {
    	
    	Integer idx;
    	String xxx;
    	Connection conn;
    	Statement stmt;
    	
    	xxx = "";
    	
    	try {
    		DataSource ds = (DataSource) new InitialContext().lookup(DS);
    		conn = ds.getConnection();
    		stmt = conn.createStatement();
    		
    		
    		ResultSet rs = stmt.executeQuery("select max(id) AS xxx from testtable");
           // Extract data from result set
           while (rs.next()) {
              // Retrieve by column name
        	   	idx = rs.getInt("xxx");
        	   	idx = idx + 1;
        	   	Label l = new Label();
	           	l.setID(idx);
	           	l.setLabel("J" + idx);
	           	em.persist(l);
           }
    	} catch (Exception x) {
    		x.printStackTrace();	
    	}
    	
    	
    	
    	// Open a connection
    	try {
    		DataSource ds = (DataSource) new InitialContext().lookup(DS);
    		conn = ds.getConnection();
    		stmt = conn.createStatement();
    		
    		
    		ResultSet rs = stmt.executeQuery(QUERY);
           // Extract data from result set
           while (rs.next()) {
              // Retrieve by column name
        	   xxx = xxx + " " + rs.getString("label");
              System.out.print("ID: " + rs.getString("label"));
           }
    	} catch (Exception x) {
    		x.printStackTrace();	
    	}
    	
        return "Hello " + name + ", and hello God and all!: " + xxx;
    }

}