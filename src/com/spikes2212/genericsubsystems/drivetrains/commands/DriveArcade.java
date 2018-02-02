package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link TankDrivetrain} using the arcade control method used written by WPILIB.
 *
 * @author Unknown
 */
public class DriveArcade extends Command {
	protected final TankDrivetrain tankDrivetrain;
	protected final Supplier<Double> moveValueSupplier;
	protected final Supplier<Double> rotateValueSupplier;

    /**
     * This constructs a new {@link DriveArcade} command
     * that moves the given {@link TankDrivetrain} forward while also turning according the WPILIB's arcade drive.
     *
     * @param drivetrain  the drivetrain this command requires and moves.
     * @param moveValue   the speed to move forward with. Negative values go backwards.
     * @param rotateValue the speed to turn with. Negative values turn right.
     */
    public DriveArcade(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
        this(drivetrain, () -> moveValue, () -> rotateValue);
    }

    /**
     * This constructs a new {@link DriveArcade} command
     * that moves the given {@link TankDrivetrain} forward while also turning using changing speeds according the WPILIB's arcade drive.
     *
     * @param drivetrain  the drivetrain this command requires and moves.
     * @param moveValueSupplier   the double {@link Supplier} supplying the speed to move forward with. Negative values go backwards.
     * @param rotateValueSupplier the double {@link Supplier} supplying the speed to turn with. Negative values go right.
     */
    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier, Supplier<Double> rotateValueSupplier) {
        requires(drivetrain);
        this.tankDrivetrain = drivetrain;
        this.moveValueSupplier = moveValueSupplier;
        this.rotateValueSupplier = rotateValueSupplier;
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        tankDrivetrain.arcadeDrive(moveValueSupplier.get(), rotateValueSupplier.get());
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
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
