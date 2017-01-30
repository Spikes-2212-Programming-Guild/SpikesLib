package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link TankDrivetrain} by giving each side a different speed.
 *
 * @author Unknown
 */
public class DriveTank extends Command {

    private TankDrivetrain tankDrivetrain;
    private Supplier<Double> leftSpeedSuplier;
    private Supplier<Double> rightSpeedSuplier;

    /**
     * This constructs a new {@link DriveArcade} command
     * that moves each of the given {@link TankDrivetrain}'s sides using a given speed.
     *
     * @param drivetrain the drivetrain this command requires and moves.
     * @param leftSpeed  the speed to move the left side with.
     * @param rightSpeed the speed to move the right side with.
     */
    public DriveTank(TankDrivetrain drivetrain, double leftSpeed, double rightSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this(drivetrain, () -> leftSpeed, () -> rightSpeed);
    }


    /**
     * This constructs a new {@link DriveArcade} command
     * that moves each of the given {@link TankDrivetrain}'s sides using a supplier supplying a changing speed.
     *
     * @param drivetrain         the drivetrain this command requires and moves.
     * @param leftSpeedSupplier  the double {@link Supplier} supplying the speed to move the left side with.
     * @param rightSpeedSupplier the double {@link Supplier} supplying the speed to move the right side with.
     */
    public DriveTank(TankDrivetrain drivetrain, Supplier<Double> leftSpeedSupplier, Supplier<Double> rightSpeedSupplier) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
        this.tankDrivetrain = drivetrain;
        this.leftSpeedSuplier = leftSpeedSupplier;
        this.rightSpeedSuplier = rightSpeedSupplier;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        tankDrivetrain.tankDrive(leftSpeedSuplier.get(), rightSpeedSuplier.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        tankDrivetrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
