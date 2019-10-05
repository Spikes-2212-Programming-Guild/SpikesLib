package com.spikes2212.command.genericsubsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.function.Supplier;

/**
 * This class represents a GenericSubsystem that moves within a limitation, or
 * without one.
 *
 * @author Omri "Riki" Cohen
 */
public abstract class GenericSubsystem extends Subsystem {
	
	private double currentSpeed = 0;
	private Supplier<Double> maxSpeed;
	private Supplier<Double> minSpeed;
	
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
		this(() -> minSpeed, () -> maxSpeed);
	}
	
	/**
	 * Constructs a new instance of GenericSubsystem with the given minSpeed supplier and maxSpeed supplier.
	 *
	 * @param minSpeed the minimum speed
	 * @param maxSpeed the maximum speed
	 */
	public GenericSubsystem(Supplier<Double> minSpeed, Supplier<Double> maxSpeed) {
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
	}
	
	/**
	 * Moves this GenericSubsystem with the given speed, as long as it is
	 * within the limits specified when this GenericSubsystem was constructed.
	 *
	 * @param speed
	 *            the speed to move the GenericSubsystem with.
	 */
	public final void move(double speed) {
		if (speed < minSpeed.get()) speed = minSpeed.get();
		if (speed > maxSpeed.get()) speed = maxSpeed.get();
		if (canMove(speed)) {
			apply(speed);
			currentSpeed = speed;
		}
	}
	
	/**
	 * This method applies a given speed to the GenericSubsystem.
	 *
	 * @param speed the speed
	 */
	public abstract void apply(double speed);
	
	/**
	 * This method returns whether the GenericSubsystem can move safely.
	 *
	 * @param speed the speed
	 *
	 * @return whether the GenericSubsystem can move safely
	 */
	public abstract boolean canMove(double speed);
	
	/**
	 * Stops this GenericSubsystem's movement.
	 */
	public abstract void stop();

	/**
	 * Return the current speed of this GenericSubsystem.
	 *
	 * @return the current speed of this GenericSubsystem.
	 */
	public double getSpeed() {
		return currentSpeed;
	}

	/**
	 * This method adds testing commands for this GenericSubsystem.
	 */
	public abstract void initTestingDashboard();
}
