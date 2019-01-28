
package com.spikes2212.utils;

/**
 * This class contains a subsystem's PID setting which wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 * uses. It also has the error tolerance for the PID loop and the time the PID
 * loop will wait while within tolerance of the setpoint before ending.
 * 
 * @author Omri "Riki" Cohen
 * 
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 */
public class PIDSettings {

	private double KP, KI, KD, tolerance, waitTime;

	/**
	 * 
	 * @param KP
	 *            the Proportional coefficient of the PID loop in this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop in this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop in this command.
	 * @param tolerance
	 *            the error tolerance of this command.
	 * @param waitTime
	 *            the time this PID loop will wait while within tolerance of the
	 *            setpoint before ending.
	 * 
	 * @see #setTolerance(double)
	 * @see #setWaitTime(double)
	 */
	public PIDSettings(double KP, double KI, double KD, double tolerance, double waitTime) {
		this.setKP(KP);
		this.setKI(KI);
		this.setKD(KD);
		this.setTolerance(tolerance);
		this.setWaitTime(waitTime);
	}

	/**
	 * Gets the error tolerance for the PID loop using this settings.
	 * 
	 * <br>
	 * <br>
	 * Tolerance defines when PID loop ends: This PID loop will end after the
	 * difference between the setpoint and the current position is within the
	 * tolerance for the amount of time specified by {@link #setWaitTime(double)}.
	 *
	 * @return The current tolerance.
	 */
	public double getTolerance() {
		return tolerance;
	}

	/**
	 * Sets the tolerance for error of this PID loop.
	 * 
	 * <br>
	 * <br>
	 * Tolerance defines when PID loop ends: This PID loop will end after the
	 * difference between the setpoint and the current position is within the
	 * tolerance for the amount of time specified by {@link #setWaitTime(double)}.
	 *
	 * @param tolerance
	 *            the new tolerance to set. 
	 */
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	/**
	 * Gets the Proportional coefficient of the PID loop using these
	 * {@link PIDSettings}.
	 *
	 * @return The current Proportional coefficient.
	 */
	public double getKP() {
		return KP;
	}

	/**
	 * Sets the Proportional coefficient of the PID loop using these
	 * {@link PIDSettings}.
	 *
	 * @param KP
	 *            the new Proportional coefficient.
	 */
	public void setKP(double KP) {
		this.KP = KP;
	}

	/**
	 * Gets the Integral coefficient of the PID loop using these
	 * {@link PIDSettings}.
	 *
	 * @return The current Integral coefficient.
	 */
	public double getKI() {
		return KI;
	}

	/**
	 * Sets the Intergral coefficient of the PID loop using these
	 * {@link PIDSettings}.
	 *
	 * @param KI
	 *            the new Integral coefficient.
	 */
	public void setKI(double KI) {
		this.KI = KI;
	}

	/**
	 * Gets the Differential coefficient of the PID loop using these
	 * {@link PIDSettings}.
	 *
	 * @return The current Differential coefficient.
	 */
	public double getKD() {
		return KD;
	}

	/**
	 * Sets the Differential coefficient of the PID loop using these
	 * {@link PIDSettings}.
	 *
	 * @param KD
	 *            the new Differential coefficient.
	 */
	public void setKD(double KD) {
		this.KD = KD;
	}

	/**
	 * Gets the time this PID loop will wait while within tolerance of the setpoint
	 * before ending.
	 * 
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 *
	 * @return The wait time, in seconds.
	 */
	public double getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the time this PID loop will wait while within tolerance of the setpoint
	 * before ending.
	 * 
	 * <br>
	 * <br>
	 * If wait time is set to 0, the command won't wait.
	 *
	 * @param waitTime
	 *            the new wait time, in seconds
	 */
	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

}
