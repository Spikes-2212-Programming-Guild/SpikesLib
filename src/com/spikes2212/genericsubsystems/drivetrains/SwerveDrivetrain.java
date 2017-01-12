package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class SwerveDrivetrain extends TankDrivetrain {
    
	public abstract void rotateWheels (double speedRotation);
		

	
}

