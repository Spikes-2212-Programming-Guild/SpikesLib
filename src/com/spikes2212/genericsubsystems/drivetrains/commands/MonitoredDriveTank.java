package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

/**
 * This command work similarly to {@link DriveTank}, only it scales down the
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
 * @see DriveTank
 * @see VoltageMonitor
 */
public class MonitoredDriveTank extends DriveTank {

	/**
	 * This constructs a new {@link DriveTank} command that moves the given
	 * {@link TankDrivetrain} acording to speed values from Double {@link Supplier}s
	 * for left and right sides.<br>
	 * Positive values move forwards.
	 *
	 * @param drivetrain
	 *            the drivetrain this command requires and moves.
	 * @param leftSpeedSupplier
	 *            the double {@link Supplier} supplying the speed to move in the
	 *            left side with.
	 * @param rightSpeedSupplier
	 *            the double {@link Supplier} supplying the speed to move in the
	 *            right side with.
	 * 
	 * @see VoltageMonitor#monitorSupplier(Supplier)
	 */
	public MonitoredDriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier,
			Supplier<Double> rightSpeedSupplier) {
		super(drivetrain, VoltageMonitor.monitorSupplier(leftSpeedSupplier),
				VoltageMonitor.monitorSupplier(rightSpeedSupplier));
	}

	/**
	 * This constructs a new {@link DriveTank} command that moves the given
	 * {@link TankDrivetrain} acording to constant left and right speeds.<br>
	 * Positive values move forwards.
	 *
	 * @param drivetrain
	 *            the tank drivetrain this command opperates on.
	 * @param leftSpeed
	 *            the speed to move the left side with.
	 * @param rightSpeed
	 *            the speed to move the right side with.
	 * 
	 * @see VoltageMonitor#monitorSupplier(Supplier)
	 */
	MonitoredDriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
		super(drivetrain, VoltageMonitor.monitorSupplier(() -> leftSpeed),
				VoltageMonitor.monitorSupplier(() -> rightSpeed));
	}

}