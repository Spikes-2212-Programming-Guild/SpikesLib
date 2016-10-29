package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TankDrivetrain extends Subsystem {
	private Encoder encoderLeft;
	private Encoder encoderRight;

	public TankDrivetrain(int aRightPort, int bRightPort, int aLeftPort, int bLeftPort) {

		encoderRight = new Encoder(aRightPort, bRightPort);
		encoderLeft = new Encoder(aLeftPort, bLeftPort);

	}

	public double getRightDistance() {
		return encoderRight.getDistance();
	}

	public void setRightDistancePerPulse(double newDistance) {
		encoderRight.setDistancePerPulse(newDistance);
	}

	public double getLeftDistance() {
		return encoderLeft.getDistance();
	}

	public void setLeftDistancePerPulse(double newDistance) {
		encoderLeft.setDistancePerPulse(newDistance);
	}

	public double getDistance() {
		return (encoderRight.getDistance() + encoderLeft.getDistance()) / 2;
	}

	public void setDistancePerPulse(double newDistance) {
		encoderLeft.setDistancePerPulse(newDistance);
		encoderRight.setDistancePerPulse(newDistance);
	}
}