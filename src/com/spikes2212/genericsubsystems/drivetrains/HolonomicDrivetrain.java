package com.spikes2212.genericsubsystems.drivetrains;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class represents a type of Drivetrain with a x and y axises which can be
 * controlled separately.
 */
public class HolonomicDrivetrain extends TankDrivetrain {

	protected final Consumer<Double> controlX;
	protected final Consumer<Double> controlY;

	/**
	 * This constructs a new {@link HolonomicDrivetrain} drivetrain.
	 * 
	 * @param controlLeft
	 *            controls the left side on the drivetrain
	 * @param controlRight
	 *            controls the right side on the drivetrain
	 * @param controlX
	 *            controls the x axis of the drivetrain
	 * @param controlY
	 *            controls the y axis of the drivetrain
	 */

	public HolonomicDrivetrain(Consumer<Double> controlLeft, Consumer<Double> controlRight, Consumer<Double> controlX,
			Consumer<Double> controlY) {
		super(controlLeft, controlRight);
		this.controlX = controlX;
		this.controlY = controlY;
	}

	/**
	 * Moves the the drivetrain on the x axis
	 *
	 * @param speedX
	 *            The speed to set to the x axis. Positive values move this
	 *            forward.
	 */
	public void setX(double speedX) {
		controlX.accept(speedX);
	}

	/**
	 * Moves the the drivetrain on the y axis
	 *
	 * @param speedY
	 *            The speed to set to the Y axis. Positive values move this
	 *            forward.
	 */
	public void setY(double speedY) {
		controlY.accept(speedY);
	}

	/**
	 * Moves the drivetrain on both x and y axises using speedX and speedY
	 * 
	 * @param speedY
	 *            The speed to set to the Y axis. Positive values move this
	 *            forward.
	 * @param speedX
	 *            The speed to set to the X axis. Positive values move this
	 *            forward.
	 */
	public void holonomicDrive(double speedY, double speedX) { // y-forward/backward  x-left/right 
		setX(speedX);
		setY(speedY);
	}

}
