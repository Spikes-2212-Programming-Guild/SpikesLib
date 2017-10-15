package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveBasicSubsystem extends Command {

	private BasicSubsystem basicSubsystem;
	protected Supplier<Double> speedSupplier;

	public MoveBasicSubsystem(BasicSubsystem basicSubsystem, Supplier<Double> speedSupplier) {
		this.basicSubsystem = basicSubsystem;
		this.speedSupplier = speedSupplier;
	}

	public MoveBasicSubsystem(BasicSubsystem basicSubsystem, double speed) {
		this(basicSubsystem, () -> speed);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		basicSubsystem.move(speedSupplier.get());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		basicSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
