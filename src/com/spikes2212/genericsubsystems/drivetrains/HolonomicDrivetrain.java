package com.spikes2212.genericsubsystems.drivetrains;

public abstract class HolonomicDrivetrain extends TankDrivetrain {

	/**
	 * @param speedY
	 *            is the speed meant for the backward and forward movement of
	 *            the drive system
	 * @param speedX
	 *            is the speed meant for the right and left movement of the
	 *            drive system
	 */
	public abstract void holonomicDrive(double speedY, double speedX); // y-forward/backward
	// x-left/right

}
