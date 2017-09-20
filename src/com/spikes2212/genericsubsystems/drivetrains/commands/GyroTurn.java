package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj.command.Command;

import java.util.Objects;
import java.util.function.Supplier;

public class GyroTurn extends Command {

    private Supplier<Double> absoluteLeftSpeedSupplier;
    private Supplier<Double> absoluteRightSpeedSupplier;
    private Supplier<Double> targetDegreeSupplier;
    private Supplier<Double> currentDegreeSupplier;

    private TankDrivetrain drivetrain;
    public GyroTurn(TankDrivetrain drivetrain,Supplier<Double> absoluteLeftSpeedSupplier, Supplier<Double> absoluteRightSpeedSupplier,
                    Supplier<Double> targetDegreeSupplier, Supplier<Double> currentDegreeSupplier) {
        this.drivetrain = drivetrain;
        this.absoluteLeftSpeedSupplier = absoluteLeftSpeedSupplier;
        this.absoluteRightSpeedSupplier = absoluteRightSpeedSupplier;
        this.currentDegreeSupplier = currentDegreeSupplier;
        this.targetDegreeSupplier = targetDegreeSupplier;
    }

    public GyroTurn(TankDrivetrain drivetrain, double absoluteLeftSpeed, double absoluteRightSpeed,
                    double targetDegree, Supplier<Double> currentDegreeSupplier ) {
        this(drivetrain, ()->absoluteLeftSpeed, ()->absoluteRightSpeed, ()->targetDegree, currentDegreeSupplier);
    }

    @Override
    protected void execute() {
        if (currentDegreeSupplier.get() > targetDegreeSupplier.get()) {
            drivetrain.tankDrive(absoluteLeftSpeedSupplier.get(), -1 * absoluteRightSpeedSupplier.get());
        } else {
            drivetrain.tankDrive(-1 * absoluteLeftSpeedSupplier.get(), absoluteRightSpeedSupplier.get());
        }
    }

    @Override
    protected void end() {
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return Objects.equals(currentDegreeSupplier.get(), targetDegreeSupplier.get());
    }
}
