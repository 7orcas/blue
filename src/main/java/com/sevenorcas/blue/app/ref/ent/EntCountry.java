package com.sevenorcas.blue.app.ref.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.org.ent.EntOrg;

/**
 * Country Reference entity
 * 
 * Created 02.12.22 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="country", schema="public")
public class EntCountry extends BaseEntityRef<EntCountry> {
	static final private long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="country_id_seq", sequenceName="country_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="country_id_seq")
	private Long id;	
	
	public EntCountry() {}
	
//	public EntCountry(BaseEntityRef<?> ent) {
//		init(ent);
//	}
	
	@SuppressWarnings("unchecked")
	public JsonCountry toJson(EntOrg org, boolean fullEntity) throws Exception {
		JsonCountry j = super.toJson(new JsonCountry());
		return j;
	}
	
	public Long getId() {
		return id;
	}
	public EntCountry setId(Long id) {
		this.id = id;
		return this;
	}
	
	/**
	 * Return a new entity
	 * 
	 * @param org 
	 * @return
	 * @throws Exception
	 */
    public EntCountry newEntity(EntOrg org, Long id) throws Exception {
    	EntCountry ent = new EntCountry();
    	ent.setId(id)
    	 .setOrgNr(org.getOrgNr())
    	 .setActive()
    	 .setSort(0)
    	 .setDvalue(false);
    	return ent;
    }
  
	
}
