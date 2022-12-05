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
@Table(name="country")
public class Country extends BaseEntityRef<Country> {
	static final private long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="country_id_seq", sequenceName="country_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="country_id_seq")
	private Long id;	
	
	public Country() {}
	
	public Country(BaseEntityRef<?> ent) {
		init(ent);
	}
	
	public JsonCountry toJson(EntOrg org, boolean fullEntity) throws Exception {
		JsonCountry j = super.toJson(new JsonCountry());
		return j;
	}
	
	public Long getId() {
		return id;
	}
	public Country setId(Long id) {
		this.id = id;
		return this;
	}
	
}
