package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class TurnTankToTargetWithPID extends Command {

    private TankDrivetrain drivetrain;
    private PIDSource source;
    private Supplier<Double> setpointSupplier;
    private PIDSettings settings;

    private PIDController controller;

    private double lastTimeOnTarget = 0;

    public TurnTankToTargetWithPID(TankDrivetrain drivetrain, PIDSource source,
                                   Supplier<Double> setpointSupplier, PIDSettings settings) {
        requires(drivetrain);
        this.drivetrain = drivetrain;
        this.source = source;
        this.setpointSupplier = setpointSupplier;
        this.settings = settings;
    }

    public TurnTankToTargetWithPID(TankDrivetrain drivetrain, PIDSource source,
                                   double setpoint, PIDSettings settings) {
        this(drivetrain, source, ()->setpoint, settings);
    }



    @Override
    protected void initialize() {
        controller = new PIDController(settings.getKP(), settings.getKI(), settings.getKD(), source,
                (rotate) -> drivetrain.arcadeDrive(0.0, rotate));
        controller.setSetpoint(setpointSupplier.get());
        controller.setOutputRange(-1.0, 1.0);
        controller.setAbsoluteTolerance(settings.getTolerance());
        controller.enable();
    }

    @Override
    protected void execute() {
        double newSetpoint = setpointSupplier.get();
        if (controller.getSetpoint() != newSetpoint)
            controller.setSetpoint(newSetpoint);
    }

    @Override
    protected boolean isFinished() {
        if (!controller.onTarget()) lastTimeOnTarget = Timer.getFPGATimestamp();

        return Timer.getFPGATimestamp() - lastTimeOnTarget >= settings.getWaitTime();

    }

    @Override
    protected void end() {
        controller.disable();
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
