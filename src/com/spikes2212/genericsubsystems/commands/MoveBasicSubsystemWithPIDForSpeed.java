package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

public class MoveBasicSubsystemWithPIDForSpeed extends MoveBasicSubsystemWithPID {

	private double acceleration;

	public MoveBasicSubsystemWithPIDForSpeed(BasicSubsystem basicSubsystem, PIDSource source, Supplier<Double> setpoint,
			com.spikes2212.utils.PIDSettings PIDSettings, double acceleration) {
		super(basicSubsystem, source, setpoint, PIDSettings);
		this.acceleration = acceleration;
	}

	public MoveBasicSubsystemWithPIDForSpeed(BasicSubsystem basicSubsystem, PIDSource source, double setpoint,
			com.spikes2212.utils.PIDSettings PIDSettings, double acceleration) {
		this(basicSubsystem, source, () -> setpoint, PIDSettings, acceleration);
	}

	@Override
	protected void initialize() {
		movmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				(additionalSpeed) -> basicSubsystem.addSpeed(additionalSpeed * acceleration));
		movmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movmentControl.setSetpoint(this.setpoint.get());
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
