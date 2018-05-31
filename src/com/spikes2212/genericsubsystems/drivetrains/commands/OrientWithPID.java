package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

/**
 * This command is used to orient an instance of {@link TankDrivetrain} to a
 * specific setpoint using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>.
 * <br/>
 * 
 * This command only rotates the {@link TankDrivetrain}.
 *
 * @see PIDController
 * @see DriveArcadeWithPID
 *
 * @author Simon "C" Kharmatsky
 */
public class OrientWithPID extends DriveArcadeWithPID {
	private double lastTimeOnTarget = 0;

	/**
	 * This constructs new {@link OrientWithPID} command using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>,
	 * a {@link Supplier} for the setpoint and the {@link PIDSettings} for the
	 * command.
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command operates on
	 * @param PIDSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            that is used by the {@link TankDrivetrain}
	 * @param setpointSupplier
	 *            a {@link Supplier} of doubles for the setpoint of the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 * @param outputRange
	 *            the range of the source's output. For example, gyro's is 360.
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			PIDSettings PIDSettings, double outputRange) {
		super(drivetrain, PIDSource, setpointSupplier, () -> 0.0, PIDSettings, outputRange);
	}

	/**
	 * This constructs new {@link OrientWithPID} command using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>,
	 * a {@link Double} for the setpoint and the {@link PIDSettings} for the
	 * command.
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command operates on
	 * @param PIDSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            that is used by the {@link TankDrivetrain}
	 * @param setpoint
	 *            a set point to get to
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 * @param outputRange
	 *            the range of the source's output. For example, gyro's is 360.
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
