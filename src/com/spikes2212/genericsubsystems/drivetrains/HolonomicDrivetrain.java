package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class HolonomicDrivetrain extends Subsystem {
	public abstract void holonomicDrive(double speedX, double speedY); // x-forward/backward
																// y-left/right
	
}
