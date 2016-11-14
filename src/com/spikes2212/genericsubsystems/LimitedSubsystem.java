package com.spikes2212.genericsubsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LimitedSubsystem extends Subsystem {
	private SpeedController motor;

	public LimitedSubsystem(SpeedController motor) {
		this.motor = motor;
	}

	/**
	 * checks whether the subsystem reached it's minimum limit
	 * 
	 * @return true if the subsystem is in the low limit and false otherwise
	 */
	public abstract boolean isMin();

	/**
	 * checks whether the subsystem reached it's maximum limit
	 * 
	 * @return true if the subsystem is in the high limit and false otherwise
	 */
	public abstract boolean isMax();

	/**
	 * Checks whether the subsystem can move at a certain speed
	 * 
	 * @param speed
	 *            represents the speed which is checked
	 * @return true if the subsystem can move at the chosen speed
	 */
	public boolean canMove(double speed) {
		return !(speed < 0 && isMin() || speed > 0 && isMax());
	}

	/**
	 * Try to move this subsystem at a certain speed
	 * 
	 * @param speed
	 *            represents the speed at which we should try and turn the motor
	 */
	public void tryMove(double speed) {
		if (canMove(speed)) {
			motor.set(speed);
		}
	}

	/**
	 * stops the subsystem
	 */
	public void stop() {
		motor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
