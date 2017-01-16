package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * <a href="http://www.simbotics.org/resources/mobility/drivetrain-selection"> for  information in simbotics site </a>
 * a drivetrain with two sides
 */
public abstract class TankDrivetrain extends Subsystem {
    /**
     * drives the system in with those values.
     * by convention if @speedLeft==@speedRight && @speedLeft>0 the robot will straight. remember to set inverted the right side to fit
     *
     * @param speedLeft the speed to set to the left side
     * @param speedRight the speed to set to the right side
     */
    public void tankDrive(double speedLeft, double speedRight) {
        setLeft(speedLeft);
        setRight(speedRight);
    }

    /**
     * setting the left side with @speedLeft
     * @param speedLeft the value to be set
     */
    public abstract void setLeft(double speedLeft);

    /**
     * setting the right side with @speedRight
     * @param speedRight the value to be set
     */
    public abstract void setRight(double speedRight);

    /**
     *@see PIDSource
     * @return the left PIDSource of the system
     */
    public abstract PIDSource getLeftPIDSource();

    /**
     *@see PIDSource
     * @return the right PIDSource of the system
     */
    public abstract PIDSource getRightPIDSource();

}
