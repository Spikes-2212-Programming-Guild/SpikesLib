package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;

import edu.wpi.first.wpilibj.Timer;

public class MoveBasicSubsystemSitickingToLimit extends MoveBasicSubsystem {
	private double waitTime;
	private double lastFalseTime = 0;

	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, Supplier<Double> speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	public MoveBasicSubsystemSitickingToLimit(BasicSubsystem basicSubsystem, double speed, double waitTime) {
		super(basicSubsystem, speed);
		this.waitTime = waitTime;
	}

	@Override
	protected boolean isFinished() {
		double currentTime = Timer.getMatchTime();
		if (!super.isFinished()) {
			lastFalseTime = currentTime;
		}
		if (currentTime - lastFalseTime > waitTime) {
			return true;
		}
		return false;
	}

}
