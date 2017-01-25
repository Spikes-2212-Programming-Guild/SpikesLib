package com.spikes2212.genericsubsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LimitedSubsystem extends Subsystem {

	public abstract boolean isMin();

	public abstract boolean isMax();
	
	public abstract PIDSource getPIDSource();
	
	protected abstract void move(double speed);

	public boolean canMove(double speed) {
		return !(speed < 0 && isMin() || speed > 0 && isMax());
	}

	public void tryMove(double speed) {
		if (canMove(speed)) {
			move(speed);
		}
	}

	public void stop() {
		move(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
