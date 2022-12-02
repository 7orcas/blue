package com.sevenorcas.blue.system.ref.ent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEntity;

/**
 * Reference Type entity
 * 
 * Created 02.12.22 
 * [Licence]
 * @author John Stewart
 */

@Entity
@Table(name="reftype")
public class EntReftype extends BaseEntity<EntReftype> {

	static final private long serialVersionUID = 1L;
	
	@Id  
	@SequenceGenerator(name="reftype_id_seq", sequenceName="reftype_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="reftype_id_seq")
	private Long id;	
	
	public EntReftype() {}
	
	public Long getId() {
		return id;
	}
	public EntReftype setId(Long id) {
		this.id = id;
		return this;
	}

}
