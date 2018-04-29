/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.dv02.autohome.alexacontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author JanBH
 */
public class AlexaDevice extends Thread {

	private volatile boolean voiceActivated = false;
	private final String startCommand;
	private Process process;
	private OutputStream processOut;

	public AlexaDevice(String startCommand) {
		this.startCommand = startCommand;
	}

	@Override
	public void run() {
		try {
			this.process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", this.startCommand });
			this.processOut = this.process.getOutputStream();
			BufferedReader processIn = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
			String gotin;
			while ((gotin = processIn.readLine()) != null) {
				System.out.println("GOTIN: " + gotin);
				final boolean oldStatus = this.voiceActivated;
				final boolean newStatus = gotin.toLowerCase().contains("listening");
				this.voiceActivated = newStatus;
				if (oldStatus != newStatus) {
					AutoHomeAlexaController.getInstance().getSessionHandler().sendStatusToAll(newStatus);
				}
			}
			this.process.waitFor();
			processIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.process.destroy();
		try {
			this.processOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isActivated() {
		return this.voiceActivated;
	}

	public void click() {
		System.out.println("Click was called");
		try {
			this.processOut.write("l\n".getBytes());
			System.out.println("Writing to process out");
			this.processOut.flush();
			System.out.println("Flushed process out");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
