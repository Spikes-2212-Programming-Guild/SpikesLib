package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;
import com.spikes2212.genericsubsystems.commands.MoveLimitedSubsystemWithPID;
import com.spikes2212.genericsubsystems.drivetrains.SwerveDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveSwerve extends Command {
	private SwerveDrivetrain swerveDrivetrain;
	private Supplier<Double> speedSuplier;
	private Supplier<Double> wantedAngle;
	private static final double ROTATE_CLOCKWISE_SPEED = 0.5;
	private static final double ROTATE_COUNTER_CLOCKWISE_SPEED = -0.5;

	public DriveSwerve(SwerveDrivetrain swerveDrivetrain, Supplier<Double> speedSuplier, Supplier<Double> wantedAngle) {
		this.swerveDrivetrain = swerveDrivetrain;
		this.speedSuplier = speedSuplier;
		this.wantedAngle = wantedAngle;
	}

	public DriveSwerve(SwerveDrivetrain swerveDrivetrain, double speedSuplier, double wantedAngle) {
		this(swerveDrivetrain, () -> speedSuplier, () -> wantedAngle);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {

		boolean inWantedAngle = true;
		double angleDifference1 = wantedAngle.get() - swerveDrivetrain.getAngle1();
		double angleDifference2 = wantedAngle.get() - swerveDrivetrain.getAngle2();
		double angleDifference3 = wantedAngle.get() - swerveDrivetrain.getAngle3();
		double angleDifference4 = wantedAngle.get() - swerveDrivetrain.getAngle1();
		if (Math.abs(angleDifference1) > swerveDrivetrain.getTolerance()) {
			inWantedAngle = false;
			if (angleDifference1 > 0)
				swerveDrivetrain.rotateWheel(ROTATE_CLOCKWISE_SPEED, 1);
			else
				swerveDrivetrain.rotateWheel(ROTATE_COUNTER_CLOCKWISE_SPEED, 1);
		}
		if (Math.abs(angleDifference2) > swerveDrivetrain.getTolerance()) {
			inWantedAngle = false;
			if (angleDifference2 > 0)
				swerveDrivetrain.rotateWheel(ROTATE_CLOCKWISE_SPEED, 2);
			else
				swerveDrivetrain.rotateWheel(ROTATE_COUNTER_CLOCKWISE_SPEED, 2);
		}
		if (Math.abs(angleDifference3) > swerveDrivetrain.getTolerance()) {
			inWantedAngle = false;
			if (angleDifference3 > 0)
				swerveDrivetrain.rotateWheel(ROTATE_CLOCKWISE_SPEED, 3);
			else
				swerveDrivetrain.rotateWheel(ROTATE_COUNTER_CLOCKWISE_SPEED, 3);
		}
		if (Math.abs(angleDifference4) > swerveDrivetrain.getTolerance()) {
			inWantedAngle = false;
			if (angleDifference4 > 0)
				swerveDrivetrain.rotateWheel(ROTATE_CLOCKWISE_SPEED, 4);
			else
				swerveDrivetrain.rotateWheel(ROTATE_COUNTER_CLOCKWISE_SPEED, 4);
		}
		if(inWantedAngle){
			swerveDrivetrain.setSpeed(speedSuplier.get());
		}

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
