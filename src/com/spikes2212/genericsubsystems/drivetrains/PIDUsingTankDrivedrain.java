package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 */
public abstract class PIDUsingTankDrivedrain extends TankDrivetrain {
	
	/**
     * Configures and returns the default {@link PIDSource} for the left side of this drivetrain.
     *
     * @return the default {@link PIDSource} for the left side. This can be null if the subsystem supplies no default PID source.
     */
    public abstract PIDSource getLeftPIDSource();


    /**
     * Configures and returns the default {@link PIDSource} for the right side of this drivetrain.
     *
     * @return the default {@link PIDSource} for right side. This can be null if the subsystem supplies no default PID source.
     */
    public abstract PIDSource getRightPIDSource();
}

