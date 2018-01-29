package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

/**
 * This command is used to turn {@link TankDrivetrain} to a specific degree
 * using wpilib's {@link PIDController}.
 *
 * @see TankDrivetrain
 * @see PIDController
 *
 * @author Simon "C" Kharmatsky
 */
public class OrientWithPID extends DriveArcadeWithPID {

	private double lastTimeOnTarget = 0;

	/**
	 * This constructs new {@link OrientWithPID} using {@link PIDSource},
	 * {@link Supplier<Double>} for the setpoint and the {@link PIDSettings} for
	 * the command
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command requires and moves
	 * @param PIDSource
	 *            the {@link PIDSource} that is used by the
	 *            {@link PIDController} to get feedback about the robot's
	 *            position
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the setpoint of the
	 *            {@link PIDController}
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 * @param outputRange
	 *            the range of the source's output (for example, gyro's is 360)s
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			PIDSettings PIDSettings, double outputRange) {
		super(drivetrain, PIDSource, setpointSupplier, () -> 0.0, PIDSettings, outputRange);
	}

	/**
	 * This constructs new {@link OrientWithPID} with constant value for
	 * {@link OrientWithPID#setpointSupplier} using {@link PIDController},
	 * {@link Double} for the setpoint and {@link PIDController} for the command
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command requires and moves
	 * @param PIDSource
	 *            the {@link PIDSource} that is used by the
	 *            {@link PIDController} to get feedback about the robot's
	 *            position
	 * @param setpoint
	 *            constant value for {@link OrientWithPID#setpointSupplier}
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 * @param outputRange
	 *            the range of the source's output (for example, gyro's is 360)
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, double setpoint, PIDSettings PIDSettings,
			double outputRange) {
		this(drivetrain, PIDSource, () -> setpoint, PIDSettings, outputRange);
	}

	@Override
	protected boolean isFinished() {
		if (!rotationController.onTarget())
			lastTimeOnTarget = Timer.getFPGATimestamp();
		return Timer.getFPGATimestamp() - lastTimeOnTarget >= PIDSettings.getWaitTime() || isTimedOut();
	}
}
