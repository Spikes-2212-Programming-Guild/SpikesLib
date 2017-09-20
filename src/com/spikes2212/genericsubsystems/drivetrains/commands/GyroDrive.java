package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.dashboard.ConstantHandler;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;
/**
 * @author Simon "C" Kharmatsky
 */
public class GyroDrive extends Command {
    private Supplier<Double> sensetivitySupplier =
            ConstantHandler.addConstantDouble("GyroDrive-Sensetivity", 0.5);

    private Supplier<Double> speedSupplier, rateSupplier;
    private TankDrivetrain drivetrain;
    public GyroDrive(TankDrivetrain drivetrain, Supplier<Double> speedSupplier, Supplier<Double> rateSupplier) {
        this.drivetrain = drivetrain;
        this.speedSupplier = speedSupplier;
        this.rateSupplier = rateSupplier;
    }

    public GyroDrive(TankDrivetrain drivetrain, double speed, Supplier<Double> rateSupplier) {
        this(drivetrain, ()->speed, rateSupplier);
    }

    @Override
    protected void execute() {
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
        return isTimedOut();
    }
}
