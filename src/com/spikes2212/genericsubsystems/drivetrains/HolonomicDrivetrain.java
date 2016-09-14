package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class HolonomicDrivetrain extends TankDrivetrain {
	public abstract void holonomicDrive(double speedY, double speedX); // y-forward/backward
																// x-left/right
	
}
