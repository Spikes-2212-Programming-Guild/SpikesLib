package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import com.spikes2212.utils.PIDSettings;

/**
 * This command moves a {@link LimitedSubsystem} using wpilib's
 * {@link PIDController}. It also waits a specified amount of time after the
 * error is within the given tolerance before stopping the PID Loop to make sure
 * the subsystem doesn't go past and remain beyond the setpoint.
 *
 * @author Omri "Riki" Cohen
 * @see LimitedSubsystem
 * @see PIDController
 * @see PIDSettings
 */

public class MoveLimitedSubsystemWithPID extends MoveBasicSubsystemWithPID {

	private LimitedSubsystem limitedSubsystem;

	public MoveLimitedSubsystemWithPID(LimitedSubsystem limitedSubsystem, PIDSource source, Supplier<Double> setpoint,
			PIDSettings PIDSettings) {
		super(limitedSubsystem, source, setpoint, PIDSettings);
		this.limitedSubsystem = limitedSubsystem;
	}

	public MoveLimitedSubsystemWithPID(LimitedSubsystem limitedSubsystem, PIDSource source, double setpoint,
			PIDSettings PIDSettings) {
		super(limitedSubsystem, source, setpoint, PIDSettings);
	}

	@Override
	protected void initialize() {
		movmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				limitedSubsystem::tryMove);
		movmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movmentControl.setSetpoint(this.setpoint.get());
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

}
