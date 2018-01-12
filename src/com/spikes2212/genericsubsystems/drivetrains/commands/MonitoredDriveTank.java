package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command work similarly to {@link DriveTank},
 * only it scales down the speed given to the {@link TankDrivetrain} according to the current voltage.
 * <p>
 * The supplier is scaled according to {@link VoltageMonitor#monitorSupplier(Supplier)},
 * meaning according to the default scaling method and values in {@link VoltageMonitor}.
 * </p>
 *
 * @author Omri "Riki" Cohen
 * @see DriveTank
 * @see VoltageMonitor
 */
public class MonitoredDriveTank extends DriveTank {


    /**
     * {@inheritDoc}
     *
     * @param leftSpeedSupplier  {@inheritDoc} Values from this supplier are scaled using {@link VoltageMonitor#monitorSupplier(Supplier)}.
     * @param rightSpeedSupplier {@inheritDoc} Values from this supplier are scaled using {@link VoltageMonitor#monitorSupplier(Supplier)}.
     */
    public MonitoredDriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier,
                              Supplier<Double> rightSpeedSupplier) {
        super(drivetrain, VoltageMonitor.monitorSupplier(leftSpeedSupplier), VoltageMonitor.monitorSupplier(rightSpeedSupplier));
    }

    /**
     * {@inheritDoc}
     *
     * @param leftSpeed  {@inheritDoc} This speed is dynamically scaled down according to {@link VoltageMonitor#monitorSupplier(Supplier)}.
     * @param rightSpeed {@inheritDoc} This speed is dynamically scaled down according to {@link VoltageMonitor#monitorSupplier(Supplier)}.
     */
    MonitoredDriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
        super(drivetrain, VoltageMonitor.monitorSupplier(() -> leftSpeed),
                VoltageMonitor.monitorSupplier(() -> rightSpeed));
    }

}