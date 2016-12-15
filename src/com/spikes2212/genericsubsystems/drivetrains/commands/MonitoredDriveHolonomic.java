package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MonitoredDriveHolonomic extends DriveHolonomic {

	public MonitoredDriveHolonomic(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier) {
		super(drivetrain,VoltageMonitor.monitorSupplier(speedYSupplier),VoltageMonitor.monitorSupplier(speedXSupplier));
	}
	public MonitoredDriveHolonomic(HolonomicDrivetrain drivetrain, double speedY, double speedX) {
		super(drivetrain, VoltageMonitor.monitorSupplier(() -> speedY),
				VoltageMonitor.monitorSupplier(() -> speedX));
	}
}