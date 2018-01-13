package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class VoltageMonitor {
    private static PowerDistributionPanel pdp = new PowerDistributionPanel();
    private static double defaultHighVoltage = 10;
    private static double defaultLowVoltage = 7;

    public enum Priority {
        LOW(1), HIGH(-1), DEFAULT(0);
        private double volageDifference;

        private Priority(double voltageDifference) {
            this.volageDifference = voltageDifference;
        }
    }

    /**
     * setting the value of the voltage to be considered "high" if higher than current low voltage
     *
     * @param highVoltage the high voltage value to be set
     */
    public static void setHighVoltage(double highVoltage) {
        if (highVoltage > defaultLowVoltage)
            VoltageMonitor.defaultHighVoltage = highVoltage;
    }

    /**
     * setting the value of the voltage to be considered "low"
     *
     * @param lowVoltage the low voltage value to be set
     */
    public static void setLowVoltage(double lowVoltage) {
        if (defaultHighVoltage > lowVoltage)
            VoltageMonitor.defaultLowVoltage = lowVoltage;
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
     * @param supplier    the original target supplier
     * @param highVoltage the voltage considered HIGH
     * @param lowVoltage  the Voltage considered LOW
     * @return a supplier of values to set, changing according to the voltage on the system
     * @see Supplier
     */
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

    /**
     * @param supplier the original target supplier
     * @param priority the priority of the system
     * @return a supplier of values to set, changing according to the voltage on the system
     */
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

    /**
     * @param supplier the original target supplier
     * @return a supplier of values to set, changing according to the voltage on the system
     */
    public static Supplier<Double> monitorSupplier(Supplier<Double> supplier) {
        return monitorSupplier(supplier, Priority.DEFAULT);
    }


}
