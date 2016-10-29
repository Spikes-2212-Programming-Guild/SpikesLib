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

	public MonitoredDriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this(drivetrain, () -> leftSpeed, () -> rightSpeed);
	}

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
