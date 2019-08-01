package com.spikes2212.command.genericsubsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a generic subsystem that moves within a limitation, or
 * without one.
 *
 * @author Omri "Riki" Cohen
 */
public abstract class GenericSubsystem extends Subsystem {

	private double currentSpeed = 0;
	private double maxSpeed;
	private double minSpeed;
	
	/**
	 * Constructs a new instance of GenericSubsystem.
	 */
	public GenericSubsystem() {
		this(-1, 1);
	}
	
	/**
	 * Constructs a new instance of GenericSubsystem with the given minSpeed and maxSpeed.
	 *
	 * @param minSpeed the minimum speed
	 * @param maxSpeed the maximum speed
	 */
	public GenericSubsystem(double minSpeed, double maxSpeed) {
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
	}
	
	/**
	 * Moves this {@link GenericSubsystem} with the given speed, as long as it is
	 * within the limits specified when this {@link GenericSubsystem} was constructed.
	 *
	 * @param speed
	 *            the speed to move the subsystem with.
	 */
	public void move(double speed) {
		if(canMove(speed) && minSpeed <= speed && speed <= maxSpeed) {
			apply(speed);
		}
	}
	
	/**
	 * This functions applies a given speed to the subsystem.
	 *
	 * @param speed the speed
	 */
	public abstract void apply(double speed);
	
	/**
	 * This method returns whether the subsystem can move safely.
	 *
	 * @param speed the speed
	 *
	 * @return whether the subsystem can move safely
	 */
	public abstract boolean canMove(double speed);
	
	/**
	 * Stops this subsystem's movement.
	 */
	public abstract void stop();

	/**
	 * Return the current speed of this {@link GenericSubsystem}.
	 *
	 * @return the current speed of this {@link GenericSubsystem}.
	 */
	public double getSpeed() {
		return currentSpeed;
	}
}
