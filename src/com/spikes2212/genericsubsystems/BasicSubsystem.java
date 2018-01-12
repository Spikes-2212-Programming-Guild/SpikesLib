package com.spikes2212.genericsubsystems;

import java.util.function.Consumer;
import java.util.function.Function;

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
	protected Consumer<Double> speedConsumer;
	private double currentSpeed = 0;

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
	 * Moves this subsystem with the given speed.
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
