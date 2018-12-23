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
 * @see DriveArcadeWithBetterPID
 *
 * @author Simon "C" Kharmatsky
 */
public class OrientWithPID extends DriveArcadeWithBetterPID {
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
	 * @param inputRange
	 *            the range of the source's output. For example, gyro's is 360.
	 * @param inputRange
	 *            the range of the source's input. For example, gyro's is 360.
	 *            Camera that has 640 px on the wanted axis has output range of 640,
	 *            and one that its values range was scaled between -1 and 1 has
	 *            output range of 2 and so on.
	 * @param continuous
	 *            true to make the PID controller consider the input to be
	 *            continuous, Rather then using the max and min input range as
	 *            constraints, it considers them to be the same point and
	 *            automatically calculates the shortest route to the setpoint.
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			PIDSettings PIDSettings, double inputRange, boolean continuous) {
		super(drivetrain, PIDSource, setpointSupplier, () -> 0.0, PIDSettings, inputRange, continuous);
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
	 * @param inputRange
	 *            the range of the source's input. For example, gyro's is 360.
	 *            Camera that has 640 px on the wanted axis has output range of 640,
	 *            and one that its values range was scaled between -1 and 1 has
	 *            output range of 2 and so on.
	 * @param continuous
	 *            true to make the PID controller consider the input to be
	 *            continuous, Rather then using the max and min input range as
	 *            constraints, it considers them to be the same point and
	 *            automatically calculates the shortest route to the setpoint.
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, double setpoint, PIDSettings PIDSettings,
			double inputRange, boolean continuous) {
		this(drivetrain, PIDSource, () -> setpoint, PIDSettings, inputRange, continuous);
	}

	@Override
	protected boolean isFinished() {
		if (!rotationController.onTarget())
			lastTimeOnTarget = Timer.getFPGATimestamp();
		return Timer.getFPGATimestamp() - lastTimeOnTarget >= PIDSettings.getWaitTime() || isTimedOut();
	}
}