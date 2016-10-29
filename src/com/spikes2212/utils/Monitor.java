package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Monitor {
	private static PowerDistributionPanel pdp;
	private static double highVoltage = 10;
	private static double lowVoltage = 7;

	public Monitor() {
		pdp = new PowerDistributionPanel();
	}

	public static void setHigh_voltage(double highVoltage) {
		if (highVoltage > lowVoltage)
			Monitor.highVoltage = highVoltage;
	}

	public static void setLow_voltage(double lowVoltage) {
		if (highVoltage > lowVoltage)
			Monitor.lowVoltage = lowVoltage;
	}

	public Supplier<Double> monitorSupplier(Supplier<Double> supplier) {
		double voltage = pdp.getVoltage();
		if (voltage < lowVoltage)
			return () -> supplier.get() * 0;
		else if (voltage > highVoltage)
			return () -> supplier.get() * 1;
		else
			return () -> supplier.get() * (voltage - lowVoltage) / (highVoltage - lowVoltage);
	}

}
