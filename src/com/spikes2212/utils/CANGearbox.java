package com.spikes2212.utils;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.ArrayList;
import java.util.List;

public class CANGearbox implements SpeedController {
	
	private WPI_TalonSRX master;
	private List<BaseMotorController> followers = new ArrayList<>();
	
	public CANGearbox (WPI_TalonSRX talon, BaseMotorController... controllers) {
		master = talon;
		for (BaseMotorController controller : controllers) {
			controller.follow(master);
			followers.add(controller);
		}
	}
	
	@Override
	public void set(double speed) {
		master.set(speed);
	}
	
	@Override
	public double get() {
		return master.get();
	}
	
	@Override
	public void setInverted(boolean isInverted) {
		master.setInverted(isInverted);
	}
	
	@Override
	public boolean getInverted() {
		return master.getInverted();
	}
	
	@Override
	public void disable() {
		master.disable();
	}
	
	@Override
	public void stopMotor() {
		master.stopMotor();
	}
	
	@Override
	public void pidWrite(double output) {
		master.pidWrite(output);
	}
}
