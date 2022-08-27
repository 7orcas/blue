package com.sevenorcas.blue.system.base;

import javax.servlet.http.HttpServlet;

import com.sevenorcas.blue.system.AppProperties;

public class BaseServlet extends HttpServlet {
	public AppProperties appProperties = AppProperties.getInstance();
}
