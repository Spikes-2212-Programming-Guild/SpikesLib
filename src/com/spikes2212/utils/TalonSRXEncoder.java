package com.spikes2212.utils;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class TalonSRXEncoder extends SendableBase implements PIDSource{
	
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
	public TalonSRXEncoder(TalonSRX talon, int countsPerRevolution) {
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
	public TalonSRXEncoder(TalonSRX talon) {
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
	
	public void reset() {
		talon.setSelectedSensorPosition(0);
	}
	
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Encoder");
		
		builder.addDoubleProperty("Distance", this::pidGet, null);
		builder.addDoubleProperty("Distance Per Tick", () -> countsPerRevolution, null);
	}
}
