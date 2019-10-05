package com.spikes2212.utils;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * This class makes an encoder that is connected to a {@link WPI_TalonSRX} to a
 * PIDSource.
 *
 * @author Tuval
 */
public class TalonSRXEncoder extends SendableBase implements PIDSource{

	private TalonSRX talon;
	private PIDSourceType type;
	private double distancePerPulse;
	
	/**
	 * Constructs the PIDSource using the talon and the number of counts per
	 * revolution of the motor.
	 *
	 * @param talon
	 *            The talon the encoder is connected to.
	 * @param distancePerPulse
	 *            Counts per revolution of the motor. Can be used to change the
	 *            scale of the value of the encoder.
	 */
	public TalonSRXEncoder(TalonSRX talon, double distancePerPulse) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		talon.setSensorPhase(true);
		
		this.talon = talon;
		this.type = PIDSourceType.kDisplacement;
		this.distancePerPulse = distancePerPulse;
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

	public double getDistancePerPulse() {
		return distancePerPulse;
	}

	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
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
			return talon.getSelectedSensorVelocity() * distancePerPulse;
		return talon.getSelectedSensorPosition() * distancePerPulse;
	}
	
	public void reset() {
		talon.setSelectedSensorPosition(0);
	}
	
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Encoder");
		
		builder.addDoubleProperty("Distance", this::pidGet, null);
		builder.addDoubleProperty("Distance Per Tick", () -> distancePerPulse, null);
	}
}
