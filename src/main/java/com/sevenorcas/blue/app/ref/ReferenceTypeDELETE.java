package com.sevenorcas.blue.app.ref;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Typesafe Heterogenous Container for reference entity lists
 * Thanks to https://stackoverflow.com/questions/3397160/how-can-i-pass-a-class-as-parameter-and-return-a-generic-collection-in-java
 * 
 * Created 05.12.22
 * [Licence]
 * @author John Stewart
 */

public abstract class ReferenceTypeDELETE<T> {
    private final Type type;

    protected ReferenceTypeDELETE() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class<?>) {
            throw new RuntimeException("Missing type parameter.");
        }
        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }
    public Type getType() {
        return this.type;
    }
}
