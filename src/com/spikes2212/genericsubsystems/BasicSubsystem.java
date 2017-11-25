package com.spikes2212.genericsubsystems;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a subsystem that moves with a limitation which depends
 * on the speed.
 *
 * @author Omri "Riki" Cohen
 */
public class BasicSubsystem extends Subsystem {

	/**
	 * This function, when applied to a certain double speed returns true if
	 * this subsystem can move at that speed
	 */
	public final Function<Double, Boolean> canMove;
	private Consumer<Double> speedConsumer;

	/**
	 * this constructs a new {@link BasicSubsystem} subsystem.
	 * 
	 * @param speedConsumer
	 *            the component using the speed (usually a motor/ motors).
	 * @param canMove
	 *            the limitation on the movement, which depends on the speed.
	 */
	public BasicSubsystem(Consumer<Double> speedConsumer, Function<Double, Boolean> canMove) {
		this.canMove = canMove;
		this.speedConsumer = speedConsumer;
	}

	/**
	 * this constructs a new {@link BasicSubsystem} subsystem this 2 limits the
	 * subsystem can move between.
	 * 
	 * @param speedConsumer
	 *            the component using the speed (usually a motor/ motors).
	 *            positive speed moves towards the maxLimit and negative towards
	 *            the minLimit
	 * @param maxLimit
	 *            the upper limit, positive speed makes the
	 *            {@link BasicSubsystem} move towards this limit.
	 * @param minLimit
	 *            the lower limit, negative speed makes the
	 *            {@link BasicSubsystem} move towards this limit.
	 */
	public BasicSubsystem(Consumer<Double> speedConsumer, Supplier<Boolean> maxLimit, Supplier<Boolean> minLimit) {
		this(speedConsumer, (speed) -> {
			if (speed > 0 && maxLimit.get()) // Checks if the max limit is
												// pressed and if a positive
												// speed is given
				return false;
			if (speed < 0 && minLimit.get()) // Checks if the min limit is
												// pressed and if a negative
												// speed is given
				return false;
			return true;
		});
	}

	/**
	 * Moves this subsystem with the given speed.
	 *
	 * @param speed
	 *            the speed to move the subsystem with.
	 */
	public void move(double speed) {
		if (canMove.apply(speed)) {
			speedConsumer.accept(speed);
		}
	}

	/**
	 * Stops this subsystem's movement.
	 */
	public void stop() {
		move(0);
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

	public void initDefaultCommand() {

	}

}
