package com.spikes2212.genericsubsystems.drivetrains;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDUsingTankDrivedrain extends TankDrivetrain {

	PIDSource leftPIDSource;
	PIDSource rightPIDSource;

	public PIDUsingTankDrivedrain(Consumer<Double> controlLeft, Consumer<Double> controlRight, PIDSource leftPIDSource,
			PIDSource rightPIDSource) {
		super(controlLeft, controlRight);
		this.leftPIDSource = leftPIDSource;
		this.rightPIDSource = rightPIDSource;

	}

	/**
	 * Configures and returns the default {@link PIDSource} for the left side of
	 * this drivetrain.
	 *
	 * @return the default {@link PIDSource} for the left side.
	 */
	public PIDSource getLeftPIDSource() {
		return leftPIDSource;
	}

	/**
	 * Configures and returns the default {@link PIDSource} for the right side
	 * of this drivetrain.
	 *
	 * @return the default {@link PIDSource} for right side.
	 */
	public PIDSource getRightPIDSource() {
		return rightPIDSource;
	}
}
