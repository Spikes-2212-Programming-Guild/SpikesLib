package com.spikes2212.genericsubsystems.basicSubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;

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
	private double startTime;

	public MoveBasicSubsystemLinearly(BasicSubsystem basicSubsystem, Supplier<Double> wantedSpeed, double time) {
		super(basicSubsystem, wantedSpeed);
		if (time <= 0) {
			time = 1;
		}
		this.time = time;
		acceleration = wantedSpeed.get() / time;
	}

	public MoveBasicSubsystemLinearly(BasicSubsystem basicSubsystem, double wantedSpeed, double time) {
		this(basicSubsystem, () -> wantedSpeed, time);
	}

	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
	}

	@Override
	public void execute() {
		currentSpeed = Math.max(speedSupplier.get(), (Timer.getFPGATimestamp() - startTime) * acceleration);
		basicSubsystem.move(currentSpeed);
	}

}
