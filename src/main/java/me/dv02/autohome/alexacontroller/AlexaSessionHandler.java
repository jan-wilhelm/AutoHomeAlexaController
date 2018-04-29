/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.dv02.autohome.alexacontroller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;

/**
 *
 * @author JanBH
 */

public class AlexaSessionHandler {

	private final Set<Session> sessions = new HashSet<Session>();

	public void addSession(Session session) {
		System.out.println("Added new session: " + session.getRemote().getInetSocketAddress().toString());
		this.sessions.add(session);
		sendToSession(session, createStatusMessage(AutoHomeAlexaController.getInstance().getAlexa().isActivated()));
	}

	public int sessionSize() {
		return this.sessions.size();
	}

	public void removeSession(Session session) {
		this.sessions.remove(session);
	}

	public JSONObject createStatusMessage(boolean listening) {
		JSONObject object = new JSONObject();
		object.put("isListening", listening);
		return object;
	}

	public void sendToAllConnectedSessions(final JSONObject message) {
		for (Session session : this.sessions) {
			sendToSession(session, message);
		}
	}

	private void sendToSession(Session session, JSONObject message) {
		try {
			session.getRemote().sendString(message.toString());
		} catch (IOException ex) {
			sessions.remove(session);
			Logger.getLogger("AlexaSessionHandler").log(Level.SEVERE, null, ex);
		}
	}

	public void sendStatusToAll(boolean newStatus) {
		this.sendToAllConnectedSessions(createStatusMessage(newStatus));
	}
}
