package com.spikes2212.utils;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

import java.util.function.Supplier;

/**
 * This class is ment to decrease the posibility of power drops while running by
 * decreasing the engine power according the the current voltage.<br>
 * Voltage is messured in Volts.
 * 
 * @author Noam "Mantin" Mantin
 * 
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PowerDistributionPanel.html">PowerDistributionPanel</a>
 */
public class VoltageMonitor {
	private static PowerDistributionPanel pdp = new PowerDistributionPanel();
	private static double defaultHighVoltage = 10;
	private static double defaultLowVoltage = 7;

	/**
	 * Priority decreases the low and high limits of the monitored voltage if the
	 * priority is HIGH to ensure the command works longer. LOW does the opposite.
	 * 
	 * @author Omri "Riki" Cohen
	 * 
	 * @see Enum
	 */
	public enum Priority {
		LOW(1), HIGH(-1), DEFAULT(0);
		private double volageDifference;

		private Priority(double voltageDifference) {
			this.volageDifference = voltageDifference;
		}
	}

	/**
	 * Setting the maximum voltage the subsystem is allowed to move in. If maximum
	 * valtage is set lower then minimum valtage, does nothing.
	 *
	 * @param highVoltage
	 *            the high voltage value
	 */
	public static void setHighVoltage(double highVoltage) {
		if (highVoltage > defaultLowVoltage)
			defaultHighVoltage = highVoltage;
	}

	/**
	 * Setting the minimum voltage the subsystem is allowed to move in. If minimum
	 * valtage is set higher then maximum valtage, does nothing.
	 *
	 * @param lowVoltage
	 *            the low voltage value
	 */
	public static void setLowVoltage(double lowVoltage) {
		if (defaultHighVoltage > lowVoltage)
			defaultLowVoltage = lowVoltage;
	}

	/**
	 * @return the voltage considered HIGH
	 */
	public static double getHighVoltage() {
		return defaultHighVoltage;
	}

	/**
	 * @return the voltage considered LOW
	 */
	public static double getLowVoltage() {
		return defaultLowVoltage;
	}

	/**
	 * @param supplier
	 *            the original target supplier
	 * @param highVoltage
	 *            the voltage considered HIGH
	 * @param lowVoltage
	 *            the Voltage considered LOW
	 * 
	 * @return a supplier of values to set, changing according to the voltage on the
	 *         system
	 * 
	 * @see Supplier
	 */
	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier, double highVoltage, double lowVoltage) {
		return () -> {
			double voltage = pdp.getVoltage();
			if (voltage < lowVoltage)
				return supplier.get() * 0;
			else if (voltage > highVoltage)
				return supplier.get() * 1;
			else
				return supplier.get() * (voltage - lowVoltage) / (highVoltage - lowVoltage);
		};
	}

	/**
	 * @param supplier
	 *            the original target supplier
	 * @param priority
	 *            the priority of the system
	 * @return a supplier of values to set, changing according to the voltage on the
	 *         system
	 */
	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier, Priority priority) {
		return () -> {
			double voltage = pdp.getVoltage();
			if (voltage < defaultLowVoltage + priority.volageDifference)
				return supplier.get() * 0;
			else if (voltage > defaultHighVoltage + priority.volageDifference)
				return supplier.get() * 1;
			else
				return supplier.get() * (voltage - defaultLowVoltage + priority.volageDifference) / (defaultHighVoltage
						+ priority.volageDifference - defaultLowVoltage + priority.volageDifference);
		};
	}

	/**
	 * @param supplier
	 *            the original target supplier
	 * @return a supplier of values to set, changing according to the voltage on the
	 *         system
	 */
	public static Supplier<Double> monitorSupplier(Supplier<Double> supplier) {
		return monitorSupplier(supplier, Priority.DEFAULT);
	}

}
