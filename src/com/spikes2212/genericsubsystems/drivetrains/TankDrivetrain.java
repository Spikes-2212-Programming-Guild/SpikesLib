package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TankDrivetrain extends Subsystem {
	/**
	 * makes the drivetrain move with certain speeds
	 * 
	 * @param speedLeft-
	 *            provides a speed for the left gearbox
	 * @param speedRight-
	 *            provides a speed for the right gearbox
	 */
	public abstract void tankDrive(double speedLeft, double speedRight);

}
