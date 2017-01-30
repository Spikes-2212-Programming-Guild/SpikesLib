package com.spikes2212.genericsubsystems.drivetrains;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
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
     * Moves the right side of this drivetrain using speedLeft
     *
     * @param speedRight The speed to set to the right side. Positive values move this side forward.
     */
    public abstract void setRight(double speedRight);

    /**
     * Configures and returns the default {@link PIDSource} for the left side of this drivetrain.
     *
     * @return the {@link PIDSource} for the left side.
     */
    public abstract PIDSource getLeftPIDSource();


    /**
     * Configures and returns the default {@link PIDSource} for the right side of this drivetrain.
     *
     * @return the {@link PIDSource} for the right side.
     */
    public abstract PIDSource getRightPIDSource();

}
