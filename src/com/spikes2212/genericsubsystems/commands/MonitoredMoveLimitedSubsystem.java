package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

public class MonitoredMoveLimitedSubsystem extends MoveLimitedSubsystem {
	

	public MonitoredMoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, Supplier<Double> speedSupplier) {
		super (limitedSubsystem,VoltageMonitor.monitorSupplier(speedSupplier));
	}

}