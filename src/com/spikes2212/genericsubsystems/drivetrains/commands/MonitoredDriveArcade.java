package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command work similarly to {@link DriveArcade},
 * only it scales down the speed given to the {@link TankDrivetrain} according to the current voltage.
 * <p>
 * The supplier is scaled according to {@link VoltageMonitor#monitorSupplier(Supplier)},
 * meaning according to the default scaling method and values in {@link VoltageMonitor}.
 * </p>
 *
 * @author Omri "Riki" Cohen
 * @see DriveArcade
 * @see VoltageMonitor
 */
public class MonitoredDriveArcade extends DriveArcade {


    /**
     * {@inheritDoc}
     *
     * @param moveValueSupplier   {@inheritDoc} Values from this supplier are scaled using {@link VoltageMonitor#monitorSupplier(Supplier)}.
     * @param rotateValueSupplier {@inheritDoc} Values from this supplier are scaled using {@link VoltageMonitor#monitorSupplier(Supplier)}.
     */
    public MonitoredDriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                                Supplier<Double> rotateValueSupplier) {
        super(drivetrain, VoltageMonitor.monitorSupplier(moveValueSupplier),
                VoltageMonitor.monitorSupplier(rotateValueSupplier));
    }

    /**
     * {@inheritDoc}
     *
     * @param moveValue   {@inheritDoc} This speed is dynamically scaled down according to {@link VoltageMonitor#monitorSupplier(Supplier)}.
     * @param rotateValue {@inheritDoc} This speed is dynamically scaled down according to {@link VoltageMonitor#monitorSupplier(Supplier)}.
     */
    public MonitoredDriveArcade(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
        super(drivetrain, VoltageMonitor.monitorSupplier(() -> moveValue),
                VoltageMonitor.monitorSupplier(() -> rotateValue));
    }

}