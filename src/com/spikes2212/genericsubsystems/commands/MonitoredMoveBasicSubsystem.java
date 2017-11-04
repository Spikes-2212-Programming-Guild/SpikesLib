package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command work similarly to {@link MoveBasicSubsystem}, only it scales
 * down the speed given to the {@link BasicSubsystem} according to the current
 * voltage.
 * <p>
 * The supplier is scaled according to
 * {@link VoltageMonitor#monitorSupplier(Supplier)}, meaning according to the
 * default scaling method and values in {@link VoltageMonitor}.
 * </p>
 *
 * @author Omri "Riki" Cohen and Itamar Rivkind
 * @see MoveBasicSubsystem
 * @see VoltageMonitor
 */
public class MonitoredMoveBasicSubsystem extends MoveBasicSubsystem {
	
	/**
	 * {@inheritDoc}
	 *
	 * @param basicSubsystem
	 *            {@inheritDoc}
	 * @param speedSupplier
	 *            {@inheritDoc} Values from this supplier are scaled using
	 *            {@link VoltageMonitor#monitorSupplier(Supplier)}.
	 */
	public MonitoredMoveBasicSubsystem(BasicSubsystem basicSubsystem, Supplier<Double> speedSupplier) {
		super(basicSubsystem, VoltageMonitor.monitorSupplier(speedSupplier));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param basicSubsystem
	 *            {@inheritDoc}
	 * @param speed
	 *            {@inheritDoc} This speed is dynamically scaled down according
	 *            to {@link VoltageMonitor#monitorSupplier(Supplier)}.
	 */
	public MonitoredMoveBasicSubsystem(BasicSubsystem basicSubsystem, double speed) {
		super(basicSubsystem, VoltageMonitor.monitorSupplier(() -> speed));
	}
}