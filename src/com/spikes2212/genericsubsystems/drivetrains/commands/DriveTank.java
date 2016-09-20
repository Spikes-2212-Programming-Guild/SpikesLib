package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTank extends Command {

	private TankDrivetrain tankDrivetrain;
	private Supplier<Double> leftSpeedSup;
	private Supplier<Double> rightSpeedSup;
	
	public DriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.leftSpeedSup =()-> leftSpeed;
		this.rightSpeedSup =()-> rightSpeed;
	}
	
	public DriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSup, Supplier<Double> rightSpeedSup) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.leftSpeedSup = leftSpeedSup;
		this.rightSpeedSup = rightSpeedSup;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		tankDrivetrain.tankDrive(leftSpeedSup.get(), rightSpeedSup.get());
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
