package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command work similarly to {@link DriveHolonomic}, only it scales down
 * the speed given to the {@link HolonomicDrivetrain} according to the current
 * voltage.
 * 
 * 
 * <br>
 * <br>
 * The supplier is scaled according to
 * {@link VoltageMonitor#monitorSupplier(Supplier)}, meaning according to the
 * default scaling method and values in {@link VoltageMonitor}.
 *
 * @author Omri "Riki" Cohen
 * @see DriveTank
 * @see VoltageMonitor
 */
public class MonitoredDriveHolonomic extends DriveHolonomic {

	/**
	 * This constructs a new {@link DriveHolonomic} command that moves the given
	 * {@link HolonomicDrivetrain} according to speed values from Double
	 * {@link Supplier}s for X and Y axes.<br>
	 * Positive values move the {@link HolonomicDrivetrain} forwards.
	 *
	 * @param drivetrain
	 *            the holonomic drivetrain this command operates on.
	 * @param speedY
	 *            the double {@link Supplier} supplying the speed to move in the
	 *            Y axis with.
	 * @param speedX
	 *            the double {@link Supplier} supplying the speed to move in the
	 *            X axis with.
	 * 
	 * @see VoltageMonitor#monitorSupplier(Supplier)
	 */
	public MonitoredDriveHolonomic(HolonomicDrivetrain drivetrain, Supplier<Double> speedYSupplier,
			Supplier<Double> speedXSupplier) {
		super(drivetrain, VoltageMonitor.monitorSupplier(speedYSupplier),
				VoltageMonitor.monitorSupplier(speedXSupplier));
	}

	/**
	 * This constructs a new {@link DriveHolonomic} command that moves the given
	 * {@link HolonomicDrivetrain} acording to constant X and Y axes speeds.<br>
	 * Positive values move the axis forwards.
	 *
	 * @param drivetrain
	 *            the holonomic drivetrain this command opperates on.
	 * @param speedY
	 *            the speed to move in the Y axis with.
	 * @param speedX
	 *            the speed to move in the X axis with.
	 * 
	 * @see VoltageMonitor#monitorSupplier(Supplier)
	 */
	public MonitoredDriveHolonomic(HolonomicDrivetrain drivetrain, double speedY, double speedX) {
		super(drivetrain, VoltageMonitor.monitorSupplier(() -> speedY), VoltageMonitor.monitorSupplier(() -> speedX));
	}
}