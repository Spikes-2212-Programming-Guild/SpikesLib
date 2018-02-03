package com.spikes2212.utils;

import edu.wpi.first.wpilibj.PIDController;

public class PIDSettings {
	private double KP, KI, KD;

	/**
	 * @param KP
	 *            the Proportional coefficient of the PID loop of this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop of this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop of this command.
	 */
	public PIDSettings(double KP, double KI, double KD, double tolerance, double waitTime) {
		this.setKP(KP);
		this.setKI(KI);
		this.setKD(KD);
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
	 * 
	 * Sets the Differential coefficient of the PID loop using this settings.
	 *
	 * @param KD
	 *            the new Differential coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */
	public void setKD(double KD) {
		this.KD = KD;
	}
}
