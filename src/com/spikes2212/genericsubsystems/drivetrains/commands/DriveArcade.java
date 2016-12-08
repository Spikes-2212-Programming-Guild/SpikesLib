package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveArcade extends Command {
	TankDrivetrain tankDrivetrain;
	private Supplier<Double> moveValueSupplier;
	private Supplier<Double> rotateValueSupplier;

	public DriveArcade(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
		this(drivetrain, () -> moveValue, () -> rotateValue);
	}

	public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier, Supplier<Double> rotateValueSupplier) {
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.moveValueSupplier = moveValueSupplier;
		this.rotateValueSupplier = rotateValueSupplier;
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
				leftSpeed = Math.max(moveValue, rotateValue);
				rightSpeed =  moveValue - rotateValue;
			} else {
				leftSpeed = moveValue + rotateValue; 
				rightSpeed =  Math.max(moveValue, -rotateValue);
			}
		} else {
			if (rotateValue > 0.0) {
				leftSpeed = Math.min(moveValue, -rotateValue);
				rightSpeed = moveValue + rotateValue;
			} else {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = Math.min(moveValue, rotateValue);
			}
		}
		tankDrivetrain.tankDrive(leftSpeed, rightSpeed);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
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
