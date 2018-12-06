package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

/**
 * {@link https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599745-scheduling-commands}
 * 
 * @author Yuval
 *
 */
public class MoveBasicSubsystemLinearly extends MoveBasicSubsystem {
	private double currentSpeed = 0;
	protected final double time;
	protected final double acceleration;

	public MoveBasicSubsystemLinearly(BasicSubsystem basicSubsystem, Supplier<Double> wantedSpeed, double time) {
		super(basicSubsystem, wantedSpeed);
		if (time <= 0) {
			time = 1;
		}
		this.time = time;
		acceleration = (wantedSpeed.get()) / time;
	}

	public MoveBasicSubsystemLinearly(BasicSubsystem basicSubsystem, double wantedSpeed, double time) {
		this(basicSubsystem, () -> wantedSpeed, time);
	}

	@Override
	public void execute() {
		currentSpeed = currentSpeed + acceleration * time / 50;
		if (currentSpeed > speedSupplier.get())
			currentSpeed = speedSupplier.get();
		basicSubsystem.move(currentSpeed);
	}

}
