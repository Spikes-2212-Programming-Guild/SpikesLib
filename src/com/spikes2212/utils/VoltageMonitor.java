package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class VoltageMonitor {
	private static PowerDistributionPanel pdp = new PowerDistributionPanel();
	private static double defaultHighVoltage = 10;
	private static double defaultLowVoltage = 7;

	public enum Priority {
		LOW (1), HIGH (-1), DEFAULT (0);
		private double volageDifference;
		private Priority (double voltageDifference){
			this.volageDifference = voltageDifference;
		}
	}

	public static void setHigh_voltage(double highVoltage) {
		if (highVoltage > defaultLowVoltage)
			VoltageMonitor.defaultHighVoltage = highVoltage;
	}

	public static void setLow_voltage(double lowVoltage) {
		if (defaultHighVoltage > lowVoltage)
			VoltageMonitor.defaultLowVoltage = lowVoltage;
	}

	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier,
			double highVoltage, double lowVoltage) {
		return () -> {
			double voltage = pdp.getVoltage();
			if (voltage < lowVoltage)
				return supplier.get() * 0;
			else if (voltage > highVoltage)
				return supplier.get() * 1;
			else
				return supplier.get() * (voltage - lowVoltage)
						/ (highVoltage - lowVoltage);
		};
	}
	
	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier, Priority priority) {
		//return monitorSupplier(supplier, defaultHighVoltage + priority.volageDifference, defaultLowVoltage + priority.volageDifference);
		return () -> {
			double voltage = pdp.getVoltage();
			if (voltage < defaultLowVoltage + priority.volageDifference)
				return supplier.get() * 0;
			else if (voltage > defaultHighVoltage + priority.volageDifference)
				return supplier.get() * 1;
			else
				return supplier.get() * (voltage - defaultLowVoltage + priority.volageDifference)
						/ (defaultHighVoltage + priority.volageDifference - defaultLowVoltage + priority.volageDifference);
		};
	}

	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier) {
		return monitorSupplier(supplier, defaultHighVoltage, defaultLowVoltage);
	}
	
	


}
