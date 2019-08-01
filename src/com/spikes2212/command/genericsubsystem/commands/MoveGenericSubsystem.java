package com.spikes2212.command.genericsubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.command.genericsubsystem.GenericSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link GenericSubsystem} according to a {@link Supplier}
 * or a constant speed until it cannot move any more.
 * 
 *
 * @author Omri "Riki" Cohen and Itamar Rivkind
 * @see GenericSubsystem
 */
public class MoveGenericSubsystem extends Command {

	protected final GenericSubsystem genericSubsystem;
	protected final Supplier<Double> speedSupplier;

	/**
	 * This constructs a new {@link MoveGenericSubsystem} command using the
	 * {@link GenericSubsystem} this command operates on and a supplier supplying the
	 * speed the {@link GenericSubsystem} should move with.
	 *
	 * @param genericSubsystem
	 *            the {@link GenericSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed this subsystem
	 *            should be moved with. Must only supply values between -1 and 1.
	 */
	public MoveGenericSubsystem(GenericSubsystem genericSubsystem, Supplier<Double> speedSupplier) {
		requires(genericSubsystem);
		this.genericSubsystem = genericSubsystem;
		this.speedSupplier = speedSupplier;
	}

	/**
	 * This constructs a new {@link MoveGenericSubsystem} command using the
	 * {@link GenericSubsystem} this command runs on and a constant speed the
	 * {@link GenericSubsystem} should move with.
	 *
	 * @param genericSubsystem
	 *            the {@link GenericSubsystem} this command operates on.
	 * @param speed
	 *            the speed this subsystem should be moved with. Values must be
	 *            between -1 and 1.
	 */
	public MoveGenericSubsystem(GenericSubsystem genericSubsystem, double speed) {
		this(genericSubsystem, () -> speed);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		genericSubsystem.move(speedSupplier.get());
	}

	/**
	 * Checks if the subsystem can move using the function canMove that is given to
	 * the {@link GenericSubsystem} in the Constructor with the current speed
	 * according to the limits given in the {@link GenericSubsystem} constructor, or
	 * if the command has timed out.
	 * 
	 * @return Returns true if this command should stop.
	 * 
	 * @see GenericSubsystem#canMove
	 */
	protected boolean isFinished() {
		return !genericSubsystem.canMove(speedSupplier.get()) || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		genericSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}