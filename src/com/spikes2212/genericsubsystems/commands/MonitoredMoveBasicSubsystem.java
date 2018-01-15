package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command works similarly to {@link MoveBasicSubsystem}, only it scales
 * down the speed according to the current voltage.
 * 
 * <br>
 * <br>
 * The supplier is scaled according to
 * {@link VoltageMonitor#monitorSupplier(Supplier)}, meaning according to the
 * default scaling method and values in {@link VoltageMonitor}.
 *
 * @author Omri "Riki" Cohen and Itamar Rivkind
 * 
 * @see MoveBasicSubsystem
 * @see VoltageMonitor
 */
public class MonitoredMoveBasicSubsystem extends MoveBasicSubsystem {

	/**
	 * This constructs a new {@link MoveBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command runs on and a supplier supplying the
	 * speed the {@link BasicSubsystem} should move with.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed this subsystem
	 *            should be moved with. Must only supply values between -1 and 1.
	 *            This speed is dynamically scaled down according to
	 *            {@link VoltageMonitor#monitorSupplier(Supplier)}.
	 */
	public MonitoredMoveBasicSubsystem(BasicSubsystem basicSubsystem, Supplier<Double> speedSupplier) {
		super(basicSubsystem, VoltageMonitor.monitorSupplier(speedSupplier));
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command runs on and a constant speed the
	 * {@link BasicSubsystem} should move with.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command opperates on.
	 * @param speed
	 *            the speed this subsystem should be moved with. Values must be
	 *            between -1 and 1. This speed is dynamically scaled down according
	 *            to {@link VoltageMonitor#monitorSupplier(Supplier)}.
	 */
	public MonitoredMoveBasicSubsystem(BasicSubsystem basicSubsystem, double speed) {
		super(basicSubsystem, VoltageMonitor.monitorSupplier(() -> speed));
	}
}