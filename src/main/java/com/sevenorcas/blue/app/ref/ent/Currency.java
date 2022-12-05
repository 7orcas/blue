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
 * Currency Reference entity
 * 
 * Created 05.12.22 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="currency")
public class Currency extends BaseEntityRef<Currency> {
	static final private long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="currency_id_seq", sequenceName="currency_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="currency_id_seq")
	private Long id;
	
	public Currency() {}
	
	public Currency(BaseEntityRef<?> ent) {
		init(ent);
	}
	
	public JsonCurrency toJson(EntOrg org, boolean fullEntity) throws Exception {
		JsonCurrency j = super.toJson(new JsonCurrency());
		return j;
	}

	public Long getId() {
		return id;
	}
	public Currency setId(Long id) {
		this.id = id;
		return this;
	}
}
