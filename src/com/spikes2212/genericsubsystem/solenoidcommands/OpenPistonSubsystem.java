package com.spikes2212.genericsubsystem.solenoidcommands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystem.solenoid.PistonSubsystem;
import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class OpenPistonSubsystem extends Command {

	private PistonSubsystem pistonsubsystem;

	public OpenPistonSubsystem(PistonSubsystem pistonSubsystem) {
		requires(pistonSubsystem);
		this.pistonsubsystem = pistonSubsystem;
	}
	

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		pistonsubsystem.open();
	}
	
	protected boolean isFinished() {
		return !pistonsubsystem.canMove(1)|| isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		pistonsubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
	
	
}
