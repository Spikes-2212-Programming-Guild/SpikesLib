package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;

/**
 * This command moves a BasicSubsystem according to a Supplier or a constant
 * speed until it cannot move any more, and then wait a given number
 * of second to make the {@link BasicSubsystem} stick to its end state.
 * 
 * @author Omri "Riki" Cohen
 * @see MoveBasicSubsystem
 */
public class MoveBasicSubsystemSitickingToLimit extends MoveBasicSubsystem {

	/**
	 * The time the {@link BasicSubsystem} should keep trying to move anter reaching
	 * its end point.
	 */
	protected double waitTime;

	/**
	 * The last time the {@link BasicSubsystem} has been on the wanted target.
	 */
	protected double lastTimeAtEndState = 0;

	/**
	 * This constructs a new {@link MoveBasicSubsystemSitickingToLimit} command
	 * using the {@link BasicSubsystem} this command runs on and the supplier
	 * supplying the speed the {@link BasicSubsystem} should move with and a wait
	 * time.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param speedSupplier
	 *            a Double {@link Supplier} supplying the speed this subsystem
	 *            should be moved with. Must only supply values between -1 and 1.
	 * @param waitTime
	 *            the time the {@link BasicSubsystem} should keep trying to move
	 *            anter reaching its end point.
	 */
	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, Supplier<Double> speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemSitickingToLimit} command
	 * using the {@link BasicSubsystem} this command runs on and a constant speed
	 * the {@link BasicSubsystem} should moved with and a wait time.
	 *
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command opperates on.
	 * @param speed
	 *            the speed this subsystem should be moved with. Values must only be
	 *            between -1 and 1.
	 * @param waitTime
	 *            the time the {@link BasicSubsystem} should keep trying to move
	 *            anter reaching its end point.
	 */
	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, double speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	/**
	 * Checks if the subsystem can move with the current speed according to the
	 * limits given in the BasicSubsystem constructor, then checks if the system has
	 * been on target for the wanted amount time.
	 * 
	 * @see MoveBasicSubsystemSitickingToLimit#waitTime
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
															 * if the subsystem was on limit the wanted amount of time
															 * the command is finished.
															 */
			return true;
		}
		return false;
	}

}
