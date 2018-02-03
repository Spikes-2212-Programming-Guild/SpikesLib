
package com.spikes2212.utils;

import edu.wpi.first.wpilibj.PIDController;

/**
 * This class contains subsystem's PID setting which wpilib's
 * {@link PIDController} uses. It also has the tolerance of the error for the
 * PID loop and the time the PID loop will wait while within tolerance of the
 * setpoint before ending.
 * 
 * @author Omri "Riki" Cohen
 * @see PIDController
 * 
 */
public class ExtendedPIDSettings extends PIDSettings {

	private double tolerance, waitTime;

	/**
	 * 
	 * @param KP
	 *            the Proportional coefficient of the PID loop of this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop of this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop of this command.
	 * @param tolerance
	 *            the tolerance for error of this command. See
	 *            {@link #setTolerance(double)}.
	 * @param waitTime
	 *            the time this PID loop will wait while within tolerance of the
	 *            setpoint before ending.
	 */
	public ExtendedPIDSettings(double KP, double KI, double KD, double tolerance, double waitTime) {
		super(KP, KI, KD);
		this.setTolerance(tolerance);
		this.setWaitTime(waitTime);
	}

	/**
	 * Gets the tolerance for error for the PID loop using this settings.
	 * <p>
	 * This tolerance defines when PID loop ends: This PID loop will end after
	 * the difference between the setpoint and the current position is within
	 * the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)} straight.
	 * </p>
	 *
	 * @return The current tolerance. If 0 and the WaitTime is not 0, this PID
	 *         loop will never end unless you cancel it.
	 * @see PIDController#setAbsoluteTolerance(double)
	 */
	public double getTolerance() {
		return tolerance;
	}

	/**
	 * Sets the tolerance for error of this PID loop.
	 * <p>
	 * This tolerance defines when this PID loop ends: This command will end
	 * after the difference between the setpoint and the current position is
	 * within the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)} straight.
	 * </p>
	 *
	 * @param tolerance
	 *            The new tolerance to set. If 0 and the WaitTime is not 0, this
	 *            PID loop will never end unless you cancel it.
	 * @see PIDController#setAbsoluteTolerance(double)
	 */
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	/**
	 * Gets the time this PID loop will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 *
	 * @return the wait time, in seconds.
	 */
	public double getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the time this PID loop will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting. <br/>
	 * If wait time is set to 0, the command won't wait.
	 * </p>
	 *
	 * @param waitTime
	 *            the new wait time, in seconds.
	 */
	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

}
