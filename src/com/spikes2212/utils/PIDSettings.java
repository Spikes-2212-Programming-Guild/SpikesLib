
package com.spikes2212.utils;

import edu.wpi.first.wpilibj.PIDController;

/**
 * This class contains subsystem's PID setting which wpilib's
 * {@link PIDController} uses. It also has the tolerance of the error for
 * the PID loop and the time the PID loop will wait while within tolerance
 * of the setpoint before ending.
 * 
 * @author Omri "Riki" Cohen
 * @see PIDController

 */

public class PIDSettings {

	private double KP, KI, KD, tolerance, waitTime;

	public PIDSettings(double KP, double KI, double KD, double tolerance, double waitTime) {
		this.setKP(KP);
		this.setKI(KI);
		this.setKD(KD);
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
	 * @return The current tolerance. If 0, this PID loop will never end.
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
	 *            The new tolerance to set. If 0, this PID loop will never end.
	 * @see PIDController#setAbsoluteTolerance(double)
	 */

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	/**
	 * Gets the Proportional coefficient of the PID loop using this settings.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 */

	public double getKP() {
		return KP;
	}

	/**
	 * Sets the Proportional coefficient of the PID loop using this settings.
	 *
	 * @param KP
	 *            the new Proportional coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */

	public void setKP(double KP) {
		this.KP = KP;
	}

	/**
	 * Gets the Integral coefficient of the PID loop using this settings.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 */

	public double getKI() {
		return KI;
	}

	/**
	 * Sets the Integral coefficients of the PID loop using this settings.
	 *
	 * @param KI
	 *            the new Integral coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */

	public void setKI(double KI) {
		this.KI = KI;
	}

	/**
	 * Gets the Differential coefficient of the PID loop using this settings.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 */

	public double getKD() {
		return KD;
	}

	/**
	 * Sets the Differential coefficient of the PID loop using this settings.
	 *
	 * @param KD
	 *            the new Differential coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */

	public void setKD(double KD) {
		this.KD = KD;
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
