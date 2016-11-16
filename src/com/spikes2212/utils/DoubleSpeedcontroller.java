package com.spikes2212.utils;

import edu.wpi.first.wpilibj.SpeedController;

public class DoubleSpeedcontroller implements SpeedController {

	private SpeedController speedcontrollerRight, speedcontrollerLeft;

	public DoubleSpeedcontroller(SpeedController speedcontrollerRight, SpeedController speedcontrollerLeft) {
		this.speedcontrollerLeft = speedcontrollerLeft;
		this.speedcontrollerRight = speedcontrollerRight;
	}
	
	/**
	 * Sets the speed of the speedControllers using PID output.
	 */
	@Override
	public void pidWrite(double output) {
		this.speedcontrollerLeft.pidWrite(output);
		this.speedcontrollerRight.pidWrite(output);

	}
	
	/**
	 * Get the speed of the right hand SpeedController
	 */
	@Override
	public double get() {
		return this.speedcontrollerRight.get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		speedcontrollerLeft.set(speed, syncGroup);
		speedcontrollerRight.set(speed, syncGroup);

	}
	
	/**
	 * Set the speed of this DoubleSpeedController
	 */
	@Override
	public void set(double speed) {
		speedcontrollerLeft.set(speed);
		speedcontrollerRight.set(speed);
	}
	
	/**
	 * Inverts the speedControllers' positive speed direction
	 */
	@Override
	public void setInverted(boolean isInverted) {
		speedcontrollerLeft.setInverted(isInverted);
		speedcontrollerRight.setInverted(isInverted);

	}
	
	/**
	 * @return whether the speedController is inverted.
	 */
	@Override
	public boolean getInverted() {

		return speedcontrollerLeft.getInverted();
	}

	@Override
	public void disable() {
		speedcontrollerLeft.disable();
		speedcontrollerRight.disable();

	}

	@Override
	public void stopMotor() {
		speedcontrollerLeft.stopMotor();
		speedcontrollerRight.stopMotor();

	}
}
