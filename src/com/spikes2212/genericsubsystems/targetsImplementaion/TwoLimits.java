package com.spikes2212.genericsubsystems.targetsImplementaion;

import java.util.function.Function;
import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

/**
 * This is a Function from double to boolean. This is for the constructor a
 * {@link BasicSubsystem} that moves between two limits.
 * 
 * @author Omri "Riki" Cohen
 *
 */
public class TwoLimits implements Function<Double, Boolean> {

	private final Supplier<Boolean> maxLimit, minLimit;

	/**
	 * Constructs a two limits function using 2 boolean suppliers.
	 * 
	 * @param maxLimit
	 *            the upper limit, positive speed makes the
	 *            {@link BasicSubsystem} move towards this limit.
	 * @param minLimit
	 *            the lower limit, negative speed makes the
	 *            {@link BasicSubsystem} move towards this limit.
	 */
	public TwoLimits(Supplier<Boolean> maxLimit, Supplier<Boolean> minLimit) {
		this.maxLimit = maxLimit;
		this.minLimit = minLimit;
	}

	/**
	 * This method checks if the subsystem can move. It gets a speed and checks if the max limit is pressed and if a positive
	 * speed is given or if the min limit is pressed and if a negative speed is
	 * given.
	 * 
	 * @param speed
	 *            The speed the {@link BasicSubsystem} tries to move at.
	 * @return true if the subsystem does not try to move out of the limits.
	 * 
	 */
	@Override
	public Boolean apply(Double speed) {
		if (speed > 0 && maxLimit.get())
			return false;
		if (speed < 0 && minLimit.get())
			return false;
		return true;
	}

}
