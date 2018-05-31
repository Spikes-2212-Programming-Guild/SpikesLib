package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This command moves a {@link BasicSubsystem}, using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>,
 * with a given speed and not a given voltage. After reaching the wanted speed
 * it sticks to this speed until this command is stoped.
 *
 * @author Omri "Riki"
 * 
 * @see BasicSubsystem
 * @see MoveBasicSubsystemWithPID
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 * @see PIDSettings
 */
public class MoveBasicSubsystemWithPIDForSpeed extends MoveBasicSubsystemWithPID {

	private double accelerationModifier;

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPIDForSpeed} using a
	 * <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>,
	 * a {@link Supplier} supplying the wanted speed, the PID coefficients this command's PID loop should have,
	 * and the tolerance for error.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param wantedSpeed
	 *            a supplier supplying the speed the {@link BasicSubsystem} should
	 *            move at.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @param accelerationModifier
	 *            the increment in which the speed should grow.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveBasicSubsystemWithPIDForSpeed(BasicSubsystem basicSubsystem, PIDSource source,
			Supplier<Double> wantedSpeed, PIDSettings PIDSettings, double accelerationModifier) {
		super(basicSubsystem, source, wantedSpeed, PIDSettings);
		this.accelerationModifier = accelerationModifier;
		this.source.setPIDSourceType(PIDSourceType.kRate);
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPIDForSpeed} using a
	 * <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>,
	 * the wanted speed, the PID coefficients this command's PID loop should have,
	 * and the tolerance for error.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param wantedSpeed
	 *            a supplier supplying the speed the {@link BasicSubsystem} should
	 *            move at.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @param accelerationModifier
	 *            the increment in which the speed should grow.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveBasicSubsystemWithPIDForSpeed(BasicSubsystem basicSubsystem, PIDSource source, double wantedSpeed,
			PIDSettings PIDSettings, double acceleration) {
		this(basicSubsystem, source, () -> wantedSpeed, PIDSettings, acceleration);
	}

	@Override
	protected void initialize() {
		/*
		 * in PID for speed instead of the PIDController changing the speed to get
		 * closer to the wanted location, the PIDController is changing the voltage to
		 * get closer to the wanted speed. It does that by adding to the current voltage
		 * to get to the wanted speed.
		 */
		movmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				(additionalSpeed) -> basicSubsystem
						.move(basicSubsystem.getSpeed() + additionalSpeed * accelerationModifier));
		movmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movmentControl.setSetpoint(this.setpoint.get());
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

	@Override
	protected boolean isFinished() {
		return false; // The subsystem should not stop when reaching the wanted
						// speed.
	}

}