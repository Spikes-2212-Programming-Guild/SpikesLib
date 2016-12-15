package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TankDrivetrain extends Subsystem {
	public void tankDrive(double speedLeft, double speedRight) {
		setLeft(speedLeft);
		setRight(speedRight);
	}

	public abstract void setLeft(double speedLeft);

	public abstract void setRight(double speedRight);

	public abstract PIDSource getLeftPIDSource();

	public abstract PIDSource getRightPIDSource();

}
