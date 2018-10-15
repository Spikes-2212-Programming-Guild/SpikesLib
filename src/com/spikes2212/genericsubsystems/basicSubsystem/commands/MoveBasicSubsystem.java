package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link BasicSubsystem} according to a {@link Supplier}
 * or a constant speed until it cannot move any more.
 * 
 *
 * @author Omri "Riki" Cohen and Itamar Rivkind
 * @see BasicSubsystem
 */
public class MoveBasicSubsystem extends Command {

	protected final BasicSubsystem subsystem;
	protected final Supplier<Double> speedSupplier;

	/**
	 * This constructs a new {@link MoveBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command operates on and a supplier supplying the
	 * speed the {@link BasicSubsystem} should move with.
	 *
	 * @param subsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed this subsystem
	 *            should be moved with. Must only supply values between -1 and 1.
	 */
	public MoveBasicSubsystem(BasicSubsystem subsystem, Supplier<Double> speedSupplier) {
		requires(subsystem);
		this.subsystem = subsystem;
		this.speedSupplier = speedSupplier;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command runs on and a constant speed the
	 * {@link BasicSubsystem} should move with.
	 *
	 * @param subsystem
	 *            the {@link BasicSubsystem} this command opperates on.
	 * @param speed
	 *            the speed this subsystem should be moved with. Values must be
	 *            between -1 and 1.
	 */
	public MoveBasicSubsystem(BasicSubsystem subsystem, double speed) {
		this(subsystem, () -> speed);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		subsystem.move(speedSupplier.get());
	}

	/**
	 * Checks if the subsystem can move using the function canMove that is given to
	 * the {@link BasicSubsystem} in the Constructor with the current speed
	 * according to the limits given in the {@link BasicSubsystem} constructor, or
	 * if the command has timed out.
	 * 
	 * @return Returns true if this command should stop.
	 * 
	 * @see BasicSubsystem#canMove
	 */
	protected boolean isFinished() {
		return !subsystem.canMove.test(speedSupplier.get()) || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		subsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}