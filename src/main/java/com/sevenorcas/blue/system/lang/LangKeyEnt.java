package com.sevenorcas.blue.system.lang;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sevenorcas.blue.system.base.BaseEnt;

/**
* Created July '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Entity
@Table(name="lang_key", schema="cntrl")
public class LangKeyEnt extends BaseEnt {

	private static final long serialVersionUID = 1L;
	
	public LangKeyEnt () {
		super();
	}
	
}
