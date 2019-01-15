package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.targets.NumericalTarget;

/**
 * This class moves a {@link HolonomicDrivetrain} until reaching a target. For
 * example, a light sensor, a limit switch or a specific encoder value (using
 * {@link NumericalTarget}).
 * 
 * @author Tuval
 *
 */
public class DriveHolonomicToTarget extends DriveHolonomic {
	protected final Supplier<Boolean> onTarget;

	/**
	 * This constructs a new {@link DriveHolonomicToTarget} command that moves the
	 * given {@link HolonomicDrivetrain} to target. Positive values go forward.
	 * 
	 * @param drivetrain
	 *            the holonomic drivetrain this command operates on.
	 * @param moveValue
	 *            the double value of speed that the {@link HolonomicDrivetrain}
	 *            that this is operated on will move by.
	 * @param rotateValue
	 *            the double value of speed that the {@link HolonomicDrivetrain}
	 *            that this is operated on will rotate by.
	 * @param onTarget
	 *            the boolean {@link Supplier} that determines whether the
	 *            {@link HolonomicDrivetrain} that this is operated on is on the
	 *            target. Returns true when reaching the target.
	 */
	public DriveHolonomicToTarget(HolonomicDrivetrain drivetrain, double speedY, double speedX,
			Supplier<Boolean> onTarget) {
		this(drivetrain, () -> speedY, () -> speedX, onTarget);
	}

	/**
	 * This constructs a new {@link DriveHolonomicToTarget} command that moves the
	 * given {@link HolonomicDrivetrain} to target. Positive values go forward.
	 * 
	 * @param drivetrain
	 *            the holonomic drivetrain this command operates on.
	 * @param moveValue
	 *            the double {@link Supplier} value of speed that the
	 *            {@link HolonomicDrivetrain} that this is operated on will move by.
	 * @param rotateValue
	 *            the double {@link Supplier} value of speed that the
	 *            {@link HolonomicDrivetrain} that this is operated on will rotate
	 *            by.
	 * @param onTarget
	 *            the boolean {@link Supplier} that determines whether the
	 *            {@link HolonomicDrivetrain} that this is operated on is on the
	 *            target.
	 */
	public DriveHolonomicToTarget(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier, Supplier<Boolean> onTarget) {
		super(drivetrain, speedYSupplier, speedXSupplier);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}
