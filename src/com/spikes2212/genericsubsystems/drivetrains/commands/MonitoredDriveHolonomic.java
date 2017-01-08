package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MonitoredDriveHolonomic extends Command {
	private HolonomicDrivetrain holonomicDrivetrain;
	private Supplier<Double> speedYSupplier, speedXSupplier;
	
	/**
	 * Constructs a command which moves the tank according to a constant speeds for its X and Y axises. 
	 * 
	 * @param drivetrain is the drive system meant to move
	 * @param speedY is the speed by which the the Y axis of the drive system will move (forward and backward)
	 * @param speedX is the speed by which the the X axis of the drive system will move (right and left)
	 */
	public MonitoredDriveHolonomic(HolonomicDrivetrain drivetrain, double speedY, double speedX) {
		// Use requires() here to declare subsystem dependencieslier
		// eg. requires(chassis);
		this(drivetrain, () -> speedY, () -> speedX);

	}

	/**
	 * Constructs a command which moves the tank according to values from suppliers for its X and Y axises.
	 * this system monitors the electricity in the robot according to the Electrical voltage.
	 * @param drivetrain is the drive system meant to move
	 * @param speedYSupplier is the supplier meant to supply speed to the Y axis of the drive system (forward and backward)
	 * @param speedXSupplier is the supplier meant to supply speed to the X axis of the drive system (right and left)
	 */
	public MonitoredDriveHolonomic(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.holonomicDrivetrain = drivetrain;
		this.speedXSupplier = VoltageMonitor.monitorSupplier(speedXSupplier);
		this.speedYSupplier = VoltageMonitor.monitorSupplier(speedYSupplier);
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
		holonomicDrivetrain.holonomicDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
