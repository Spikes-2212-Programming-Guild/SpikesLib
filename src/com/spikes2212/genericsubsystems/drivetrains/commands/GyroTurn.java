package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * This command is used to turn the robot to a target angle using {@link Gyro#getAngle()}.
 * <p>
 *     The main usage of this command will be in autonomous mode,
 *     in situations when the team will decide to turn their robot to a specific angle.
 * </p>
 * @author Simon "C" Kharmatsky
 * @see Gyro
 * @see DriveTank
 */
public class GyroTurn extends Command {

    private Supplier<Double> absoluteLeftSpeedSupplier;
    private Supplier<Double> absoluteRightSpeedSupplier;
    private Supplier<Double> targetAngleSupplier;
    private Supplier<Double> currentAngleSupplier;


    private TankDrivetrain drivetrain;

    /**
     * This constructs a new {@link GyroTurn} command using {@link Supplier<Double>} for left, and right speeds.
     * as well as the current and target angles.
     * @param drivetrain the drivetrain this command has to move
     * @param absoluteLeftSpeedSupplier {@link Supplier<Double>} of the left speed.
     *                                  will be multiplied by -1 if target angle is bigger than current angle
     * @param absoluteRightSpeedSupplier {@link Supplier<Double>} of the right speed.
     *                                   will be multiplied by -1 if target angle is smaller than current angle
     * @param targetAngleSupplier {@link Supplier<Double>} of the angle the robot has to end at after the command executes
     * @param currentAngleSupplier {@link Supplier<Double>} of the current angle of the robot
     */
    public GyroTurn(TankDrivetrain drivetrain,Supplier<Double> absoluteLeftSpeedSupplier, Supplier<Double> absoluteRightSpeedSupplier,
                    Supplier<Double> targetAngleSupplier, Supplier<Double> currentAngleSupplier) {
        this.drivetrain = drivetrain;
        this.absoluteLeftSpeedSupplier = absoluteLeftSpeedSupplier;
        this.absoluteRightSpeedSupplier = absoluteRightSpeedSupplier;
        this.currentAngleSupplier = currentAngleSupplier;
        this.targetAngleSupplier = targetAngleSupplier;
    }

    /**
     * This constructs a new {@link GyroTurn} command using static {@link Double} values for left and right speeds.
     * as well as target angle. and an {@link Supplier<Double>} instance that returns the current angle
     * @param drivetrain the drivetrain the command has to drive
     * @param absoluteLeftSpeed static value for {@link GyroTurn#absoluteLeftSpeedSupplier}
     * @param absoluteRightSpeed static value for {@link GyroTurn#absoluteRightSpeedSupplier}
     * @param targetAngle static value for {@link GyroTurn#targetAngleSupplier}
     * @param currentAngleSupplier {@link Supplier<Double>} of the current angle of the robot
     */
    public GyroTurn(TankDrivetrain drivetrain, double absoluteLeftSpeed, double absoluteRightSpeed,
                    double targetAngle, Supplier<Double> currentAngleSupplier) {
        this(drivetrain, ()->absoluteLeftSpeed, ()->absoluteRightSpeed, ()->targetAngle, currentAngleSupplier);
    }

    @Override
    protected void execute() {
        if (currentAngleSupplier.get() > targetAngleSupplier.get()) {
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
        return Objects.equals(currentAngleSupplier.get(), targetAngleSupplier.get());
    }
}
