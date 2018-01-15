package com.spikes2212.genericsubsystems;

import java.util.function.Consumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a generic subsystem that moves within a limitation, or
 * without one, with given speed limits.
 *
 * @author Omri "Riki" Cohen
 */
public class BasicSubsystem extends Subsystem {

	/**
	 * A {@link Function} to store the limits of the subsystem's speed.
	 */
	public final Function<Double, Boolean> canMove;

	/**
	 * A {@link Consumer} to represent the moving component.
	 */
	protected final Consumer<Double> speedConsumer;

	/**
	 * Constructor that recieves a {@link Consumer} for speed and a {@link Function}
	 * that represents the limits of the subsystem's speed.
	 * 
	 * @param speedConsumer
	 *            the component using the speed (usually a motor/motors).
	 * @param canMove
	 *            the limitation on the movement, which depends on the speed.
	 */
	public BasicSubsystem(Consumer<Double> speedConsumer, Function<Double, Boolean> canMove) {
		this.canMove = canMove;
		this.speedConsumer = speedConsumer;
	}

	/**
	 * Moves this subsystem with the given speed, assuming it is within the limits
	 * specifed when this {@link BasicSubsystem} was constructed.
	 *
	 * @param speed
	 *            the speed to move the subsystem with.
	 */
	public void move(double speed) {
		if (canMove.apply(speed)) {		// Checks whether the given speed is with the limits
			speedConsumer.accept(speed);// Activates the moving component with the given speed
		}
	}

	/**
	 * Stops this subsystem's movement.
	 */
	public void stop() {
		move(0);
	}

	/**
	 * Sets the default command. If this is not called, or is called with null, then
	 * there will be no default command for the subsystem.
	 */
	public void setDefaultCommand(Command defaultCommand) {
		super.setDefaultCommand(defaultCommand);
	}

	public void initDefaultCommand() {

	}

}
