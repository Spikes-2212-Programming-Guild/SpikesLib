package com.spikes2212.genericsubsystems.basicSubsystem.utils.limitationFunctions;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MaxLimit implements Predicate<Double>{
	
	private final Supplier<Boolean> limit;
	
	public MaxLimit(Supplier<Boolean> limit) {
		this.limit = limit;
	}
	
	@Override
	public boolean test(Double speed) {
		if(limit.get() && speed > 0) 
			return false;
		return true;
	}
}
