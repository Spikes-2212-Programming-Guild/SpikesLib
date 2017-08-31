package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a type of Drivetrain with a left and right sides
 * which can be controlled separately, allowing you to turn by giving each side an opposite sign speed,
 * and go forward or backwards by giving both sides the same positive or negative speed; as well as a combination of the two.
 */
public abstract class TankDrivetrain extends Subsystem {
    public void tankDrive(double speedLeft, double speedRight) {
        setLeft(speedLeft);
        setRight(speedRight);
    }

    /**
     * Moves the left side of this drivetrain using speedLeft
     *
     * @param speedLeft The speed to set to the left side. Positive values move this side forward.
     */
    public abstract void setLeft(double speedLeft);


    /**
     * Moves the right side of this drivetrain using speedRight
     *
     * @param speedRight The speed to set to the right side. Positive values move this side forward.
     */
    public abstract void setRight(double speedRight);
    
    /**
     * stops this drivetrain
     * 
     * <p> 
     * it set the speed for both sides to 0.
     * if your drivetrain doesn't stop this way Override this
     * </p>
     */
    public void stop(){
    	setRight(0);
    	setLeft(0);
    }
    
     
}
