package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TankDrive extends Subsystem {
	public abstract void tankDrive(double spLeft, double spRight);
}
