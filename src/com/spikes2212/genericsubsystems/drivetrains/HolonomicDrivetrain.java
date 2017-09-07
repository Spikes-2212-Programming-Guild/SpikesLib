package com.spikes2212.genericsubsystems.drivetrains;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.command.Command;

public class HolonomicDrivetrain extends TankDrivetrain {

	Consumer<Double> controlX;
	Consumer<Double> controlY;

	public HolonomicDrivetrain(Consumer<Double> controlLeft, Consumer<Double> controlRight, Consumer<Double> controlX,
			Consumer<Double> controlY) {
		super(controlLeft, controlRight);
		this.controlX = controlX;
		this.controlY = controlY;
	}

	public void holonomicDrive(double speedY, double speedX) { // y-forward/backward
																// x-left/right
		controlX.accept(speedX);
		controlY.accept(speedY);
	}

}
