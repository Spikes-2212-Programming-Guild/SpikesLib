package com.spikes2212.command.genericsubsystem;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.*;

/**
 * This class represents a generic subsystem that within a limitation or
 * without one, using lambdas for the movement and limitation.
 *
 * @author Eran "Eren" Goldstein
 */
public class LambdaSubsystem extends GenericSubsystem {
    public final Predicate<Double> canMove;
    public final Consumer<Double> speedConsumer;
    
    /**
     * Constructs a new instance of LambdaSubsystem with the given movement.
     *
     * @param speedConsumer a {@link Consumer} that takes speed and applies it to the subsystem
     */
    public LambdaSubsystem(Consumer<Double> speedConsumer) {
        this(speed -> true, speedConsumer);
    }
    
    /**
     * Constructs a new instance of LambdaSubsystem with the given limitations and movement.
     *
     * @param canMove a {@link Predicate} that takes speed and returns whether the subsystem can move
     * @param speedConsumer a {@link Consumer} that takes speed and applies it to the subsystem
     */
    public LambdaSubsystem(Predicate<Double> canMove, Consumer<Double> speedConsumer) {
        this(canMove, speedConsumer, -1, 1);
    }
    
    /**
     * Constructs a new instance of LambdaSubsystem with the given limitations, movement, minSpeed and maxSpeed.
     *
     * @param canMove a {@link Predicate} that takes speed and returns whether the subsystem can move
     * @param speedConsumer a {@link Consumer} that takes speed and applies it to the subsystem
     * @param minSpeed the minimum speed
     * @param maxSpeed he maximum speed
     */
    public LambdaSubsystem(Predicate<Double> canMove, Consumer<Double> speedConsumer, double minSpeed, double maxSpeed) {
        super(minSpeed, maxSpeed);
        this.canMove = canMove;
        this.speedConsumer = speedConsumer;
    }
    
    /**
     * This method applies a given speed to the subsystem, via the speedConsumer lambda.
     *
     * @param speed the speed
     */
    @Override
    public void apply(double speed) {
        speedConsumer.accept(speed);
    }
    
    /**
     * This method returns whether the subsystem can move safely, via the canMove lambda.
     *
     * @param speed the speed
     *
     * @return whether the subsystem can move safely
     */
    @Override
    public boolean canMove(double speed) {
        return canMove.test(speed);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        apply(0);
    }
    
    @Override
    public void setDefaultCommand(Command command) {
        super.setDefaultCommand(command);
    }
    
    @Override
    protected void initDefaultCommand() {}
}
