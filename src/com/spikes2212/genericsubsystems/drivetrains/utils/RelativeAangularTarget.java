package com.spikes2212.genericsubsystems.drivetrains.utils;

import java.util.function.Supplier;

public class RelativeAangularTarget extends GlobalAangularTarget {

	public RelativeAangularTarget(Supplier<Double> targetSupplier, Supplier<Double> positionSupplier) {
		super(() -> targetSupplier.get() + positionSupplier.get(), positionSupplier);
	}
	
	public RelativeAangularTarget(double target, Supplier<Double> positionSupplier){
		this(() -> target, positionSupplier);
	}

}
