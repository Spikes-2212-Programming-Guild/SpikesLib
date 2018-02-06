package com.spikes2212.genericsubsystems.utils.limitationFunctions;

import java.util.function.Function;
import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

/**
 * This is a {@link Function} from Double to Boolean. This is for the
 * constructor of a {@link BasicSubsystem} that moves between two given limits.
 * 
 * @author Omri "Riki" Cohen
 *
 * @see Function
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
	 * This method checks if the subsystem can move.<br>
	 * If given a positive speed - it checks if the max limit is reached.<br>
	 * If a negative speed is given - it checks if the min limit is reached.
	 * 
	 * @param speed
	 *            The speed the {@link BasicSubsystem} tries to move at.
	 * @return True if the subsystem does not try to move out of the limits.
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
