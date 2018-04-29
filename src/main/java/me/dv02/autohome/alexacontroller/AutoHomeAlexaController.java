package me.dv02.autohome.alexacontroller;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class AutoHomeAlexaController {

	private static AutoHomeAlexaController instance;

	private AlexaDevice alexa;
	private AlexaSessionHandler sessionHandler;
	private Server server;

	public static void main(String[] args) throws Exception {
		instance = new AutoHomeAlexaController();
		instance.start();
	}

	public AutoHomeAlexaController() {
		this.sessionHandler = new AlexaSessionHandler();

		System.out.println("Calling AutoHomeAlexaController constructor");

		alexa = new AlexaDevice("cd /home/pi/alexa-avs-sample-app/samples/javaclient && sudo mvn exec:exec");
		alexa.start();

		this.server = new Server(808);
		WebSocketHandler wsHandler = new WebSocketHandler() {
			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(AlexaWebsocketHandler.class);
			}
		};
		this.server.setHandler(wsHandler);
	}

	public static AutoHomeAlexaController getInstance() {
		return instance;
	}

	public AlexaSessionHandler getSessionHandler() {
		return sessionHandler;
	}

	public AlexaDevice getAlexa() {
		return alexa;
	}

	public void start() throws Exception {
		this.server.start();
		this.server.join();
	}

}