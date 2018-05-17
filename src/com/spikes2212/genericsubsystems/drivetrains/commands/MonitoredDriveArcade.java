package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command work similarly to {@link DriveArcade}, only it scales down the
 * speed given to the {@link TankDrivetrain} according to the current voltage.
 * 
 * 
 * <br>
 * <br>
 * The supplier is scaled according to
 * {@link VoltageMonitor#monitorSupplier(Supplier)}, meaning according to the
 * default scaling method and values in {@link VoltageMonitor}.
 *
 * @author Omri "Riki" Cohen
 * @see DriveArcade
 * @see VoltageMonitor
 */
public class MonitoredDriveArcade extends DriveArcade {

	/**
	 * This constructs a new {@link MonitoredDriveArcade} command that moves the given
	 * {@link TankDrivetrain} according to speed values from Double
	 * {@link Supplier}s for linear and rotational movements.
	 *
	 * @param drivetrain
	 *            the tank drivetrain this command operates on.
	 * @param moveValueSupplier
	 *            the double {@link Supplier} supplying the speed to move
	 *            forward with. Positive values go forwards.
	 * @param rotateValueSupplier
	 *            the double {@link Supplier} supplying the speed to turn with.
	 *            Positive values go left.
	 * 
	 * @see VoltageMonitor#monitorSupplier(Supplier)
	 */
	public MonitoredDriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
			Supplier<Double> rotateValueSupplier) {
		super(drivetrain, VoltageMonitor.monitorSupplier(moveValueSupplier),
				VoltageMonitor.monitorSupplier(rotateValueSupplier));
	}

	/**
	 * This constructs a new {@link DriveArcade} command that moves the given
	 * {@link TankDrivetrain} according to constant linear and rotational speeds.
	 *
	 * @param drivetrain
	 *            the tank drivetrain this command operates on.
	 * @param moveValue
	 *            the speed to move straight with. Positive values go forwards.
	 * @param rotateValue
	 *            the speed to turn with. Positive values turn left.
	 * 
	 * @see VoltageMonitor#monitorSupplier(Supplier)
	 */
	public MonitoredDriveArcade(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
		super(drivetrain, VoltageMonitor.monitorSupplier(() -> moveValue),
				VoltageMonitor.monitorSupplier(() -> rotateValue));
	}

}