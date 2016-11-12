package com.spikes2212.genericsubsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LimitedSubsystem extends Subsystem {
	private SpeedController motor;

	public LimitedSubsystem(SpeedController motor) {
		this.motor = motor;
	}

	public abstract boolean isMin();

	public abstract boolean isMax();
	
	public PIDSource getPIDSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean canMove(double speed) {
		return !(speed < 0 && isMin() || speed > 0 && isMax());
	}

	public void tryMove(double speed) {
		if (canMove(speed)) {
			motor.set(speed);
		}
	}

	public void stop() {
		motor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
