package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

public class DriveArcadeToTarget extends DriveArcade {
	protected final Supplier<Boolean> onTarget;

	public DriveArcadeToTarget(TankDrivetrain drivetrain, double moveValue, double rotateValue,
			Supplier<Boolean> onTarget) {
		this(drivetrain, () -> moveValue, () -> rotateValue, onTarget);
	}

	public DriveArcadeToTarget(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
			Supplier<Double> rotateValueSupplier, Supplier<Boolean> onTarget) {
		super(drivetrain, moveValueSupplier, rotateValueSupplier);
		this.onTarget = onTarget;
	}

	protected boolean isFinished() {
		return onTarget.get() || super.isFinished();
	}
}
