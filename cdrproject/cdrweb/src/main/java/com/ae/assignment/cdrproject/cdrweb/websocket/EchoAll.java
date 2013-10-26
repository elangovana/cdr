
package com.ae.assignment.cdrproject.cdrweb.websocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.lf5.LogLevel;

import com.ae.assignment.cdrproject.cdrservice.ServiceRuleConfigDroppedCallsPromoImpl;

@ServerEndpoint("/websocket/echoall")
public class EchoAll {
	
	private final static Logger LOGGER = Logger
			.getLogger(ServiceRuleConfigDroppedCallsPromoImpl.class.getName());
	
   @OnMessage
   public void onMessage(Session session, String msg) {
      try {
         for (Session sess : session.getOpenSessions()) {
            if (sess.isOpen())
               sess.getBasicRemote().sendText(msg);
         }
      } catch (IOException e) {
    	  
    	  LOGGER.log(Level.WARNING, e.toString());
      }
   }
}