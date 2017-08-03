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
    
}

