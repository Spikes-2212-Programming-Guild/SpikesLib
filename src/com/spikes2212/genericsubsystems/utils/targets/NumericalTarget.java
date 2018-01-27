package com.spikes2212.genericsubsystems.utils.targets;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;
import com.spikes2212.genericsubsystems.commands.MoveBasicSubsystemToTarget;

/**
 * This class is a Double {@link Supplier}. It is used in the constructor of
 * {@link MoveBasicSubsystemToTarget} to move a {@link BasicSubsystem} to a
 * numerical target.
 * 
 * @author Omri "Riki" Cohen
 */

public class NumericalTarget implements Supplier<Boolean> {

	protected final Supplier<Double> position;
	private double targetPosition, tolerance;

	/**
	 * Constructs a new {@link NumericalTarget} using a {@link Supplier} of the
	 * {@link BasicSubsystem}'s position, the target's position and the tolerance.
	 * 
	 * @param position
	 *            a {@link Supplier} of the {@link BasicSubsystem}'s position.
	 * @param targetPosition
	 *            the target's position.
	 * @param tolerance
	 *            the tolerance of the position.
	 */
	public NumericalTarget(Supplier<Double> position, double targetPosition, double tolerance) {
		this.position = position;
		this.targetPosition = targetPosition;
		this.tolerance = tolerance;
	}

	/**
	 * Returns if the position is within target position's tolerance.
	 * 
	 * @return if the position is within target position's tolerance.
	 */
	@Override
	public Boolean get() {
		return (Math.abs(targetPosition - position.get()) <= tolerance);
	}

}
