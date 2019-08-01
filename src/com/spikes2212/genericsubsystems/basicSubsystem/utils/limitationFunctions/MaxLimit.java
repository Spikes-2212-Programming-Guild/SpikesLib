package com.spikes2212.genericsubsystems.basicSubsystem.utils.limitationFunctions;

import java.util.function.Predicate;
import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.GenericSubsystem;

/**
 * This is a {@link Predicate<Double>}. An instance of this class can be used as
 * the canMove condition in the constructor of a {@link GenericSubsystem} that can
 * move forward until reaching a limit and backwards endlessly.
 * 
 * @author Tuval
 *
 * @see Predicate
 */
public class MaxLimit implements Predicate<Double> {

	private final Supplier<Boolean> limit;

	/**
	 * Constructs a max limit Predicate using a boolean suppliers.
	 * 
	 * @param limit
	 *            The limit, positive speed makes the {@link GenericSubsystem} move
	 *            towards this limit.
	 */
	public MaxLimit(Supplier<Boolean> limit) {
		this.limit = limit;
	}

	/**
	 * This method checks if the genericSubsystem can move.<br>
	 * When given a positive speed - it checks if the max limit is reached.
	 * 
	 * @param speed
	 *            The speed the {@link GenericSubsystem} tries to move at.
	 * @return True if the subsystem does not try to move out of the limit.
	 * 
	 */
	@Override
	public boolean test(Double speed) {
		if (limit.get() && speed > 0)
			return false;
		return true;
	}
}
