package com.spikes2212.genericsubsystems.limitationFunctions;

import java.util.function.Supplier;

public class NumericalTarget implements Supplier<Boolean> {

	protected final Supplier<Double> position;
	private double targetPosition, tolerance;

	public NumericalTarget(Supplier<Double> position, double targetPosition, double tolerance) {
		this.position = position;
		this.targetPosition = targetPosition;
		this.tolerance = tolerance;
	}

	@Override
	public Boolean get() {
		return (Math.abs(targetPosition - position.get()) <= tolerance);
	}

}
