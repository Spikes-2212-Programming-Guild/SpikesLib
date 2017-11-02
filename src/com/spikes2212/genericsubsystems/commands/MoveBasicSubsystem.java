package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link BasicSubsystem} according to a {@link Supplier}
 * or a constant speed until it cannot move any more.
 * 
 *
 * @author Omri "Riki" Cohen and Itamar
 * @see BasicSubsystem
 */
public class MoveBasicSubsystem extends Command {

	private BasicSubsystem basicSubsystem;
	private Supplier<Double> speedSupplier;

	/**
	 * This constructs a new {@link MoveBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command runs on and the supplier supplying
	 * the speed the {@link BasicSubsystem} should be moved with.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should moves.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed basicSubsystem
	 *            should be moved with. Should only supply values between -1 and
	 *            1.
	 */
	public MoveBasicSubsystem(BasicSubsystem basicSubsystem, Supplier<Double> speedSupplier) {
		this.basicSubsystem = basicSubsystem;
		this.speedSupplier = speedSupplier;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command runs on and the supplier supplying
	 * the speed the {@link BasicSubsystem} should be moved with.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should moves.
	 * @param speed
	 *            the speed basicSubsystem should be moved with. Values should
	 *            be between -1 and 1.
	 */
	public MoveBasicSubsystem(BasicSubsystem basicSubsystem, double speed) {
		this(basicSubsystem, () -> speed);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		basicSubsystem.move(speedSupplier.get());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !basicSubsystem.canMove.apply(speedSupplier.get()) || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		basicSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}