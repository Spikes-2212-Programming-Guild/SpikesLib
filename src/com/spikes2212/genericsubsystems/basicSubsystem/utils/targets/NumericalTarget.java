package com.spikes2212.genericsubsystems.basicSubsystem.utils.targets;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;
import com.spikes2212.genericsubsystems.basicSubsystem.commands.MoveBasicSubsystemToTarget;

/**
 * This class is a Boolean {@link Supplier}. It is used in the constructor of
 * {@link MoveBasicSubsystemToTarget} to move a {@link BasicSubsystem} to a
 * numerical target, for example, an encoder's output.
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
	 * @return whether the position is within target position's tolerance.
	 */
	@Override
	public Boolean get() {
		return (Math.abs(targetPosition - position.get()) <= tolerance);
	}

}