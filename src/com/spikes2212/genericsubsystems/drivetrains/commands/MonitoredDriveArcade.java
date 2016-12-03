package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;


public class MonitoredDriveArcade extends DriveArcade {


	public MonitoredDriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
			Supplier<Double> rotateValueSupplier) {
		super(drivetrain,VoltageMonitor.monitorSupplier(moveValueSupplier),VoltageMonitor.monitorSupplier(rotateValueSupplier));
	}

}