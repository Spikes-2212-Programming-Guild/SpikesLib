package com.spikes2212.genericsubsystem.solenoid;

import java.util.function.Supplier;

import com.spikes2212.utils.Piston;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PistonSubsystem extends Subsystem {
	protected Piston piston;
	
	public PistonSubsystem(String name, Piston piston) {
		super(name);
		this.piston = piston;

	}

	public PistonSubsystem(Piston piston) {
		this.piston = piston;
	}
	
	public int getSpeed() {
		if(piston.get() == Value.kForward)
			return 1;
		if(piston.get() == Value.kReverse)
			return -1;
		
		return 0;
	}
	
	public void stop() {
		piston.off();
	}

	public boolean canMove(int speed) {
		if (speed > 0 && piston.get() != Value.kForward || speed < 0 && piston.get() != Value.kReverse || speed == 0) {
			return true;
		}
		return false;
	}

	public void open() {
		if(canMove(1)) {
			piston.open();
		}
	}
	
	public void close() {
		if(canMove(-1)) {
			piston.close();
		}
	}
	
	public void off() {
		piston.off();
	}

	public void setDefaultCommand(Command defaultCommand) {
		super.setDefaultCommand(defaultCommand);
	}

	public void initDefaultCommand() {

	}

}
