package com.sevenorcas.blue.system.login;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.base.BaseDao;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlExecute;

/**
* Created July '22
* 
* Data access methods to the Login Module
* TODO Expand Module Description
* 
* [Licence]
* @author John Stewart
*/

@Stateless
public class LoginDao extends BaseDao {

	private AppProperties appProperties = AppProperties.getInstance();
	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
		
	@PersistenceContext(unitName="blue")
	protected EntityManager em;

	public LoginDto login(
    		SqlParm parms,
    		String userid,
    		String pw,
    		Integer org) throws Exception {
		
		parms = validateParms(parms);
		
		String sql = "SELECT l.id, l.xxx, l.yyy, l.attempts " +
				"FROM cntrl.zzz AS l " + 
				"WHERE l.xxx";
		
		List<Object[]> r = SqlExecute.executeQuery(parms, sql, log);
		List<LoginDto> list = new ArrayList<>();
		
		// Extract data from result set
		for (int i=0;i<r.size();i++) {
			LoginDto d = new LoginDto();
			list.add(d);
			Object[] row = r.get(i);
						
			d.setId((Long)row[0])
			 .setOrg((Integer)row[1])
			 .setCode((String)row[2])
			 .setDescr((String)row[3])
			 .setDefaultValue(appProperties.get("LanguageDefault").equals((String)row[2]));
		}
		return new LoginDto();
    }

	
	
	
}
