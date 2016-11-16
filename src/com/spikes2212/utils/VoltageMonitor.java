package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class VoltageMonitor {
	private static PowerDistributionPanel pdp;
	private static double highVoltage = 10;
	private static double lowVoltage = 7;

	public VoltageMonitor() {
		pdp = new PowerDistributionPanel();
	}
	
	/**
	 * Sets the highest voltage which will be considered acceptable in order to operate the robot.
	 * 
	 * @param highVoltage the new highest voltage reachable
	 */
	public static void setHigh_voltage(double highVoltage) {
		if (highVoltage > lowVoltage)
			VoltageMonitor.highVoltage = highVoltage;
	}
	
	/**
	 * Sets the lowest voltage which will be considered acceptable in order to operate the robot.
	 * 
	 * @param highVoltage the new highest voltage reachable
	 */
	public static void setLow_voltage(double lowVoltage) {
		if (highVoltage > lowVoltage)
			VoltageMonitor.lowVoltage = lowVoltage;
	}
	
	/**
	 * Calculates the speed allowed to be used by the caller of this method, according to the pdp's voltage extreme points.
	 * 
	 * @param supplier the speed supplier meant to be checked by 
	 * @return a supplier that supplies speed according to the robot's voltage levels
	 */
	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier) {
		double voltage = pdp.getVoltage();
		if (voltage < lowVoltage)
			return () -> supplier.get() * 0;
		else if (voltage > highVoltage)
			return () -> supplier.get() * 1;
		else
			return () -> supplier.get() * (voltage - lowVoltage) / (highVoltage - lowVoltage);
	}

}
