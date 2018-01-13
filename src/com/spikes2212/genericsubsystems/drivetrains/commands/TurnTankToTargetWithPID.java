package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
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
    protected boolean isFinished() {
        return false;
    }
}
