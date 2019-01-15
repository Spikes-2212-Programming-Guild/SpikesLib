package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

/**
 * This command moves a {@link TankDrivetrain} with an {@link DriveArcade}
 * controls to target.
 * 
 * @author Tuval
 *
 */
public class DriveArcadeToTarget extends DriveArcade {
	protected final Supplier<Boolean> onTarget;

	/**
	 * This constructs a new {@link DriveArcadeToTarget} command that moves the
	 * given {@link TankDrivetrain} to target.
	 * Positive values go forward.
	 * 
	 * @param drivetrain
	 *            the tank drivetrain this command operates on.
	 * @param moveValue
	 *            the double value of speed that the {@link TankDrivetrain} that
	 *            this is operated on will move by.
	 * @param rotateValue
	 *            the double value of speed that the {@link TankDrivetrain} that
	 *            this is operated on will rotate by.
	 * @param onTarget
	 *            the boolean {@link Supplier} that determines whether the
	 *            {@link TankDrivetrain} that this is operated on is on the target.
	 */
	public DriveArcadeToTarget(TankDrivetrain drivetrain, double moveValue, double rotateValue,
			Supplier<Boolean> onTarget) {
		this(drivetrain, () -> moveValue, () -> rotateValue, onTarget);
	}
	/**
	 This constructs a new {@link DriveArcadeToTarget} command that moves the
	 * given {@link TankDrivetrain} to target.
	 * Positive values go forward.
	 * 
	 * @param drivetrain
	 *            the tank drivetrain this command operates on.
	 * @param moveValue
	 *            the double {@link Supplier} value of speed that the {@link TankDrivetrain} that
	 *            this is operated on will move by.
	 * @param rotateValue
	 *            the double {@link Supplier} value of speed that the {@link TankDrivetrain} that
	 *            this is operated on will rotate by.
	 * @param onTarget
	 *            the boolean {@link Supplier} that determines whether the
	 *            {@link TankDrivetrain} that this is operated on is on the target.
	 */
	public DriveArcadeToTarget(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
			Supplier<Double> rotateValueSupplier, Supplier<Boolean> onTarget) {
		super(drivetrain, moveValueSupplier, rotateValueSupplier);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}
