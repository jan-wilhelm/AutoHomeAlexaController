package me.dv02.autohome.alexacontroller;
/**
 * import java.awt.AWTException; import java.util.concurrent.Executors; import
 * java.util.concurrent.ScheduledExecutorService; import
 * java.util.concurrent.TimeUnit; import java.util.logging.Level; import
 * java.util.logging.Logger;
 * 
 * import org.eclipse.jetty.server.Server; import
 * org.eclipse.jetty.websocket.server.WebSocketHandler; import
 * org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
 * 
 * public class GuiAutoHomeAlexaController {
 * 
 * private static GuiAutoHomeAlexaController instance;
 * 
 * private final ScheduledExecutorService timer =
 * Executors.newSingleThreadScheduledExecutor(); private AlexaDevice alexa;
 * private AlexaSessionHandler sessionHandler; private Server server;
 * 
 * public static void main(String[] args) throws Exception { instance = new
 * GuiAutoHomeAlexaController(); instance.start(); }
 * 
 * public GuiAutoHomeAlexaController() { this.sessionHandler = new
 * AlexaSessionHandler();
 * 
 * System.out.println("Calling AutoHomeAlexaController constructor");
 * 
 * timer.scheduleAtFixedRate(new Runnable() {
 * 
 * @Override public void run() { final boolean oldStatus =
 *           getAlexa().isActivated(); if (oldStatus !=
 *           getAlexa().isActivated(true)) {
 *           sessionHandler.sendToAllConnectedSessions(sessionHandler.
 *           createStatusMessage(getAlexa())); } } }, 2, 1, TimeUnit.SECONDS);
 * 
 *           // Click button: 285 285 // Leftmost check pos: 15 214 // Rightmost
 *           check pos: 480 214
 * 
 *           final int[] leftPos = new int[] { 15, 214 }; final int[] rightPos =
 *           new int[] { 480, 214 }; final int[] clickPos = new int[] { 285, 285
 *           };
 * 
 *           try { alexa = new AlexaDevice(leftPos, rightPos, clickPos);
 *           this.sessionHandler.createStatusMessage(alexa); } catch
 *           (AWTException e) {
 *           Logger.getLogger(AlexaWebsocketHandler.class.getName()).log(Level.
 *           SEVERE, null, e); System.exit(0); } this.server = new Server(808);
 *           WebSocketHandler wsHandler = new WebSocketHandler() {
 * @Override public void configure(WebSocketServletFactory factory) {
 *           factory.register(AlexaWebsocketHandler.class); } };
 *           this.server.setHandler(wsHandler); }
 * 
 *           public static GuiAutoHomeAlexaController getInstance() { return
 *           instance; }
 * 
 *           public AlexaSessionHandler getSessionHandler() { return
 *           sessionHandler; }
 * 
 *           public AlexaDevice getAlexa() { return alexa; }
 * 
 *           public void start() throws Exception { this.server.start();
 *           this.server.join(); }
 * 
 *           }
 */