package com.spikes2212.genericsubsystems.drivetrains;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a type of Drivetrain with a left and right sides which
 * can be controlled separately, allowing you to turn by giving each side an
 * opposite sign speed, and go forward or backwards by giving both sides the
 * same positive or negative speed; as well as a combination of the two.
 */
public class TankDrivetrain extends Subsystem {

	protected final Consumer<Double> controlLeft;
	protected final Consumer<Double> controlRight;

	/**
	 * This constructs a new {@link TankDrivetrain} drivetrain.
	 * 
	 * @param controlLeft
	 *            controls the left side on the drivetrain
	 * @see Consumer
	 * @param controlRight
	 *            controls the right side on the drivetrain
	 * @see Consumer
	 */
	public TankDrivetrain(Consumer<Double> controlLeft, Consumer<Double> controlRight) {
		this.controlLeft = controlLeft;
		this.controlRight = controlRight;
	}

	/**
	 * Moves the both sides of this drivetrain using speedLeft and speedRight
	 * 
	 * @param speedLeft
	 *            The speed to set to the left side. Positive values move this
	 *            side forward.
	 * @param speedRight
	 *            The speed to set to the right side. Positive values move this
	 *            side forward.
	 */
	public void tankDrive(double speedLeft, double speedRight) {
		setLeft(speedLeft);
		setRight(speedRight);
	}

	/**
	 * Moves the left side of this drivetrain using speedLeft
	 *
	 * @param speedLeft
	 *            The speed to set to the left side. Positive values move this
	 *            side forward.
	 */
	public void setLeft(double speedLeft) {
		controlLeft.accept(speedLeft);
	}

	/**
	 * Moves the right side of this drivetrain using speedRight
	 *
	 * @param speedRight
	 *            The speed to set to the right side. Positive values move this
	 *            side forward.
	 */
	public void setRight(double speedRight) {
		controlRight.accept(speedRight);
	}

	/**
	 * stops this drivetrain
	 * 
	 * <p>
	 * it set the speed for both sides to 0. if your drivetrain doesn't stop
	 * this way Override this
	 * </p>
	 */
	public void stop() {
		setRight(0);
		setLeft(0);
	}

	/**
	 * Sets the default command. If this is not called or is called with null,
	 * then there will be no default command for the subsystem.
	 * 
	 * @see edu.wpi.first.wpilibj.command.Subsystem#setDefaultCommand(edu.wpi.first.wpilibj.command.Command)
	 */
	public void setDefaultCommand(Command defaultCommand) {
		super.setDefaultCommand(defaultCommand);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
