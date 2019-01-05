package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * This command rise the speed of a {@link BasicSubsystem} linearly so it will
 * reach a wanted speed after a given time.
 * 
 * @author Yuval
 *
 */
public class RiseBasicSubsystem extends MoveBasicSubsystem {
	protected final double time;
	protected final double acceleration;
	private double currentSpeed = 0;
	private double startTime;

	/**
	 * This constructs a new {@link RiseBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command operates on and a supplier supplying the
	 * wanted speed the {@link BasicSubsystem} should move with after the given
	 * time.
	 * 
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param wantedSpeed
	 *            the speed the subsystem should move after the time.
	 * @param time
	 *            the time it takes for the subsystem to get to the speed.
	 */
	public RiseBasicSubsystem(BasicSubsystem basicSubsystem, Supplier<Double> wantedSpeed, double time) {
		super(basicSubsystem, wantedSpeed);
		if (time <= 1) {
			time = 1;
		}
		this.time = time;
		acceleration = wantedSpeed.get() / time;
	}

	/**
	 * This constructs a new {@link RiseBasicSubsystem} command using the
	 * {@link BasicSubsystem} this command operates on and a supplier supplying the
	 * wanted speed the {@link BasicSubsystem} should move with after the given
	 * time.
	 * 
	 * @param basicSubsystem
	 *            the {@link BasicSubsystem} this command should move.
	 * @param wantedSpeed
	 *            the speed the subsystem should move after the time.
	 * @param time
	 *            the time it takes for the subsystem to get to the speed.
	 */
	public RiseBasicSubsystem(BasicSubsystem basicSubsystem, double wantedSpeed, double time) {
		this(basicSubsystem, () -> wantedSpeed, time);
	}

	/**
	 * Reset the timer.
	 */
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
	}

	/**
	 * Calculate the speed and moves the subsystem.
	 */
	@Override
	public void execute() {
		currentSpeed = (Timer.getFPGATimestamp() - startTime) * acceleration;
		if (Math.abs(currentSpeed) > Math.abs(speedSupplier.get()))
			currentSpeed = speedSupplier.get();
		basicSubsystem.move(currentSpeed);
	}

}
