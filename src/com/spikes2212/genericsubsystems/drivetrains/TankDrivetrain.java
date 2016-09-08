package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TankDrivetrain extends Subsystem {
	public abstract void tankDrive(double spLeft, double spRight);
}
