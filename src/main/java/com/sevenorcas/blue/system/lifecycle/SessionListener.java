package com.sevenorcas.blue.system.lifecycle;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * When a users session is invalidated (eg timed out) we need to send a message to the client to re-login
 * This Listener is part of that process
 * Note it requires an entry in web.xml
 * 
 * [Licence]
 * @author John Stewart
 */


public class SessionListener implements HttpSessionListener {

   @Override
   public void sessionCreated(HttpSessionEvent sessionEvent) {
System.out.println("SESSION CREATED " + sessionEvent.getSession().getId());
   }

   @Override
   public void sessionDestroyed(HttpSessionEvent sessionEvent) {
System.out.println("SESSION DESTROYED " + sessionEvent.getSession().getId());         
   }
} 
