package com.spikes2212.genericsubsystems;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class represents a subsystem that moves linearly between up to two limits.
 * It also makes sure the subsystem doesn't respond to calls that try to move it beyond its defined limits.
 *
 * @author Omri "Riki" Cohen and Itamar
 */
public class LimitedSubsystem extends BasicSubsystem {
	
	private Supplier<Boolean> downLimit; 
	private Supplier<Boolean> upLimit; 

	public LimitedSubsystem(Consumer<Double> movingSpeed, Supplier<Boolean> downLimit, Supplier<Boolean> upLimit){
		super(movingSpeed);
		this.downLimit=downLimit;
		this.upLimit=upLimit;
	}
    /**
     * The "Negative" limit of this subsystem; i.e. the limit defining if the subsystem can move using a negative speed.
     *
     * @return true if the subsystem reached its minimum point and cannot move with a negative speed any further.
     */
    public boolean isMin(){
    	return downLimit.get();
    }
    
    /**
     * The "Positive" limit of this subsystem; i.e. the limit defining if the subsystem can move using a positive speed.
     *
     * @return true if the subsystem reached its maximum point and cannot move with a positive speed any further.
     */
    public boolean isMax(){
    	return upLimit.get();
    }

    /**
     * Checks if this subsystem can move with the given speed.
     *
     * @param speed the speed to check if the subsystem can move with.
     * @return true if the subsystem can move with the given speed, i.e. it hasn't reached the relevant limit.
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
}
