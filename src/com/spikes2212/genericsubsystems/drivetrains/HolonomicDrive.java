package com.spikes2212.genericsubsystems.drivetrains;

public abstract class HolonomicDrive extends TankDrive {
	public abstract void holonomicDrive(double speedX, double speedY); // x-forward/backward
																// y-left/right
}
