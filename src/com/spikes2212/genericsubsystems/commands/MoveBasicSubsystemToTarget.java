package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

public class MoveBasicSubsystemToTarget extends MoveBasicSubsystem {
	protected final Supplier<Boolean> onTarget;

	public MoveBasicSubsystemToTarget(BasicSubsystem basicSubsystem, Supplier<Double> speedSupplier,
			Supplier<Boolean> onTarget) {
		super(basicSubsystem, speedSupplier);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}
