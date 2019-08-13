package com.spikes2212.command.genericsubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.command.genericsubsystem.GenericSubsystem;
import com.spikes2212.utils.targets.NumericalTarget;

/**
 * This class moves a {@link GenericSubsystem} until reaching a target. For
 * example, a light sensor, a limit switch or a specific encoder value (using
 * {@link NumericalTarget}).
 * 
 * @author Omri "Riki" Cohen
 */
public class MoveGenericSubsystemToTarget extends MoveGenericSubsystem {
	protected final Supplier<Boolean> onTarget;
	

	/**
	 * Constructs a new {@link MoveGenericSubsystemToTarget} command using the
	 * {@link GenericSubsystem} this command runs on, the supplier supplying the speed
	 * the {@link GenericSubsystem} should be moved with and a boolean supplier, which
	 * returns true then reaching the wanted target.
	 * 
	 * @param subsystem
	 *            the {@link GenericSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed subsystem should be
	 *            moved with. Should only supply values between -1 and 1.
	 * @param onTarget
	 *            a Boolean {@link Supplier} returning true when reaching the wanted
	 *            target.
	 */
	public MoveGenericSubsystemToTarget(GenericSubsystem subsystem, Supplier<Double> speedSupplier,
	                                    Supplier<Boolean> onTarget) {
		super(subsystem, speedSupplier);
		this.onTarget = onTarget;
	}

	/**
	 * Constructs a new {@link MoveGenericSubsystemToTarget} command using the
	 * {@link GenericSubsystem} this command runs on, the supplier supplying the speed
	 * the {@link GenericSubsystem} should be moved with and a boolean supplier, which
	 * returns true then reaching the wanted target.
	 * 
	 * @param subsystem
	 *            the {@link GenericSubsystem} this command should move.
	 * @param speed
	 *            the speed subsystem should be moved with. Should only supply
	 *            values between -1 and 1.
	 * @param onTarget
	 *            a Boolean {@link Supplier} returning true when reaching the wanted
	 *            target.
	 */
	public MoveGenericSubsystemToTarget(GenericSubsystem subsystem, double speed, Supplier<Boolean> onTarget) {
		super(subsystem, () -> speed);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}