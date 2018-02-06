package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link HolonomicDrivetrain} using X and Y axes speeds.
 */
public class DriveHolonomic extends Command {
	protected final HolonomicDrivetrain holonomicDrivetrain;
	protected final Supplier<Double> speedYSupplier, speedXSupplier;

	/**
	 * This constructs a new {@link DriveHolonomic} command that moves the given
	 * {@link HolonomicDrivetrain} according to constant X and Y axes speeds.<br>
	 * Positive values move the drivetrain forward on the corresponding axis.
	 *
	 * @param drivetrain
	 *            the holonomic drivetrain this command operates on.
	 * @param speedY
	 *            the speed to move in the Y axis with.
	 * @param speedX
	 *            the speed to move in the X axis with.
	 */
	public DriveHolonomic(HolonomicDrivetrain drivetrain, double speedY, double speedX) {
		// Use requires() here to declare subsystem dependencies lier
		// eg. requires(chassis);
		this(drivetrain, () -> speedY, () -> speedX);

	}

	/**
	 * This constructs a new {@link DriveHolonomic} command that moves the given
	 * {@link HolonomicDrivetrain} according to speed values from Double {@link Supplier}s
	 * for X and Y axes.<br>
	 * Positive values move the drivetrain forward on the given axis.
	 *
	 * @param drivetrain
	 *            the holonomic drivetrain this command operates on.
	 * @param speedY
	 *            the double {@link Supplier} supplying the speed to move in the Y
	 *            axis with.
	 * @param speedX
	 *            the double {@link Supplier} supplying the speed to move in the X
	 *            axis with.
	 */
	public DriveHolonomic(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.holonomicDrivetrain = drivetrain;
		this.speedXSupplier = speedXSupplier;
		this.speedYSupplier = speedYSupplier;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		holonomicDrivetrain.holonomicDrive(speedYSupplier.get(), speedXSupplier.get());

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		holonomicDrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
