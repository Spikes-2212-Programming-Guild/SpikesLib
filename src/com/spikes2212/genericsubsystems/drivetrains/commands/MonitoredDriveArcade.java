package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.VoltageMonitor;

import edu.wpi.first.wpilibj.command.Command;

public class MonitoredDriveArcade extends Command {
	TankDrivetrain tankDrivetrain;
	private Supplier<Double> moveValueSupplier;
	private Supplier<Double> rotateValueSupplier;

	/**
	 * Constructs a command which moves the drive system according to constant speed values for its straight and angular movements.this system is monitored.
	 * 
	 * @param drivetrain is the drive system meant to move
	 * @param moveValue is the constant speed value by which the drive system will move straight.
	 * @param rotateValue is the constant speed value by which the drive system will turn to any side.
	 */
	public MonitoredDriveArcade(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
		this(drivetrain, () -> moveValue, () -> rotateValue);
	}

	/**
	 * Constructs a command which moves the drive system according to speed suppliers for its straight and angular movements.
	 * 
	 * @param drivetrain is the drive system meant to move
	 * @param moveValueSupplier is the speed supplier which will supply speed values to the drive system's straight movement
	 * @param rotateValueSupplier is the speed supplier which will supply speed values to the drive system's turning movement
	 */
	public MonitoredDriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
			Supplier<Double> rotateValueSupplier) {
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.moveValueSupplier = VoltageMonitor.monitorSupplier(moveValueSupplier);
		this.rotateValueSupplier = VoltageMonitor.monitorSupplier(rotateValueSupplier);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		double leftSpeed, rightSpeed;
		double moveValue = moveValueSupplier.get();
		double rotateValue = rotateValueSupplier.get();
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftSpeed = Math.max(moveValue, -rotateValue);
				rightSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftSpeed = -Math.max(-moveValue, rotateValue);
				rightSpeed = moveValue + rotateValue;
			} else {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}
		tankDrivetrain.tankDrive(leftSpeed, rightSpeed);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		tankDrivetrain.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
