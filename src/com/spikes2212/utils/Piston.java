package com.spikes2212.utils;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Piston extends DoubleSolenoid {

	public Piston(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
	}

	public Piston(int moduleNumber, int forwardChannel, int reverseChannel) {
		super(moduleNumber, forwardChannel, reverseChannel);
	}

	public void open() {
		super.set(Value.kForward);
	}

	public void close() {
		super.set(Value.kReverse);
	}

	public void off() {
		super.set(Value.kOff);
	}

}
