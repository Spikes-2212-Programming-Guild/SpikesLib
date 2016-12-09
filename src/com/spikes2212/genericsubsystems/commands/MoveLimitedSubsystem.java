package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MoveLimitedSubsystem extends Command {
	private LimitedSubsystem limitedSubsystem;
	private Supplier<Double> speedSupplier;

	public MoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, double speed) {
		this(limitedSubsystem, () -> speed);
	}

	public MoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, Supplier<Double> speedSupplier) {
		requires(limitedSubsystem);
		this.limitedSubsystem = limitedSubsystem;
		this.speedSupplier = speedSupplier;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute() {
		limitedSubsystem.tryMove(speedSupplier.get());

	}

	@Override
	protected boolean isFinished() {
		return (!limitedSubsystem.canMove(speedSupplier.get())) || isTimedOut();
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
