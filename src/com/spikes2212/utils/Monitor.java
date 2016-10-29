package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Monitor {
	private static PowerDistributionPanel pdp;
	private static double high_voltage = 10;
	private static double low_voltage = 7;

	public Monitor() {
		pdp = new PowerDistributionPanel();
	}

	public static void setHigh_voltage(double high_voltage) {
		Monitor.high_voltage = high_voltage;
	}

	public static void setLow_voltage(double low_voltage) {
		Monitor.low_voltage = low_voltage;
	}

	public Supplier<Double> monitorSupplier(Supplier<Double> supplier) {
		double voltage = pdp.getVoltage();
		if (voltage < low_voltage)
			return () -> supplier.get() * 0;
		else if (voltage > high_voltage)
			return () -> supplier.get() * 1;
		else
			return () -> supplier.get() * (voltage - low_voltage) / (high_voltage - low_voltage);
	}

}
