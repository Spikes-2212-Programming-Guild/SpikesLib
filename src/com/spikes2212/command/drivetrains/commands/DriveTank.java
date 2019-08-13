package com.spikes2212.command.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.command.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link TankDrivetrain} using speeds supplied to the left and the right sides independently.
 */
public class DriveTank extends Command {

	protected final TankDrivetrain tankDrivetrain;
	protected final Supplier<Double> leftSpeedSuplier;
	protected final Supplier<Double> rightSpeedSuplier;

	/**
	 * This constructs a new {@link DriveTank} command that moves the given
	 * {@link TankDrivetrain} according to constant left side and right side speeds.<br>
	 * Positive values move forwards.
	 *
	 * @param drivetrain
	 *            the tank drivetrain this command operates on.
	 * @param leftSpeed
	 *            the speed to move the left side with.
	 * @param rightSpeed
	 *            the speed to move the right side with.
	 */
	public DriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this(drivetrain, () -> leftSpeed, () -> rightSpeed);
	}

	/**
	 * This constructs a new {@link DriveTank} command that moves the given
	 * {@link TankDrivetrain} acording to speed values from Double
	 * {@link Supplier}s for left and right sides.<br>
	 * Positive values move forwards.
	 *
	 * @param drivetrain
	 *            the drivetrain this command requires and moves.
	 * @param leftSpeedSupplier
	 *            the double {@link Supplier} supplying the speed to move in the
	 *            left side with.
	 * @param rightSpeedSupplier
	 *            the double {@link Supplier} supplying the speed to move in the
	 *            right side with.
	 */
	public DriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier,
			Supplier<Double> rightSpeedSupplier) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.leftSpeedSuplier = leftSpeedSupplier;
		this.rightSpeedSuplier = rightSpeedSupplier;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		tankDrivetrain.tankDrive(leftSpeedSuplier.get(), rightSpeedSuplier.get());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		tankDrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
