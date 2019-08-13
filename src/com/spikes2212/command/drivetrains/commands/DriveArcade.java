package com.spikes2212.command.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.command.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * this command moves a {@link TankDrivetrain} by linear and rotational speeds, using
 * the arcade control method written by WPILIB.
 */
public class DriveArcade extends Command {
	protected final TankDrivetrain tankDrivetrain;
	protected final Supplier<Double> moveValueSupplier;
	protected final Supplier<Double> rotateValueSupplier;

	/**
	 * This constructs a new {@link DriveArcade} command that moves the given
	 * {@link TankDrivetrain} according to constant linear and rotational speeds.
	 *
	 * @param drivetrain
	 *            the tank drivetrain this command opperates on.
	 * @param moveValue
	 *            the speed to move straight with. Positive values go forwards.
	 * @param rotateValue
	 *            the speed to turn with. Positive values turn left.
	 */
	public DriveArcade(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
		this(drivetrain, () -> moveValue, () -> rotateValue);
	}

	/**
	 * This constructs a new {@link DriveArcade} command that moves the given
	 * {@link TankDrivetrain} according to speed values from Double {@link Supplier}s
	 * for linear and rotational movements.
	 *
	 * @param drivetrain
	 *            the tank drivetrain this command opperates on.
	 * @param moveValueSupplier
	 *            the double {@link Supplier} supplying the speed to move forward
	 *            with. Positive values go forwards.
	 * @param rotateValueSupplier
	 *            the double {@link Supplier} supplying the speed to turn with.
	 *            Positive values go left.
	 */
	public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
			Supplier<Double> rotateValueSupplier) {
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
        tankDrivetrain.arcadeDrive(moveValueSupplier.get(), rotateValueSupplier.get());
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		tankDrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
