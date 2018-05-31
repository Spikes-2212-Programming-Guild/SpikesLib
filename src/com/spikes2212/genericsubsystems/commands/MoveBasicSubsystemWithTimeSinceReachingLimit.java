package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;

/**
 * This command moves a {@link BasicSubsystem} according to a {@link Supplier}
 * or a constant speed until it reaches its limit, and then continue moving for
 * a given number of seconds.
 * 
 * @author Omri "Riki" Cohen
 * 
 * @see MoveBasicSubsystem
 */
public class MoveBasicSubsystemWithTimeSinceReachingLimit extends MoveBasicSubsystem {

	/**
	 * The time the {@link BasicSubsystem} should keep trying to move after reaching
	 * its end point.
	 */
	protected double waitTime;

	/**
	 * The last time the {@link BasicSubsystem} has been on the wanted endState.
	 */
	protected double lastTimeAtEndState = 0;

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithTimeSinceReachingLimit}
	 * command using the {@link BasicSubsystem} this command runs on, a supplier
	 * supplying the speed the {@link BasicSubsystem} should move with, and a wait
	 * time.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a {@link Double} {@link Supplier} supplying the speed this
	 *            subsystem should be moved with. Must only supply values between -1
	 *            and 1.
	 * @param waitTime
	 *            the time the {@link BasicSubsystem} should keep trying to move
	 *            after reaching its end point.
	 */
	public MoveBasicSubsystemWithTimeSinceReachingLimit(BasicSubsystem basicSubsystem, Supplier<Double> speed,
			double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithTimeSinceReachingLimit}
	 * command using the {@link BasicSubsystem} this command runs on, a constant
	 * speed the {@link BasicSubsystem} should move with, and a wait time.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a speed this subsystem should be moved with. Must only be a value
	 *            between -1 and 1.
	 * @param waitTime
	 *            the time the {@link BasicSubsystem} should keep trying to move
	 *            after reaching its end point.
	 */
	public MoveBasicSubsystemWithTimeSinceReachingLimit(BasicSubsystem basicSubsystem, double speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	/**
	 * Checks if the subsystem has reached the limits given in the
	 * {@link BasicSubsystem} constructor, then for the given amount time.
	 */
	@Override
	protected boolean isFinished() {
		double currentTime = Timer.getFPGATimestamp();
		if (!super.isFinished()) { /*
									 * if not in the ending position reset the timer.
									 */
			lastTimeAtEndState = currentTime;
		}
		if (currentTime - lastTimeAtEndState > waitTime) { /*
															 * if the subsystem was on limit the wanted time the command
															 * is finished.
															 */
			return true;
		}
		return false;
	}

}
