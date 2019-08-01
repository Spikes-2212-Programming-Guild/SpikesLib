package com.spikes2212.genericsubsystems.basicSubsystem.utils.limitationFunctions;

import java.util.function.Predicate;
import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.GenericSubsystem;

/**
 * This is a {@link Predicate<Double>}. An
 * instance of this class can be used as the canMove condition in the
 * constructor of a  {@link GenericSubsystem} that moves between two given limits.
 * 
 * @author Omri "Riki" Cohen
 *
 * @see Predicate
 */
public class TwoLimits implements Predicate<Double> {

	private final Supplier<Boolean> maxLimit, minLimit;

	/**
	 * Constructs a two limits predicate using 2 boolean suppliers.
	 * 
	 * @param minLimit
	 *            the lower limit, negative speed makes the
	 *            {@link GenericSubsystem} move towards this limit.
	 * @param maxLimit
	 *            the upper limit, positive speed makes the
	 *            {@link GenericSubsystem} move towards this limit.
	 */
	public TwoLimits(Supplier<Boolean> minLimit, Supplier<Boolean> maxLimit) {
		this.maxLimit = maxLimit;
		this.minLimit = minLimit;
	}

	/**
	 * This method checks if the genericSubsystem can move.<br>
	 * When given a positive speed - it checks if the max limit is reached.<br>
	 * When a negative speed is given - it checks if the min limit is reached.
	 * 
	 * @param speed
	 *            The speed the {@link GenericSubsystem} tries to move at.
	 * @return True if the subsystem does not try to move out of the limits.
	 * 
	 */
	@Override
	public boolean test(Double speed) {
		if (speed > 0 && maxLimit.get())
			return false;
		if (speed < 0 && minLimit.get())
			return false;
		return true;
	}

}
