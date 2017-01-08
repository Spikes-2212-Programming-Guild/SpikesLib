package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MonitoredDriveTank extends Command {

	private TankDrivetrain tankDrivetrain;
	private Supplier<Double> leftSpeedSuplier;
	private Supplier<Double> rightSpeedSuplier;

	/**
	 * Constructs a command which moves the tank according to a constant left speed and a constant right speed.
	 * 
	 * @param drivetrain is the drive system subsystem meant to move
	 * @param leftSpeed is the permanent speed by which the left side of the drive system will move until the command is over
	 * @param rightSpeed is the permanent speed by which the right side of the drive system will move until the command is over
	 */
	public MonitoredDriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this(drivetrain, () -> leftSpeed, () -> rightSpeed);
	}
	
	
	/**
	 * Constructs a command which moves the tank according to suppliers for its left and right sides.
	 * 
	 * @param drivetrain is the drivetrain subsystem meant to move
	 * @param leftSpeedSupplier is the speed supplier which will supply values to the drivetrain's left side.
	 * @param rightSpeedSupplier is the speed supplier which will supply values to the drivetrain's right side.
	 */
	public MonitoredDriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier,
			Supplier<Double> rightSpeedSupplier) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.leftSpeedSuplier = VoltageMonitor.monitorSupplier(leftSpeedSupplier);
		this.rightSpeedSuplier = VoltageMonitor.monitorSupplier(rightSpeedSupplier);
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
		tankDrivetrain.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
