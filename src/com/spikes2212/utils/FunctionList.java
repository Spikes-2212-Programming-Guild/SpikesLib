package com.spikes2212.utils;

import java.util.function.Function;
import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

/**
 * This is the functions that {@link BasicSubsystem} uses
 * 
 * @author Omri "Riki" Cohen
 *
 */

public class FunctionList {

	/**
	 * This function is used for a {@link BasicSubsystem} with 2 limits and
	 * checks if it can move the given speed.
	 *
	 * @param speed
	 *            the speed to check if the subsystem can move with.
	 * @return true if the subsystem can move with the given speed, i.e. it
	 *         hasn't reached the relevant limit.
	 */
	public static Function<Double, Boolean> twoLimits(Supplier<Boolean> isMax, Supplier<Boolean> isMin) {
		return (speed) -> (speed > 0 && isMax.get()) || (speed < 0 && isMin.get());
	}

}
