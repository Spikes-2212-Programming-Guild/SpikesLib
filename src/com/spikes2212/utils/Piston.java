package com.spikes2212.utils;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Piston extends DoubleSolenoid {

	public Piston(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
	}

	public Piston(int moduleNumber, int forwardChannel, int reverseChannel) {
		super(moduleNumber, forwardChannel, reverseChannel);
	}

	public void Open() {
		super.set(Value.kForward);
	}

	public void Close() {
		super.set(Value.kReverse);
	}

	public void Off() {
		super.set(Value.kOff);
	}

}