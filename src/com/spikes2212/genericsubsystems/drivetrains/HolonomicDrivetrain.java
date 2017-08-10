package com.spikes2212.genericsubsystems.drivetrains;

public abstract class HolonomicDrivetrain extends TankDrivetrain {
	public abstract void holonomicDrive(double speedY, double speedX, double turningSpeed); // y-forward/backward
	// x-left/right
	
	@Override
	public void stop(){
    	holonomicDrive(0, 0, 0);
    }

}
