package com.sevenorcas.blue.system.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate fields with configurations
 * 
* Create 24.09.22
* [Licence]
* @author John Stewart
 */

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Field {
	
	/**
	 * Field must be unique in record set.
	 */
	boolean unique() default false;

	/**
	 * Field must contain a value equal to or greater than the min (unless -1)
	 * Note this is context dependent on the field type, eg:
	 * <ul>- integer / double field: min represents the actual value</ul>
	 * <ul>- string field: min represents the value length</ul>
	 */
	double min() default -1;
    
    /**
	 * Field must contain a value equal to or less than the max.<br>
	 * Note this is context dependent on the field type, eg:
	 * <ul>- integer / double field: max represents the actual value</ul>
	 * <ul>- string field: max represents the value length</ul>
	 * <p>
	 */
	double max() default -1; 

}
