package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

import com.spikes2212.utils.PIDSettings;

/**
 * This command moves a {@link LimitedSubsystem} using wpilib's
 * {@link PIDController}. It also waits a specified amount of time after the
 * error is within the given tolerance before stopping the PID Loop to make sure
 * the subsystem doesn't go past and remain beyond the setpoint.
 *
 * @author Omri "Riki" Cohen and Itamar
 * @see LimitedSubsystem
 * @see PIDController
 * @see PIDSettings
 */

public class MoveLimitedSubsystemWithPID extends MoveBasicSubsystemWithPID {

	private LimitedSubsystem limitedSubsystem;

	/**
	 * This constructs a new {@link MoveLimitedSubsystemWithPID} using a
	 * {@link PIDSource}, a setpoint, the PID coefficients this command's PID
	 * loop should have, and the tolerance for error.
	 *
	 * @param limitedSubsystem
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param source
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop.
	 * @param setpoint
	 *            a supplier supplying the target point of this command.
	 *            <p>
	 *            This command will try to move limitedSubsystem until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            supply values using the same units as source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see PIDController
	 */

	public MoveLimitedSubsystemWithPID(LimitedSubsystem limitedSubsystem, PIDSource source, Supplier<Double> setpoint,
			PIDSettings PIDSettings) {
		super(limitedSubsystem, source, setpoint, PIDSettings);
		this.limitedSubsystem = limitedSubsystem;
	}

	/**
	 * This constructs a new {@link MoveLimitedSubsystemWithPID} using a
	 * {@link PIDSource}, a setpoint, the PID coefficients this command's PID
	 * loop should have, and the tolerance for error.
	 *
	 * @param limitedSubsystem
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param source
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop.
	 * @param setpoint
	 *            the target point of this command.
	 *            <p>
	 *            This command will try to move basicSubsystem until it reaches
	 *            the setpoint. setpoint should be using the same units as
	 *            source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see PIDController
	 */

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
