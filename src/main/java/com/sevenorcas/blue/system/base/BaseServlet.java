package com.sevenorcas.blue.system.base;

import javax.servlet.http.HttpServlet;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.ApplicationI;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet implements ApplicationI {
	public AppProperties appProperties = AppProperties.getInstance();
}
