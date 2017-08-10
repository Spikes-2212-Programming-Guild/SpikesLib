package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class OmniDrivetrain extends HolonomicDrivetrain {
	
	public abstract void setFront(double speedFront); //right is positive
	 
	public abstract void setRear(double speedRear); //right is positive
	
	public void driveSideways(double speedSideways){
		setFront(speedSideways);
		setRear(speedSideways);
	}
	
	public void turn(double speed){ //right is positive
		setRight(speed);
		setFront(-speed);
		setLeft(-speed);
		setRear(speed);
	}
}

