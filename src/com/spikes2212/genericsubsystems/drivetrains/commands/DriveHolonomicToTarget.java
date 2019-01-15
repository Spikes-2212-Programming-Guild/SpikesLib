package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;

public class DriveHolonomicToTarget extends DriveHolonomic {
	protected final Supplier<Boolean> onTarget;

	public DriveHolonomicToTarget(HolonomicDrivetrain drivetrain, double speedY, double speedX,
			Supplier<Boolean> onTarget) {
		this(drivetrain, () -> speedY, () -> speedX, onTarget);
	}

	public DriveHolonomicToTarget(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier, Supplier<Boolean> onTarget) {
		super(drivetrain, speedYSupplier, speedXSupplier);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}
