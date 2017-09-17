package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link LimitedSubsystem} according to a {@link Supplier}
 * or a constant speed until it cannot move any more.
 * <p>
 * This command finishes when {@link LimitedSubsystem#canMove(double)} returns
 * false for the value read from the supplier.
 * </p>
 *
 * @author Unknow Author
 * @see LimitedSubsystem
 */
public class MoveLimitedSubsystem extends MoveBasicSubsystem {
	private LimitedSubsystem limitedSubsystem;

	/**
	 * This constructs a new {@link MoveLimitedSubsystem} command using the
	 * {@link LimitedSubsystem} this command runs on and the speed the
	 * {@link LimitedSubsystem} should be moved with.
	 *
	 * @param limitedSubsystem
	 *            the {@link LimitedSubsystem} this command should moves.
	 * @param speed
	 *            the speed limitedSubsystem should be moved with. Values should
	 *            be between -1 and 1.
	 */
	public MoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, double speed) {
		super(limitedSubsystem, speed);
		this.limitedSubsystem = limitedSubsystem;
	}

	/**
	 * This constructs a new {@link MoveLimitedSubsystem} command using the
	 * {@link LimitedSubsystem} this command runs on and the supplier supplying
	 * the speed the {@link LimitedSubsystem} should be moved with.
	 *
	 * @param limitedSubsystem
	 *            the {@link LimitedSubsystem} this command should moves.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed limitedSubsystem
	 *            should be moved with. Should only supply values between -1 and
	 *            1.
	 */
	public MoveLimitedSubsystem(LimitedSubsystem limitedSubsystem, Supplier<Double> speedSupplier) {
		super(limitedSubsystem, speedSupplier);
		this.limitedSubsystem = limitedSubsystem;
	}

	@Override
	protected void execute() {
		limitedSubsystem.tryMove(speedSupplier.get());

	}

	@Override
	protected boolean isFinished() {
		return !limitedSubsystem.canMove(speedSupplier.get()) || isTimedOut();
	}
}
