package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;

/**
 * This command moves a {@link BasicSubsystem} according to a {@link Supplier}
 * or a constant speed until it can no longer move and then wait a given
 * number of second to make the {@link BasicSubsystem} stick to its end state.
 * 
 *
 * @author Omri "Riki" Cohen
 * @see BasicSubsystem
 */
public class MoveBasicSubsystemSitickingToLimit extends MoveBasicSubsystem {
	private double waitTime;
	private double lastTimeAtEndState = 0;

	/**
	 * This constructs a new {@link MoveBasicSubsystemSitickingToLimit} command
	 * using the {@link BasicSubsystem} this command runs on and the supplier
	 * supplying the speed the {@link BasicSubsystem} should be moved with and a
	 * wait time.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed basicSubsystem
	 *            should be moved with. Should only supply values between -1 and
	 *            1.
	 * @param waitTime
	 *            the time the {@link BasicSubsystem} should keep trying to move
	 *            since reaching its end point.
	 */
	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, Supplier<Double> speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemSitickingToLimit} command
	 * using the {@link BasicSubsystem} this command runs on and the supplier
	 * supplying the speed the {@link BasicSubsystem} should be moved with and a
	 * wait time.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speed
	 *            the speed {@link BasicSubsystem} should be moved with. Should only
	 *            be values between -1 and 1.
	 * @param waitTime
	 *            the time the {@link BasicSubsystem} should keep trying to move
	 *            since reaching its end point.
	 */
	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, double speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	@Override
	protected boolean isFinished() {
		double currentTime = Timer.getMatchTime();
		if (!super.isFinished()) {
			lastTimeAtEndState = currentTime; 
		}
		if (currentTime - lastTimeAtEndState > waitTime) {
			return true;
		}
		return false;
	}

}
