package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

/**
 * This command moves {@link TankDrivetrain} instance as strait as possible with
 * wpilib's {@link PIDController} using the output from {@link GyroBase instance} and
 * {@link TankDrivetrain#arcadeDrive}
 *
 * @see PIDController
 * @see TankDrivetrain
 * @see GyroBase
 * @author Simon "C" Kharmatsky
 */
public class DriveArcadeWithPID extends Command{

    private TankDrivetrain drivetrain;
    private PIDSource source;
    private Supplier<Double> setpointSupplier;
    private Supplier<Double> speedSupplier;
    private Supplier<Boolean> isFinishedSupplier;
    private PIDSettings settings;

    private PIDController angleController;

    /**
     * This constructs a new {@link DriveArcadeWithPID} using
     * {@link GyroBase}, {@link Supplier<Double>}s for the setpoint and the speed,
     * and the {@link PIDSettings} for this command
     * @param drivetrain the {@link DriveArcadeWithPID} this command requires and moves
     * @param source the {@link PIDSource} that this command uses to get feedback about the
     *               {@link DriveArcadeWithPID}'s degree
     * @param setpointSupplier {@link Supplier<Double>} for the degree the robot has to be at
     * @param speedSupplier {@link Supplier<Double>} supplier of the speed for
     *                                              {@link TankDrivetrain#arcadeDrive}
     * @param isFinishedSupplier {@link Supplier<Boolean>} that checks if the command is finished
     * @param settings {@link PIDSettings} for this command
     */
    public DriveArcadeWithPID(TankDrivetrain drivetrain, GyroBase source, Supplier<Double> setpointSupplier,
                                  Supplier<Double> speedSupplier, Supplier<Boolean> isFinishedSupplier, PIDSettings settings) {
        requires(drivetrain);
        this.drivetrain = drivetrain;
        this.source = source;
        this.setpointSupplier = setpointSupplier;
        this.speedSupplier = speedSupplier;
        this.isFinishedSupplier = isFinishedSupplier;
        this.settings = settings;
    }

    /**
     * This constructs a new {@link DriveArcadeWithPID} using static values
     * for {@link DriveArcadeWithPID#setpointSupplier} and
     * {@link DriveArcadeWithPID#speedSupplier} instead of {@link Supplier<Double>}s
     * @param drivetrain the {@link DriveArcadeWithPID} this command requires and moves
     * @param source the {@link PIDSource} that this command uses to get feedback about the
     *               {@link DriveArcadeWithPID#drivetrain}'s degree
     * @param setpoint static value for {@link DriveArcadeWithPID#setpointSupplier}
     * @param speed static value for {@link DriveArcadeWithPID#speedSupplier}
     * @param isFinishedSupplier {@link Supplier<Boolean>} that checks if the command is finished
     * @param settings @link PIDSettings} for this command
     */
    public DriveArcadeWithPID(TankDrivetrain drivetrain, GyroBase source,
                                  double setpoint, double speed, Supplier<Boolean> isFinishedSupplier, PIDSettings settings) {
        this(drivetrain, source, () -> setpoint, () -> speed, isFinishedSupplier, settings);
    }

    /**
     * This constructs a new {@link DriveArcadeWithPID}
     * ignoring the {@link DriveArcadeWithPID#isFinishedSupplier}
     * @param drivetrain the {@link DriveArcadeWithPID} this command requires and moves
     *
     * @param source the {@link PIDSource} that this command uses to get feedback about the
     *               {@link DriveArcadeWithPID#drivetrain}'s degree
     * @param setpointSupplier {@link Supplier<Double>} for the degree the robot has to be at
     * @param speedSupplier supplier of the speed for
     *                                              {@link TankDrivetrain#arcadeDrive}
     * @param settings {@link PIDSettings} for this command
     */
    public DriveArcadeWithPID(TankDrivetrain drivetrain, GyroBase source, Supplier<Double> setpointSupplier,
                                  Supplier<Double> speedSupplier, PIDSettings settings) {
        this(drivetrain, source, setpointSupplier, speedSupplier, () -> false , settings);

    }

    /**
     * This constructs a new {@link DriveArcadeWithPID}
     * ignoring the {@link DriveArcadeWithPID#isFinishedSupplier} and uses
     * constant values for {@link DriveArcadeWithPID#setpointSupplier}
     * and {@link DriveArcadeWithPID#speedSupplier}
     * @param drivetrain the {@link DriveArcadeWithPID} this command requires and moves
     * @param source the {@link PIDSource} that this command uses to get feedback about the
     *               {@link DriveArcadeWithPID#drivetrain}'s degree
     * @param setpoint constant value for {@link DriveArcadeWithPID#setpointSupplier}
     * @param speed constant value for {@link DriveArcadeWithPID#speedSupplier}
     * @param settings {@link PIDSettings} for this command
     */
    public DriveArcadeWithPID(TankDrivetrain drivetrain, GyroBase source, double setpoint,
                                  double speed, PIDSettings settings) {
        this(drivetrain, source, ()-> setpoint, ()->speed, settings);
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
        return isTimedOut() || isFinishedSupplier.get();
    }
}