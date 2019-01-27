package com.spikes2212.genericsubsystem.solenoidcommands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystem.solenoid.PistonSubsystem;
import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MovePistonSubsystem extends Command {

	private PistonSubsystem pistonsubsystem;
	private Supplier<Integer> speed;

	public MovePistonSubsystem(PistonSubsystem pistonSubsystem, Supplier<Integer> speed) {
		requires(pistonSubsystem);
		this.pistonsubsystem = pistonSubsystem;
		this.speed = speed;
	}
	
	public MovePistonSubsystem(PistonSubsystem pistonSubsystem, int speed) {
		this(pistonSubsystem, () -> speed);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		pistonsubsystem.move(speed.get());
	}

	protected boolean isFinished() {
		return !pistonsubsystem.canMove(speed.get())|| isTimedOut();
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
