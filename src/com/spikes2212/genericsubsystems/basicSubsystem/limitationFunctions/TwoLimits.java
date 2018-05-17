package com.spikes2212.genericsubsystems.basicSubsystem.limitationFunctions;

import java.util.function.Function;
import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

/**
 * This is a {@link Function} from Double to Boolean. An
 * instance of this class can be used as the canMove condition in the
 * constructor of a  {@link BasicSubsystem} that moves between two given limits.
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
	 * @param minLimit
	 *            the lower limit, negative speed makes the
	 *            {@link BasicSubsystem} move towards this limit.
	 * @param maxLimit
	 *            the upper limit, positive speed makes the
	 *            {@link BasicSubsystem} move towards this limit.
	 */
	public TwoLimits(Supplier<Boolean> minLimit, Supplier<Boolean> maxLimit) {
		this.maxLimit = maxLimit;
		this.minLimit = minLimit;
	}

	/**
	 * This method checks if the basicSubsystem can move.<br>
	 * When given a positive speed - it checks if the max limit is reached.<br>
	 * When a negative speed is given - it checks if the min limit is reached.
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
