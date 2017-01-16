package com.spikes2212.genericsubsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * a system that have two ends, and have to move only between those two.
 *the limits o each edge may be caused by the same source.
 *examples may be a moving system between two limits, or even a system working between two distances with distance sensor
 */
public abstract class LimitedSubsystem extends Subsystem {
	protected SpeedController motor;

	/**
	 *
	 * @param motor  the controller for the system
	 */
	public LimitedSubsystem(SpeedController motor) {
		this.motor = motor;
	}

	/**
	 * minimum for the code is the edge of the moving range in which you can only give positive values to the motor
	 * @return true if at Min point.
	 */
	public abstract boolean isMin();

	/**
	 * maximum for the code is the edge of the moving range in which you can only give negative values to the motor
	 * @return true if at Max point
	 */
	public abstract boolean isMax();

	/**
	 *
	 * @return the PIS source of the system
	 */
	public abstract PIDSource getPIDSource();

	/**
	 *
	 * @param speed the speed in which you ar going to move
	 * @return true if moving at @speed will not get you out of the range
	 */
	public boolean canMove(double speed) {
		return !(speed < 0 && isMin() || speed > 0 && isMax());
	}

	/**
	 * move in the given speed, if such movement will not get the system out of the moving range
	 * @param speed the speed to move at
	 */
	public void tryMove(double speed) {
		if (canMove(speed)) {
			motor.set(speed);
		}
	}

	/**
	 * stop the system
	 */
	public void stop() {
		motor.set(0);
	}

	@Override
	protected void initDefaultCommand() {

	}
}
