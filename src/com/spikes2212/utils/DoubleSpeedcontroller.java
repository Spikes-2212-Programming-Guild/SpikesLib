package com.spikes2212.utils;

import edu.wpi.first.wpilibj.SpeedController;

public class DoubleSpeedcontroller implements SpeedController {

	private SpeedController speedcontrollerRight, speedcontrollerLeft;

	public DoubleSpeedcontroller(SpeedController speedcontrollerRight, SpeedController speedcontrollerLeft) {
		this.speedcontrollerLeft = speedcontrollerLeft;
		this.speedcontrollerRight = speedcontrollerRight;
	}

	@Override
	public void pidWrite(double output) {
		this.speedcontrollerLeft.pidWrite(output);
		this.speedcontrollerRight.pidWrite(output);

	}

	@Override
	public double get() {
		return this.speedcontrollerRight.get();
	}


	@Override
	public void set(double speed) {
		speedcontrollerLeft.set(speed);
		speedcontrollerRight.set(speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		speedcontrollerLeft.setInverted(isInverted);
		speedcontrollerRight.setInverted(isInverted);

	}

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
