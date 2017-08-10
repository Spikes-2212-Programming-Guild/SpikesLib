package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveHolonomic extends Command {
	private HolonomicDrivetrain holonomicDrivetrain;
	private Supplier<Double> speedYSupplier, speedXSupplier, turningSpeedSuppier;

	public DriveHolonomic(HolonomicDrivetrain drivetrain, double speedY, double speedX, double turningSpeed) {
		// Use requires() here to declare subsystem dependencieslier
		// eg. requires(chassis);
		this(drivetrain, () -> speedY, () -> speedX, () -> turningSpeed);

	}

	public DriveHolonomic(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier, Supplier<Double> turningSpeedSuppier) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.holonomicDrivetrain = drivetrain;
		this.speedXSupplier = speedXSupplier;
		this.speedYSupplier = speedYSupplier;
		this.turningSpeedSuppier = turningSpeedSuppier;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		holonomicDrivetrain.holonomicDrive(speedYSupplier.get(), speedXSupplier.get(), turningSpeedSuppier.get());

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		holonomicDrivetrain.holonomicDrive(0, 0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
