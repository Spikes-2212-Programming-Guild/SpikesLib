package com.spikes2212.genericsubsystems.drivetrain;

public interface HolonomicDrive extends TankDrive {
	public void holonomicDrive(double speedX, double speedY); // x-forward/backward
																// y-left/right
}
