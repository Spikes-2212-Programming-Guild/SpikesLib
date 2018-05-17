package com.spikes2212.genericsubsystems.basicSubsystem;

import java.util.function.Consumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a generic subsystem that moves within a limitation, or
 * without one.
 *
 * @author Omri "Riki" Cohen
 */
public class BasicSubsystem extends Subsystem {

	/**
	 * This function, when applied to a certain double speed returns true if
	 * this subsystem can move at that speed
	 * A {@link Function} to store the limits of the subsystem's speed.
	 */
	public final Function<Double, Boolean> canMove;
	
	/**
	 * A {@link Consumer} to represent the movement of the basicSubsystem.
	 */
	protected final Consumer<Double> speedConsumer;
	private double currentSpeed = 0;

	/**
	 * Constructor that recieves a {@link Consumer} for the movement component and a {@link Function}
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
	 * Moves this basicSubsystem with the given speed, as long as it is within the limits
	 * specifed when this {@link BasicSubsystem} was constructed.
	 *
	 * @param speed
	 *            the speed to move the subsystem with.
	 */
	public void move(double speed) {
		if (canMove.apply(speed)) {
			if (speed > 1)
				speed = 1;
			else if (speed < -1)
				speed = -1;
			speedConsumer.accept(speed);
			this.currentSpeed = speed;
		}
	}

	/**
	 * Stops this subsystem's movement.
	 */
	public void stop() {
		move(0);
	}

	/**
	 * Return the current speed of this {@link BasicSubsystem}.
	 *
	 * @return the current speed of this {@link BasicSubsystem}.
	 */
	public double getSpeed() {
		return currentSpeed;
	}

	/**
	 * @see Sets the default command. If this is not called, or is called with null, then
	 * there will be no default command for the subsystem.
	 */
	public void setDefaultCommand(Command defaultCommand) {
		super.setDefaultCommand(defaultCommand);
	}

	public void initDefaultCommand() {

	}

}
