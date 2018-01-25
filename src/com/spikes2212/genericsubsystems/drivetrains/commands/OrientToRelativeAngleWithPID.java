package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.CompassGyro;
import com.spikes2212.utils.PIDSettings;

public class OrientToRelativeAngleWithPID extends OrientToGlobalAngleWithPID {
	public OrientToRelativeAngleWithPID(TankDrivetrain drivetrain, CompassGyro compassGyro, Supplier<Double> setpointSupplier,
			PIDSettings settings) {
		super(drivetrain, compassGyro, () -> setpointSupplier.get() + compassGyro.pidGet(), settings);
	}

	public OrientToRelativeAngleWithPID(TankDrivetrain drivetrain, CompassGyro compassGyro, double setpoint,
			PIDSettings settings) {
		this(drivetrain, compassGyro, () -> setpoint, settings);
	}
}