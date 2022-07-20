package com.sevenorcas.blue.system.lifecycle.provider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.json.JSONObject;

/**
 * Passing Json to Rest and receiving it through JSONObject
 * In JAX-RS, you need a JSON MessageBodyReader that will readFrom InputStream and return a JSONObject.
 * 
 * https://stackoverflow.com/questions/33362957/passing-json-to-rest-and-receiving-it-through-jsonobject
 * 
 * [Licence]
 * @author John Stewart
 */


@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JSONObjectMessageBodyReader implements MessageBodyReader<JSONObject> {

	@Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == JSONObject.class;
    }

	@Override
    public JSONObject readFrom(Class<JSONObject> type, Type genericType, Annotation[] annotations, MediaType mediaType,
    		MultivaluedMap<String, String> httpHeaders, InputStream entityStream) 
    				throws java.io.IOException, javax.ws.rs.WebApplicationException {
        //Using entityStream, read the content and return a  JSONObject back...
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(entityStream, "UTF-8")); 
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        return new JSONObject(responseStrBuilder.toString());
    }
}
