package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command work similarly to {@link MoveLimitedSubsystem},
 * only it scales down the speed given to the {@link LimitedSubsystem} according to the current voltage.
 * <p>
 * The supplier is scaled according to {@link VoltageMonitor#monitorSupplier(Supplier)},
 * meaning according to the default scaling method and values in {@link VoltageMonitor}.
 * </p>
 *
 * @author Omri "Riki" Cohen
 * @see MoveLimitedSubsystem
 * @see VoltageMonitor
 */
public class MonitoredMoveLimitedSubsystem extends MoveLimitedSubsystem {

    /**
     * {@inheritDoc}
     *
     * @param limitedSubsystem {@inheritDoc}
     * @param speedSupplier    {@inheritDoc} Values from this supplier are scaled using {@link VoltageMonitor#monitorSupplier(Supplier)}
     */
    public MonitoredMoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, Supplier<Double> speedSupplier) {
        super(limitedSubsystem, VoltageMonitor.monitorSupplier(speedSupplier));
    }

    /**
     * {@inheritDoc}
     *
     * @param limitedSubsystem {@inheritDoc}
     * @param speed            {@inheritDoc} This speed is dynamically scaled down according to {@link VoltageMonitor#monitorSupplier(Supplier)}
     */
    public MonitoredMoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, double speed) {
        super(limitedSubsystem, VoltageMonitor.monitorSupplier(() -> speed));
    }
}