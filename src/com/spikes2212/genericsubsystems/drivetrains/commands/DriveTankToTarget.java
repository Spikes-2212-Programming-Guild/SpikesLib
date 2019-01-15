package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

/**
 *
 */
public class DriveTankToTarget extends DriveTank {
	protected final Supplier<Boolean> onTarget;
	
	public DriveTankToTarget(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed, Supplier<Boolean> onTarget) {
		super(drivetrain, leftSpeed, rightSpeed);
		this.onTarget = onTarget;
	}

	public DriveTankToTarget(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier,
			Supplier<Double> rightSpeedSupplier, Supplier<Boolean> onTarget) {
		super(drivetrain, leftSpeedSupplier, rightSpeedSupplier);
		this.onTarget = onTarget;
	}

    protected boolean isFinished() {
        return onTarget.get() || super.isFinished();
    }
}