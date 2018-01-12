package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class DriveTankByGyroWithPID extends Command{

    private TankDrivetrain drivetrain;
    private PIDSource source;
    private Supplier<Double> setpointSupplier;
    private Supplier<Double> arcadeSpeedSupplier;
    private PIDSettings settings;



    public DriveTankByGyroWithPID(TankDrivetrain drivetrain, GyroBase source, Supplier<Double> setpointSupplier,
                                  Supplier<Double> arcadeSpeedSupplier, PIDSettings settings) {
        this.drivetrain = drivetrain;
        this.source = source;
        this.setpointSupplier = setpointSupplier;
        this.arcadeSpeedSupplier = arcadeSpeedSupplier;
        this.settings = settings;
    }

    public DriveTankByGyroWithPID(TankDrivetrain drivetrain, GyroBase source,
                                  double setpoint, double arcadeSpeed, PIDSettings settings) {
        this.drivetrain = drivetrain;
        this.source = source;
        this.setpointSupplier = () -> setpoint;
        this.arcadeSpeedSupplier = () -> arcadeSpeed;
        this.settings = settings;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }


}
