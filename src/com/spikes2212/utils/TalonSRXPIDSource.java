package com.spikes2212.utils;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TalonSRXPIDSource implements PIDSource {

	/**
	 * This class makes an encoder that is connected to a {@link WPI_TalonSRX} to a
	 * PIDSource.
	 * 
	 * @author Tuval
	 */
	private TalonSRX talon;
	private PIDSourceType type;
	private int countsPerRevolution;

	/**
	 * Constructs the PIDSource using the talon and the number of counts per
	 * revolution of the motor.
	 * 
	 * @param talon
	 *            The talon the encoder is connected to.
	 * @param countsPerRevolution
	 *            Counts per revolution of the motor. Can be used to change the
	 *            scale of the value of the encoder.
	 */
	public TalonSRXPIDSource(TalonSRX talon, int countsPerRevolution) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		talon.setSensorPhase(true);

		this.talon = talon;
		this.countsPerRevolution = countsPerRevolution;
	}

	/**
	 * Constructs the PIDSource using the talon. The source will return the raw
	 * value of the encoder.
	 * 
	 * @param talon
	 *            The talon the encoder is connected to.
	 */
	public TalonSRXPIDSource(TalonSRX talon) {
		this(talon, 1);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		if (type.equals(PIDSourceType.kRate))
			return talon.getSelectedSensorVelocity() / countsPerRevolution;
		return talon.getSelectedSensorPosition() / countsPerRevolution;
	}

}
