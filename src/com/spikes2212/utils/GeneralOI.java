package com.spikes2212.utils;

/**
 * This class general Operator Interface. Operator Interface is the glue that binds the
 * controls on the physical operator interface to the commands and command
 * groups that allow control of the robot.
 * 
 * @author Omri "Riki" Cohen
 */

public abstract class GeneralOI {

	// sets all commands and buttons connected to joystick driver
	protected abstract void initDriverJoystick();

	// sets all commands and buttons connected to controller navigator
	protected abstract void initNavigatorController();
	
	// constructor
	public GeneralOI() {
		initDriverJoystick();
		initNavigatorController();
	}

	// returns the adjusted value of the Rotate
	// use this to choose between the 2 drive arcade methods
	public abstract double getRotation();

	// returns the adjusted value of the driving right joystick's y
	public abstract double getForwardRight();

	// returns the adjusted value of the driving left joystick's y
	public abstract double getForwardLeft();

	// receives input, returns the adjusted input for better sensitivity
	private double adjustInput(double input) {
		return input * Math.abs(input);
	}
}
