package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class DriveTankByGyroWithPID extends Command{

    private TankDrivetrain drivetrain;
    private PIDSource source;
    private Supplier<Double> setpointSupplier;
    private Supplier<Double> speedSupplier;
    private PIDSettings settings;

    private PIDController angleController;

    public DriveTankByGyroWithPID(TankDrivetrain drivetrain, GyroBase source, Supplier<Double> setpointSupplier,
                                  Supplier<Double> speedSupplier, PIDSettings settings) {
        requires(drivetrain);
        this.drivetrain = drivetrain;
        this.source = source;
        this.setpointSupplier = setpointSupplier;
        this.speedSupplier = speedSupplier;
        this.settings = settings;
    }

    public DriveTankByGyroWithPID(TankDrivetrain drivetrain, GyroBase source,
                                  double setpoint, double speed, PIDSettings settings) {
        this(drivetrain, source, () -> setpoint, () -> speed, settings);
    }

    @Override
    protected void initialize() {
        this.angleController = new PIDController(settings.getKP(), settings.getKI(), settings.getKD(), source,
                (rotate) -> drivetrain.arcadeDrive(speedSupplier.get(), rotate));
        angleController.setAbsoluteTolerance(settings.getTolerance());
        angleController.setSetpoint(setpointSupplier.get());
        angleController.setOutputRange(-1.0, 1.0);
        angleController.enable();
    }

    @Override
    protected void execute() {
        double newSetpoint = setpointSupplier.get();
        if (angleController.getSetpoint() != newSetpoint) {
            angleController.setSetpoint(newSetpoint);
        }
    }

    @Override
    protected void end() {
        angleController.disable();
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
