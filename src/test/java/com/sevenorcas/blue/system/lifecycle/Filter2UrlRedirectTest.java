package com.sevenorcas.blue.system.lifecycle;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.login.ent.ClientSession;

/**
 * REdirect Filter test.
 * 
 * Created 28.11.2022
 * [Licence]
 * @author John Stewart
 */
public class Filter2UrlRedirectTest extends BaseTest {

	private Filter2UrlRedirect filter;
	private ClientSession clientSession;
	
	private String [][] urls = {
			{"a/$x?w=1",     "2", "/x?w=1",     "x"},
			{"a/$x/",        "4", "/x/",        "x"},
			{"a/$",          "2", "/",          ""},
			{"a/b/d/$q/y",   "1", "/q/y",       "q/y"},
			{"a/b/d/$q/y/",  "1", "/q/y/",      "q/y"},
			{"d/$q/y?=1&2=3","1", "/q/y?=1&2=3","q/y"},
	};
	
	@Before
	public void setup() throws Exception {
		filter = new Filter2UrlRedirect();
		filter.init(null);
		clientSession = new ClientSession(getEntUser());
		for (String[]url : urls) {
			Integer n = Integer.parseInt(url[1]);
			String nrUrl = clientSession.setSessionNr(n).getUrlSegment(); 
			url[0] = url[0].replace("$", nrUrl);
		}
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void getClientSessionNr() {
		try {
			for (String[]url : urls) {
				Integer n = Integer.parseInt(url[1]);
				Integer x = filter.getClientSessionNr(url[0]);
				assertTrue(x.intValue() == n.intValue());
			}
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void getURLAfterClientSessionNr() {
		try {
			for (String[]url : urls) {
				String urlx = filter.getURLAfterClientSessionNr(url[0]);
				//System.out.println(urlx + "=" + url[2]);
				assertTrue(urlx.equals(url[2]));
			}
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void cleanUrl() {
		try {
			for (String[]url : urls) {
				String urlx = filter.getURLAfterClientSessionNr(url[0]);
				urlx = filter.cleanUrl(urlx);
				System.out.println(urlx + "=" + url[3]);
				assertTrue(urlx.equals(url[3]));
			}
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
