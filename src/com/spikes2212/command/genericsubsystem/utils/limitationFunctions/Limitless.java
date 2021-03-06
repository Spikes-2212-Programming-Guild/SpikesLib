package com.spikes2212.command.genericsubsystem.utils.limitationFunctions;

import java.util.function.Predicate;

import com.spikes2212.command.genericsubsystem.GenericSubsystem;

/**
 * This is a {@link Predicate<Double>} that always returns true.An
 * instance of this class can be used as the canMove condition in the
 * constructor of a {@link GenericSubsystem} with no limits.
 * 
 * 
 * @author Omri "Riki" Cohen
 *
 * @see Predicate
 */
public class Limitless implements Predicate<Double> {

	/**
	 * Constructs a limitless Function.
	 * 
	 */
	public Limitless() {
	}

	/**
	 * This method applies this function to a given double. Always returns true.
	 * 
	 * @param speed
	 *            the double this function get.
	 * @return always true.
	 */
	@Override
	public boolean test(Double speed) {
		return true;
	}

}
