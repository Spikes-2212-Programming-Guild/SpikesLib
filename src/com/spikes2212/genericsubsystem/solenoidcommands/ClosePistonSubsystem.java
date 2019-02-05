package com.spikes2212.genericsubsystem.solenoidcommands;


import com.spikes2212.utils.Piston;

import edu.wpi.first.wpilibj.command.Command;

public class ClosePistonSubsystem extends Command {

	private Piston piston;

	public ClosePistonSubsystem(Piston piston) {
		this.piston = piston;
	}
	

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		piston.close();
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		piston.off();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
	
	
}
