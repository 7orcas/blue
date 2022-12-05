package com.sevenorcas.blue.app.ref;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

/**
 * Typesafe Heterogenous Container for reference entity lists
 * Thanks to https://stackoverflow.com/questions/3397160/how-can-i-pass-a-class-as-parameter-and-return-a-generic-collection-in-java
 * 
 * Created 05.12.22
 * [Licence]
 * @author John Stewart
 */

public class ReferenceDELETE {
	private Map<Type, Object> references = new HashMap<Type, Object>();

    public <T> void setReference(ReferenceTypeDELETE<T> ref, T thing) {
        references.put(ref.getType(), thing);
    }
    public <T> T getReference(ReferenceTypeDELETE<T> ref) {
        @SuppressWarnings("unchecked")
        T ret = (T) references.get(ref.getType());
        return ret;
    }
}
