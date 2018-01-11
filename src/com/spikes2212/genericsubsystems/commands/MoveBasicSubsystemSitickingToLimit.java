package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

public class MoveBasicSubsystemSitickingToLimit extends MoveBasicSubsystem {

	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, Supplier<Double> speed) {
		super(basicSubsystem, speed);
	}
	
	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, double speed) {
		super(basicSubsystem, speed);
	}

}
