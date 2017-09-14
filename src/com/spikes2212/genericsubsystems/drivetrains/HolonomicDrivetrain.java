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
	
	public void setX (double speedX){
		controlX.accept(speedX);
	}
	
	public void setY (double speedY){
		controlY.accept(speedY);
	}

	public void holonomicDrive(double speedY, double speedX) { // y-forward/backward														// x-left/right
		setX(speedX);
		setY(speedY);
	}

}
