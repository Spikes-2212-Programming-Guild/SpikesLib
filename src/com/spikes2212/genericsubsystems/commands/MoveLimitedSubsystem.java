package com.spikes2212.genericsubsystems.commands;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MoveLimitedSubsystem extends Command {
	private LimitedSubsystem limitedSubsystem;
	private double speed;

	public MoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, double speed) {
		requires(limitedSubsystem);
		this.limitedSubsystem = limitedSubsystem;
		this.speed = speed;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute() {
		limitedSubsystem.tryMove(this.speed);

	}

	@Override
	protected boolean isFinished() {
		return limitedSubsystem.canMove(speed);
	}

	@Override
	protected void end() {
		limitedSubsystem.stop();
	}

	@Override
	protected void interrupted() {
		end();

	}

}
