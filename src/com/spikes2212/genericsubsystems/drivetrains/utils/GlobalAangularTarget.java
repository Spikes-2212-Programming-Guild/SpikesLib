package com.spikes2212.genericsubsystems.drivetrains.utils;

import java.util.function.Supplier;

public class GlobalAangularTarget implements Supplier<Double> {

	final protected Supplier<Double> targetSupplier;
	final protected Supplier<Double> positionSupplier;

	public GlobalAangularTarget(Supplier<Double> targetSupplier, Supplier<Double> positionSupplier) {
		this.targetSupplier = targetSupplier;
		this.positionSupplier = positionSupplier;
	}
	
	public GlobalAangularTarget(double target, Supplier<Double> positionSupplier) {
		this(() -> target, positionSupplier);
	}

	@Override
	public Double get() {
		double setpoint = targetSupplier.get();
		setpoint = setpoint % 360;
		if (Math.abs(setpoint - positionSupplier.get()) > 180)
			setpoint -= 360;
		else if(Math.abs(setpoint - positionSupplier.get()) < -180)
			setpoint += 360;
		return setpoint;
	}

}
