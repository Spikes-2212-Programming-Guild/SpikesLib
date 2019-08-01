package com.spikes2212.command.genericsubsystem;

import java.util.function.*;

public class LambdaSubsystem extends GenericSubsystem {
    public final Predicate<Double> canMove;
    public final Consumer<Double> speedConsumer;
    
    public LambdaSubsystem(Predicate<Double> canMove, Consumer<Double> speedConsumer) {
        super();
        this.canMove = canMove;
        this.speedConsumer = speedConsumer;
    }
    
    public LambdaSubsystem(Predicate<Double> canMove, Consumer<Double> speedConsumer, double minSpeed, double maxSpeed) {
        super(minSpeed, maxSpeed);
        this.canMove = canMove;
        this.speedConsumer = speedConsumer;
    }
    
    @Override
    public void apply(double speed) {
        speedConsumer.accept(speed);
    }
    
    @Override
    public boolean canMove(double speed) {
        return canMove.test(speed);
    }
    
    @Override
    public void stop() {
        apply(0);
    }
    
    @Override
    protected void initDefaultCommand() {}
}
