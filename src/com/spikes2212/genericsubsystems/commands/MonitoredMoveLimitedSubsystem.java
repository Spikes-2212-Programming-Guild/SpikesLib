package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

public class MonitoredMoveLimitedSubsystem extends Command {
	private LimitedSubsystem limitedSubsystem;
	private Supplier<Double> speedSupplier;

	/**
	 * build a command that moves a certain limited subsystem in a certain speed
	 * 
	 * @param limitedSubsystem
	 *            -the limited subsystem on which the command activates
	 * @param speed-
	 *            the speed which the subsystem should move in
	 */
	public MonitoredMoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, double speed) {
		this(limitedSubsystem, () -> speed);
	}

	/**
	 * build a command that moves a certain limited subsystem in changing speed
	 * from a supplier
	 * 
	 * @param limitedSubsystem
	 *            -the limited subsystem on which the command activates
	 * @param speedSupplier-
	 *            the supplier which supplies the speed while the command is
	 *            activated
	 */
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
