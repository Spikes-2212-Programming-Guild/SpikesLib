package com.spikes2212.genericsubsystems.commands;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

public class MoveUp extends Command {
	private SpeedController motor;

	public MoveUp(SpeedController motor){
		this.motor= motor;
		
	}
	@Override protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		motor.stop();

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
