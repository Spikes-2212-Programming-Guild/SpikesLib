package com.spikes2212.utils;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.ArrayList;
import java.util.List;

public class CANGearbox implements SpeedController {
	
	private WPI_TalonSRX master;
	private List<BaseMotorController> followers = new ArrayList<>();
	
	/**
	 * Constructs a new Gearbox. All controllers will follow the first TalonSRX.
	 * @param talon the 'master controller'
	 * @param controllers all other controllers connected to the gearbox
	 */
	public CANGearbox (WPI_TalonSRX talon, BaseMotorController... controllers) {
		master = talon;
		for (BaseMotorController controller : controllers) {
			controller.follow(master);
			followers.add(controller);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(double speed) {
		master.set(speed);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get() {
		return master.get();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInverted(boolean isInverted) {
		master.setInverted(isInverted);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getInverted() {
		return master.getInverted();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disable() {
		master.disable();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopMotor() {
		master.stopMotor();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pidWrite(double output) {
		master.pidWrite(output);
	}
}
