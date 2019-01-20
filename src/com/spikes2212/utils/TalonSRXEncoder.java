package com.spikes2212.utils;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TalonSRXEncoder implements PIDSource {
	
	private TalonSRX talon;
	private PIDSourceType type;
	private int countsPerRevolution;
	public TalonSRXEncoder(TalonSRX talon, int countsPerRevolution) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		talon.setSensorPhase(true);
		
		this.talon = talon;
		this.countsPerRevolution = countsPerRevolution;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) { type=pidSource; }

	@Override
	public PIDSourceType getPIDSourceType() { return type; }

	@Override
	public double pidGet() {
		if(type.equals(PIDSourceType.kRate)) return talon.getSelectedSensorVelocity() / countsPerRevolution;
		return talon.getSelectedSensorPosition() / countsPerRevolution;
	}

}
