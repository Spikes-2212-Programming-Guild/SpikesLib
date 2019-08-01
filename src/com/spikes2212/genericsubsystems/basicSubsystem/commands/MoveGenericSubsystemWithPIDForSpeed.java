package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.GenericSubsystem;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This command moves a {@link GenericSubsystem}, using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>,
 * with a given speed and not a given voltage. After reaching the wanted speed
 * it sticks to this speed until this command is stoped.
 *
 * @author Omri "Riki"
 * 
 * @see GenericSubsystem
 * @see MoveGenericSubsystemWithPID
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 * @see PIDSettings
 */
public class MoveGenericSubsystemWithPIDForSpeed extends MoveGenericSubsystemWithPID {

	/**
	 * This constructs a new {@link MoveGenericSubsystemWithPIDForSpeed} using a
	 * <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>,
	 * a {@link Supplier} supplying the wanted speed, the PID coefficients this command's PID loop should have,
	 * and the tolerance for error.
	 *
	 * @param genericSubsystem
	 *            the {@link GenericSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param wantedSpeed
	 *            a supplier supplying the speed the {@link GenericSubsystem} should
	 *            move at.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveGenericSubsystemWithPIDForSpeed(GenericSubsystem genericSubsystem, PIDSource source,
	                                           Supplier<Double> wantedSpeed, PIDSettings PIDSettings) {
		super(genericSubsystem, source, wantedSpeed, PIDSettings);
		this.source.setPIDSourceType(PIDSourceType.kRate);
	}

	/**
	 * This constructs a new {@link MoveGenericSubsystemWithPIDForSpeed} using a
	 * <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>,
	 * the wanted speed, the PID coefficients this command's PID loop should have,
	 * and the tolerance for error.
	 *
	 * @param genericSubsystem
	 *            the {@link GenericSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param wantedSpeed
	 *            a supplier supplying the speed the {@link GenericSubsystem} should
	 *            move at.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveGenericSubsystemWithPIDForSpeed(GenericSubsystem genericSubsystem, PIDSource source, double wantedSpeed,
	                                           PIDSettings PIDSettings) {
		this(genericSubsystem, source, () -> wantedSpeed, PIDSettings);
	}

	@Override
	protected void initialize() {
		/*
		 * in PID for speed instead of the PIDController changing the speed to get
		 * closer to the wanted location, the PIDController is changing the voltage to
		 * get closer to the wanted speed. It does that by adding to the current voltage
		 * to get to the wanted speed.
		 */
		movementControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				(additionalSpeed) -> genericSubsystem
						.move(genericSubsystem.getSpeed() + additionalSpeed));
		movementControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movementControl.setSetpoint(this.setpoint.get());
		movementControl.setOutputRange(-1, 1);
		movementControl.enable();
	}

	@Override
	protected boolean isFinished() {
		return false; // The subsystem should not stop when reaching the wanted
						// speed.
	}

}