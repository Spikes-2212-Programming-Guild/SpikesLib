package com.spikes2212.pathfinder;

import com.spikes2212.command.drivetrains.EncoderDrivetrain;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.function.Supplier;

public class FollowTrajectory extends Command {

    private EncoderDrivetrain drivetrain;
    private ADXRS450_Gyro gyro;
    private PIDSettings settings;
    private Supplier<Double> gyro_kp;
    private Trajectory trajectory, leftTrajectory, rightTrajectory;
    private TankModifier modifier;
    private EncoderFollower leftFollower, rightFollower;
    private double width, wheelDiameter;

    public FollowTrajectory(EncoderDrivetrain drivetrain, ADXRS450_Gyro gyro, PIDSettings settings
            , Supplier<Double> gyro_kp, Trajectory trajectory, double width, double wheelDiameter) {
        requires(drivetrain);
        this.drivetrain = drivetrain;
        this.gyro = gyro;
        this.settings = settings;
        this.gyro_kp = gyro_kp;
        this.trajectory = trajectory;
        this.width = width;
        this.wheelDiameter = wheelDiameter;
        modifier = new TankModifier(trajectory);
        modifier.modify(width);
        leftTrajectory = modifier.getLeftTrajectory();
        rightTrajectory = modifier.getRightTrajectory();
        leftFollower = new EncoderFollower(leftTrajectory);
        rightFollower = new EncoderFollower(rightTrajectory);
        leftFollower.configurePIDVA(settings.getKP(), 0.0, settings.getKD(), 1.0, 0.0);
        rightFollower.configurePIDVA(settings.getKP(), 0.0, settings.getKD(), 1.0, 0.0);
    }

    @Override
    protected void initialize() {
        drivetrain.getLeftEncoder().reset();
        drivetrain.getRightEncoder().reset();
        gyro.reset();
        leftFollower.reset();
        rightFollower.reset();
        leftFollower.configureEncoder(drivetrain.getLeftEncoder().get()
                , (int)((Math.PI*wheelDiameter)/drivetrain.getLeftEncoder().getDistancePerPulse())
                , wheelDiameter);
        rightFollower.configureEncoder(drivetrain.getRightEncoder().get()
                , (int)((Math.PI*wheelDiameter)/drivetrain.getRightEncoder().getDistancePerPulse())
                , wheelDiameter);
    }

    @Override
    protected void execute() {
        double leftCalculate = leftFollower.calculate(drivetrain.getLeftEncoder().get());
        double rightCalculate = rightFollower.calculate(drivetrain.getRightEncoder().get());
        double angleError = Pathfinder.boundHalfDegrees(gyro.getAngle()
                - Pathfinder.r2d(leftFollower.getHeading())) % 360;
        if (Math.abs(angleError) > 180) angleError = angleError > 0 ? angleError - 360 : angleError + 360;
        angleError *= gyro_kp.get();
        drivetrain.tankDrive(leftCalculate - angleError, rightCalculate + angleError);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }
}
