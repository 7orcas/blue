package com.sevenorcas.blue.system.login;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseServlet;

public class LoginServlet extends BaseServlet {
    
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());    
	
	@Override
	public void init() {
	}
	
    /**
     * Process GET request.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

	/**
     * Process POST request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process the actual request.
     * @param request The request to be processed.
     * @param response The response to be created.
     * @throws IOException If something fails at I/O level.
     */
    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String url = request.getRequestURL().toString();

    	//Get new URL to forward to
 		url = url.replaceFirst(LOGIN_PATH, APPLICATION_PATH);
		int index = url.indexOf(APPLICATION_PATH + "/");
		url = "/" + url.substring(index);

System.out.println("Login Servlet url=" + url);    	
		//Forward to rest for processing

		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
		rd.forward(request, response);
    	
    }
 
}
