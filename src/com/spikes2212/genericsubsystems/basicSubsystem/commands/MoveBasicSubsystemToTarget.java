package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;
import com.spikes2212.genericsubsystems.basicSubsystem.utils.targets.NumericalTarget;

/**
 * This class moves a {@link BasicSubsystem} until reaching a
 * target. For example, a light sensor, a limit switch or a specific encoder
 * value (using {@link NumericalTarget}).
 * 
 * @author Omri "Riki" Cohen
 */
public class MoveBasicSubsystemToTarget extends MoveBasicSubsystem {
	protected final Supplier<Boolean> onTarget;

	/**
	 * Constructs a new {@link MoveBasicSubsystemToTarget} command using the
	 * {@link BasicSubsystem} this command runs on, the supplier supplying the speed
	 * the {@link BasicSubsystem} should be moved with and a boolean supplier, which
	 * returns true then reaching the wanted target.
	 * 
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed subsystem
	 *            should be moved with. Should only supply values between -1 and 1.
	 * @param onTarget
	 *            a Boolean {@link Supplier} returning true when reaching the wanted
	 *            target.
	 */
	public MoveBasicSubsystemToTarget(BasicSubsystem basicSubsystem, Supplier<Double> speedSupplier,
			Supplier<Boolean> onTarget) {
		super(basicSubsystem, speedSupplier);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}
