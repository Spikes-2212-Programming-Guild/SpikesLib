package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

public class MonitoredMoveLimitedSubsystem extends Command {
	private LimitedSubsystem limitedSubsystem;
	private Supplier<Double> speedSupplier;

	public MonitoredMoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, double speed) {
		this(limitedSubsystem, () -> speed);
	}

	public MonitoredMoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, Supplier<Double> speedSupplier) {
		requires(limitedSubsystem);
		this.limitedSubsystem = limitedSubsystem;
		this.speedSupplier = VoltageMonitor.monitorSupplier(speedSupplier);
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
		return limitedSubsystem.canMove(speedSupplier.get());
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
