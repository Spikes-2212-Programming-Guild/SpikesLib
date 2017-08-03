package com.spikes2212.utils;

public abstract class GeneralOI {
	
	protected abstract void initDriverJoystick();
	protected abstract void initNavigatorController();
	
	public abstract double getRotation();
	public abstract double getForwardRight();
	public abstract double getForwardLeft();

}
