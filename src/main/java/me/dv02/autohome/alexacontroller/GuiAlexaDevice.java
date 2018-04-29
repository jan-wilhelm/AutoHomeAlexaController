/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.dv02.autohome.alexacontroller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author JanBH
 */
public class GuiAlexaDevice {

	private final static int CLICK_MASK = InputEvent.BUTTON1_DOWN_MASK;

	private boolean voiceActivated = false;
	private final Robot robot;
	private final int[] positionToCheck;
	private final int[] secondValidationPosition;
	private final int[] micClickPosition;

	public GuiAlexaDevice(int[] positionToCheck, int[] secondValidationPosition, int[] micClickPosition)
			throws AWTException {
		this.robot = new Robot();
		this.positionToCheck = positionToCheck;
		this.secondValidationPosition = secondValidationPosition;
		this.micClickPosition = micClickPosition;
		this.renew();
	}

	public GuiAlexaDevice(int[] positionToCheck, int[] micClickPosition) throws AWTException {
		this(positionToCheck, null, micClickPosition);
	}

	private boolean getStatusAtPosition(int[] position) {
		int x = position[0];
		int y = position[1];
		return this.robot.getPixelColor(x, y).getBlue() == 204;
	}

	/**
	 * Get the real status by checking all the positions for their
	 * 
	 * @return
	 */
	private boolean getRealStatus() {
		if (this.getStatusAtPosition(this.positionToCheck)) {
			if (this.secondValidationPosition != null && this.getStatusAtPosition(this.secondValidationPosition)) {
				return false;
			}
			return true;
		}

		return false;
	}

	public final void renew() {
		this.voiceActivated = this.getRealStatus();
	}

	public boolean isActivated(boolean renew) {
		if (renew) {
			this.renew();
		}
		return this.voiceActivated;
	}

	public boolean isActivated() {
		return this.isActivated(false);
	}

	public void click() {
		this.robot.mouseMove(this.micClickPosition[0], this.micClickPosition[1]);
		this.robot.mousePress(CLICK_MASK);
		this.robot.mouseRelease(CLICK_MASK);
	}

}
