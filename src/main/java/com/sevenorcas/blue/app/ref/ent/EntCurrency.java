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
@Table(name="currency", schema="public")
public class EntCurrency extends BaseEntityRef<EntCurrency> {
	static final private long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="currency_id_seq", sequenceName="currency_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="currency_id_seq")
	private Long id;
	
	public EntCurrency() {}
	
	@SuppressWarnings("unchecked")
	public JsonCurrency toJson(EntOrg org, boolean fullEntity) throws Exception {
		JsonCurrency j = super.toJson(new JsonCurrency());
		return j;
	}

	public Long getId() {
		return id;
	}
	public EntCurrency setId(Long id) {
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
    public EntCurrency newEntity(EntOrg org, Long id) throws Exception {
    	EntCurrency ent = new EntCurrency();
    	ent.setId(id)
    	 .setOrgNr(org.getOrgNr())
    	 .setActive()
    	 .setSort(0)
    	 .setDvalue(false);
    	return ent;
    }
  
}
