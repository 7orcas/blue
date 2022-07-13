package com.sevenorcas.blue.app.home;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.json.JSONObject;

import com.sevenorcas.blue.system.cache.Item;
import com.sevenorcas.blue.system.cache.Manager;
import com.sevenorcas.blue.system.rest.BaseRest;

/**
 * 
 * [Licence]
 * @author John Stewart
 */

@Stateless
@Path("/home")
@Produces({"application/json"})
@Consumes({"application/json"})
public class HomeRest extends BaseRest {

	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	@EJB
	private Manager m;
	
	@GET
	@Path("lang")
    public String languagePack(@Context HttpServletRequest httpRequest) {
System.out.println("HomeRest.languagePack() called");

//		Manager m = new Manager();
		List l = m.getCacheList();
		for (int i=0; i<l.size(); i++) {
			Item item = (Item)l.get(i);
			System.out.println("HomeRest cache k=" + item.getKey() + "  v=" + item.getValue());
		}

		JSONObject j = new JSONObject("{key: 'x', value: 'lang pack'}");
    	return j.toString();
    }
	
}
