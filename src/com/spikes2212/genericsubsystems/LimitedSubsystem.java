package com.spikes2212.genericsubsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a subsystem that moves linearly between up to two limits.
 * It also makes sure the subsystem doesn't respond to calls that try to move it beyond its defined limits.
 *
 * @author Unkonwn
 */
public abstract class LimitedSubsystem extends Subsystem {

    /**
     * The "Negative" limit of this subsystem; i.e. the limit defining if the subsystem can move using a negative speed.
     *
     * @return true if the subsystem reached its minimum point and cannot move with a negative speed any further.
     */
    public abstract boolean isMin();


    /**
     * The "Positive" limit of this subsystem; i.e. the limit defining if the subsystem can move using a positive speed.
     *
     * @return true if the subsystem reached its maximum point and cannot move with a positive speed any further.
     */
    public abstract boolean isMax();

    /**
     * Configures and returns the default {@link PIDSource} for this subsystem.
     *
     * @return the {@link PIDSource} for the left side.
     */
    public abstract PIDSource getPIDSource();

    /**
     * Moves this subsystem with the given speed.
     *
     * @param speed the speed to move with. Positive values should move this subsystem towards the {@link #isMax()} limit.
     */
    protected abstract void move(double speed);

    /**
     * Checks if this subsystem can move with the given speed.
     *
     * @param speed the speed to check if the subsystem can move with.
     * @return true if the subsytem can move with the given speed, i.e. it hasn't reached the relevant limit.
     */
    public boolean canMove(double speed) {
        return !(speed < 0 && isMin() || speed > 0 && isMax());
    }

    /**
     * Tries to move this subsytem with the given speed. If this subsytem cannot move with the given speed
     * as defined by {@link #canMove(double)}, does nothing.
     *
     * @param speed the speed to try to move with. Values should range between -1 and 1.
     */
    public void tryMove(double speed) {
        if (canMove(speed)) {
            move(speed);
        }
    }

    /**
     * Stops this subsystem's movement.
     * <p>
     * synonym to calling tryMove(0).
     * </p>
     */
    public void stop() {
        move(0);
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub

    }
}
