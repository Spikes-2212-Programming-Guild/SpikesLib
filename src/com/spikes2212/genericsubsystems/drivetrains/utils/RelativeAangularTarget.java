package com.spikes2212.genericsubsystems.drivetrains.utils;

import java.util.function.Supplier;

public class RelativeAangularTarget extends GlobalAangularTarget {
	
	private RelativeAangularTarget(Supplier<Double> targetSupplier, Supplier<Double> positionSupplier, double startingPosition) {
		super(() -> targetSupplier.get() + startingPosition, positionSupplier);
	}

	public RelativeAangularTarget(Supplier<Double> targetSupplier, Supplier<Double> positionSupplier) {
		this(targetSupplier, positionSupplier, positionSupplier.get());
	}
	
	public RelativeAangularTarget(double target, Supplier<Double> positionSupplier){
		this(() -> target, positionSupplier);
	}
}
