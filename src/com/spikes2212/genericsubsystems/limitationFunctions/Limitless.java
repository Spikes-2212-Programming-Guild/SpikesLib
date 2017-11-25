package com.spikes2212.genericsubsystems.limitationFunctions;

import java.util.function.Function;

import com.spikes2212.genericsubsystems.BasicSubsystem;

/**
 * This is a function from double to boolean that always returns true. This is
 * for a {@link BasicSubsystem} with no limits.
 * 
 * @author Omri "Riki" Cohen
 *
 */
public class Limitless implements Function<Double, Boolean> {

	/**
	 * Constructs a limitless Funcation.
	 * 
	 */
	public Limitless() {
	}

	/**
	 * This applies this function to a given double. For every double returns
	 * true.
	 * 
	 * @param t
	 *            the double this function get.
	 * @return always true.
	 */
	@Override
	public Boolean apply(Double t) {
		return true;
	}

}
