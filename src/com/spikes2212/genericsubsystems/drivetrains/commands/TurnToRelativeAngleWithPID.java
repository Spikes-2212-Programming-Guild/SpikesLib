package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

/**
 * This command is used to turn {@link TankDrivetrain} to a specific degree using
 * wpilib's {@link PIDController}.
 *
 * @see TankDrivetrain
 * @see PIDController
 *
 * @author Simon "C" Kharmatsky
 */
public class TurnToRelativeAngleWithPID extends Command {

    private TankDrivetrain drivetrain;
    private PIDSource source;
    private Supplier<Double> setpointSupplier;
    private PIDSettings settings;

    private PIDController controller;

    private double lastTimeOnTarget = 0;

    /**
     * This constructs new {@link TurnToRelativeAngleWithPID} using
     * {@link PIDSource}, {@link Supplier<Double>} for the setpoint and the
     * {@link PIDSettings} for the command
     * @param drivetrain the {@link TankDrivetrain} this command requires and moves
     * @param source the {@link PIDSource} that is used by the {@link PIDController}
     *               to get feedback about the robot's position
     * @param setpointSupplier {@link Supplier<Double>} for the setpoint of the {@link PIDController}
     * @param settings {@link PIDSettings} for this command
     */
    public TurnToRelativeAngleWithPID(TankDrivetrain drivetrain, PIDSource source,
                                   Supplier<Double> setpointSupplier, PIDSettings settings) {
        requires(drivetrain);
        this.drivetrain = drivetrain;
        this.source = source;
        this.setpointSupplier = setpointSupplier;
        this.settings = settings;
    }

    /**
     * This constructs new {@link TurnToRelativeAngleWithPID} with
     * constant value for {@link TurnToRelativeAngleWithPID#setpointSupplier} using
     * {@link PIDController}, {@link Double} for the setpoint and
     * {@link PIDController} for the command
     * @param drivetrain the {@link TankDrivetrain} this command requires and moves
     * @param source the {@link PIDSource} that is used by the {@link PIDController}
     *               to get feedback about the robot's position
     * @param setpoint constant value for {@link TurnToRelativeAngleWithPID#setpointSupplier}
     * @param settings {@link PIDSettings} for this command
     */
    public TurnToRelativeAngleWithPID(TankDrivetrain drivetrain, PIDSource source,
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
