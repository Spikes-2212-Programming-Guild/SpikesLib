package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.dashboard.ConstantHandler;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.RobotDrive;

import java.util.function.Supplier;
/**
 * This command is used to make {@link TankDrivetrain} drive on a particular angle.
 * By slowing one side of the drivetrain if {@link GyroDrive#rateSupplier} value is not 0.
 * <p>
 *     This method implements an algorithm that was used in {@link RobotDrive#drive}
 *     to make the robot drive in arcade style by the given rate of the gyro.
 * </p>
 * @author Simon "C" Kharmatsky
 */
public class GyroDrive extends Command {
    private Supplier<Double> sensetivitySupplier =
            ConstantHandler.addConstantDouble("GyroDrive-Sensetivity", 0.5);

    private Supplier<Double> speedSupplier, rateSupplier;
    private TankDrivetrain drivetrain;

    /**
     * This constructs a new {@link GyroDrive} command. using {@link TankDrivetrain} that the command will drive.
     * as well as {@link Supplier<Double>} instances for speed and rate.
     * @param drivetrain {@link TankDrivetrain} instance that the command is driving
     * @param speedSupplier {@link Supplier<Double>} that provides the speed the motors have to move at.
     *                                              this value will be changed if {@link GyroDrive#rateSupplier}
     *                                              returns a value that is not 0.
     * @param rateSupplier {@link Supplier<Double>} instance that provides the rate of the gyro
     */
    public GyroDrive(TankDrivetrain drivetrain, Supplier<Double> speedSupplier, Supplier<Double> rateSupplier) {
        this.drivetrain = drivetrain;
        this.speedSupplier = speedSupplier;
        this.rateSupplier = rateSupplier;
    }

    /**
     * This constructs a new {@link GyroDrive} command. using {@link TankDrivetrain},
     * a static {@link Double} value for the speed and a {@link Supplier<Double>} instance for the rate of the gyro
     * @param drivetrain the drivetrain the command is driving
     * @param speed a static value for the speed the motors have to move at.
     *              this value will be changed if {@link GyroDrive#rateSupplier}
     *              returns a value that is not 0.
     * @param rateSupplier {@link Supplier<Double>} instance that provides the rate of the gyro
     */
    public GyroDrive(TankDrivetrain drivetrain, double speed, Supplier<Double> rateSupplier) {
        this(drivetrain, ()->speed, rateSupplier);
    }

    @Override
    protected void execute() {
        double logValue, ratio, leftSpeed, rightSpeed;
        if (rateSupplier.get() < 0) {
            logValue = Math.log(-rateSupplier.get());
            ratio = (logValue - sensetivitySupplier.get()) / (logValue + sensetivitySupplier.get());
            leftSpeed = speedSupplier.get() / ratio;
            rightSpeed = speedSupplier.get();
        } else if (rateSupplier.get() > 0) {
            logValue = Math.log(rateSupplier.get());
            ratio = (logValue - sensetivitySupplier.get()) / (logValue + sensetivitySupplier.get());
            leftSpeed = speedSupplier.get();
            rightSpeed = speedSupplier.get() / ratio;
        } else {
            rightSpeed = speedSupplier.get();
            leftSpeed = speedSupplier.get();
        }
        drivetrain.tankDrive(leftSpeed, rightSpeed);
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
