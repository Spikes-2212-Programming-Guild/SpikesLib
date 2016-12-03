package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MonitoredDriveTank extends DriveTank {

	public MonitoredDriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier,
			Supplier<Double> rightSpeedSupplier) {
		super (drivetrain,VoltageMonitor.monitorSupplier(leftSpeedSupplier),VoltageMonitor.monitorSupplier(rightSpeedSupplier));
	}

}