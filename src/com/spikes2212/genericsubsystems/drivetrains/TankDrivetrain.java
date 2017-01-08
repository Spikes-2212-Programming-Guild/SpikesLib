package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TankDrivetrain extends Subsystem {
	/**
	 * makes the drivetrain move with certain speeds
	 * 
	 * @param speedLeft
	 *            is the speed set to the left side
	 * @param speedRight
	 *            is the speed set to the right side
	 * 
	 *            provides a speed for the right gearbox
	 */
	public abstract void tankDrive(double speedLeft, double speedRight);

}
