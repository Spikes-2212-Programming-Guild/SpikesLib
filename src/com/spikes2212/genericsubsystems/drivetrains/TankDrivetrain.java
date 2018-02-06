package com.spikes2212.genericsubsystems.drivetrains;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a type of Drivetrain that its left and right sides are
 * controlled independently, allowing it to move by giving each side a speed
 * value separately.
 * 
 * It can move forwards/backwards by giving both its sides an equal speed or
 * turn by giving the sides different speeds
 */
public class TankDrivetrain extends Subsystem {

	protected final Consumer<Double> controlLeft;
	protected final Consumer<Double> controlRight;

	/**
	 * This constructs a new {@link TankDrivetrain}.
	 * 
	 * @param controlLeft
	 *            the component controlling the left side of the drivetrain
	 * @param controlRight
	 *            the component controlling the left side of the drivetrain
	 * 
	 * @see Consumer
	 */
	public TankDrivetrain(Consumer<Double> controlLeft, Consumer<Double> controlRight) {
		this.controlLeft = controlLeft;
		this.controlRight = controlRight;
	}

	/**
	 * Moves both sides of this drivetrain by the given speeds for each side.
	 * 
	 * @param speedLeft
	 *            the speed to set to the left side. Positive values move this side
	 *            forward.
	 * @param speedRight
	 *            the speed to set to the right side. Positive values move this side
	 *            forward.
	 */
	public void tankDrive(double speedLeft, double speedRight) {
		setLeft(speedLeft);
		setRight(speedRight);
	}

	public void arcadeDrive(double moveValue, double rotateValue) {
		double leftSpeed, rightSpeed;

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

		this.tankDrive(leftSpeed, rightSpeed);
	}

	/**
	 * Moves the left side of this drivetrain by a given speed.
	 *
	 * @param speedLeft
	 *            the speed to set to the left side. Positive values move this side
	 *            forward.
	 */
	public void setLeft(double speedLeft) {
		controlLeft.accept(speedLeft);
	}

	/**
	 * Moves the right side of this drivetrain with a given speed.
	 *
	 * @param speedRight
	 *            the speed to set to the right side. Positive values move this side
	 *            forward.
	 */
	public void setRight(double speedRight) {
		controlRight.accept(speedRight);
	}

	/**
	 * Stops this drivetrain. <br>
	 * <br>
	 * 
	 * This sets the speed for both sides to 0. If your drivetrain doesn't stop
	 * using this method, override it.
	 */
	public void stop() {
		setRight(0);
		setLeft(0);
	}

	/**
	 * Sets the default
	 * <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/">command</a>.
	 * If this is not called or is called with null, then there will be no default
	 * command for the subsystem.
	 *
	 * @param defaultCommand
	 *            the default command (or null if there should be none)
	 * @throws IllegalUseOfCommandException
	 *             if the command does not require the subsystem
	 */
	public void setDefaultCommand(Command defaultCommand) {
		super.setDefaultCommand(defaultCommand);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
