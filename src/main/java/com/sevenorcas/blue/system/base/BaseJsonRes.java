package com.sevenorcas.blue.system.base;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
* Base JSON Object for actual module JSON classes to extend
* 
* Created 22 July 2022
* [Licence]
* @author John Stewart
*/

public class BaseJsonRes {
	public Long id;
	public String code;
	public String descr;
	public Integer orgNr;
	public Boolean active;
	public Timestamp updated;
	public Long updatedX;
	
	public void initialise (BaseEntity<?> ent) {
		id = ent.getId();
		code = ent.getCode();
		descr = ent.getDescr();
		orgNr = ent.getOrgNr();
		active = ent.isActive();
		updated = ent.getUpdated();
		
		LocalDateTime d = ent.getUpdated().toLocalDateTime();
		ZonedDateTime zdt = ZonedDateTime.of(d, ZoneId.systemDefault());
		updatedX = zdt.toInstant().toEpochMilli();
	}
	
}
