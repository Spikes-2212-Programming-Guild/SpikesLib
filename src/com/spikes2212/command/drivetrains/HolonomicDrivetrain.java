package com.spikes2212.command.drivetrains;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * This class represents a type of Drivetrain with an X (left/right) and Y
 * (forwards/backwards) axes which can be controlled independently, allowing it
 * to move by giving each axis a speed value separately. <br>
 * <br>
 * 
 * This Drivetrain extends {@link TankDrivetrain}, which means it can also move
 * by controlling the left and right sides of the drivetrain using driveTank, or by
 * controlling its linear and rotational speeds using driveArcade.
 * 
 * @see TankDrivetrain
 */
public class HolonomicDrivetrain extends TankDrivetrain {

	protected final SpeedController controlX;
	protected final SpeedController controlY;

	/**
	 * This constructs a new {@link HolonomicDrivetrain} drivetrain.
	 * 
	 * @param controlLeft
	 *            the component controlling the left side of the drivetrain
	 * @param controlRight
	 *            the component controlling the left side of the drivetrain
	 * @param controlX
	 *            the component controlling the X axis of the drivetrain
	 * @param controlY
	 *            the component controlling the Y axis of the drivetrain
	 *
	 */

	public HolonomicDrivetrain(SpeedController controlLeft, SpeedController controlRight, SpeedController controlX,
			SpeedController controlY) {
		super(controlLeft, controlRight);
		this.controlX = controlX;
		this.controlY = controlY;
	}

	/**
	 * Moves the drivetrain on the X axis by a given speed.
	 *
	 * @param speedX
	 *            the speed to set to the X axis. Positive values move this axis
	 *            forward.
	 */
	public void setX(double speedX) {
		controlX.set(speedX);
	}

	/**
	 * Moves the drivetrain on the Y axis by a given speed.
	 *
	 * @param speedY
	 *            the speed to set to the Y axis. Positive values move this axis
	 *            forward.
	 */
	public void setY(double speedY) {
		controlY.set(speedY);
	}

	/**
	 * Moves the drivetrain on both X and Y axes using given speeds for each axis.
	 * 
	 * @param speedY
	 *            the speed to set to the Y axis. Positive values move the {@link HolonomicDrivetrain}
	 *            forward on this axis.
	 * @param speedX
	 *            the speed to set to the X axis. Positive values move the {@link HolonomicDrivetrain}
	 *            forward on this axis.
	 */
	public void holonomicDrive(double speedY, double speedX) { // y-forward/backward x-left/right
		setX(speedX);
		setY(speedY);
	}
	
	public void stop() {
		super.stop();
		setX(0);
		setY(0);
	}
}
