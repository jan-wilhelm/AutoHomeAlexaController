/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.dv02.autohome.alexacontroller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 *
 * @author JanBH
 */

@WebSocket
public class AlexaWebsocketHandler {

	@OnWebSocketConnect
	public void open(Session session) {
		AutoHomeAlexaController.getInstance().getSessionHandler().addSession(session);
	}

	@OnWebSocketClose
	public void close(Session session, int code, String string) {
		AutoHomeAlexaController.getInstance().getSessionHandler().removeSession(session);
	}

	@OnWebSocketError
	public void onError(Throwable error) {
		Logger.getLogger(AlexaWebsocketHandler.class.getName()).log(Level.SEVERE, null, error);
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		System.out.println("Got: " + message);
		AutoHomeAlexaController.getInstance().getAlexa().click();
	}

}
