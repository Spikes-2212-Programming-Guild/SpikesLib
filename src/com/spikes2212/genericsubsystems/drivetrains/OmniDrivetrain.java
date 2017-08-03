package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class OmniDrivetrain extends HolonomicDrivetrain {
	
	public abstract void setForwards(double speedForwards);
	
	public abstract void setRear(double speedRear);
	
	public void driveSideways(double speedForwards, double speedRear){
		setForwards(speedForwards);
		setRear(speedRear);
	}
    
	@Override
	public void holonomicDrive(double speedY, double speedX){
		tankDrive(speedY, speedY);
		driveSideways(speedX, speedX);
	}
	
	@Override
	public void stop(){
		super.stop();
		driveSideways(0, 0);
	}
}

