package com.spikes2212.genericsubsystems.drivetrains;

public abstract class HolonomicDrivetrain extends TankDrivetrain {
	public abstract void holonomicDrive(double speedY, double speedX); // y-forward/backward
	// x-left/right

}
