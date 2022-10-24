package com.sevenorcas.blue.system.conf;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;

/**
 * Entity Validation Module service bean interface.
 * Created 19.10.2022
 * [Licence]
 * @author John Stewart
 */
@Local
public interface SValidateI {
    public <T extends BaseEntity<T>> ValidationErrors validate(List<T> list, EntityConfig config) throws Exception;
    public <T extends BaseEntity<T>> void validate(List<T> list, String parentCode, Long parentId, EntityConfig config, ValidationErrors errors) throws Exception;
}
