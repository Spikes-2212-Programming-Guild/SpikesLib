package com.spikes2212.command.drivetrains;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.IllegalUseOfCommandException;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This class represents a type of Drivetrain that its left and right sides are
 * controlled independently, allowing it to move by giving each side a speed
 * value separately.
 * 
 * It can move forwards/backwards by giving both its sides an equal speed or
 * turn by giving the sides different speeds
 */
public class TankDrivetrain extends Subsystem {
	
	private SpeedController leftController;
	private SpeedController rightController;
	private DifferentialDrive drive;
	
	public TankDrivetrain(SpeedController left, SpeedController right) {
		this.leftController = left;
		this.rightController = right;
		drive = new DifferentialDrive(leftController, rightController);
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
		drive.tankDrive(speedLeft, speedRight);
	}

	public void arcadeDrive(double moveValue, double rotateValue) {
		drive.arcadeDrive(moveValue, rotateValue);
	}

	/**
	 * Moves the left side of this drivetrain by a given speed.
	 *
	 * @param speedLeft
	 *            the speed to set to the left side. Positive values move this side
	 *            forward.
	 */
	public void setLeft(double speedLeft) {
		leftController.set(speedLeft);
	}

	/**
	 * Moves the right side of this drivetrain with a given speed.
	 *
	 * @param speedRight
	 *            the speed to set to the right side. Positive values move this side
	 *            forward.
	 */
	public void setRight(double speedRight) {
		rightController.set(speedRight);
	}

	/**
	 * Stops this drivetrain. <br>
	 * <br>
	 * 
	 * This sets the speed for both sides to 0. If your drivetrain doesn't stop
	 * using this method, override it.
	 */
	public void stop() {
		leftController.stopMotor();
		rightController.stopMotor();
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
