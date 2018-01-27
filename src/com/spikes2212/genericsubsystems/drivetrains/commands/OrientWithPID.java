package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.CompassGyro;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

/**
 * This command is used to turn {@link TankDrivetrain} to a specific degree
 * using wpilib's {@link PIDController}.
 *
 * @see TankDrivetrain
 * @see PIDController
 *
 * @author Simon "C" Kharmatsky
 */
public class OrientWithPID extends Command {

	private TankDrivetrain drivetrain;
	private PIDSource PIDSource;
	private Supplier<Double> setpointSupplier;
	private PIDSettings PIDsettings;

	private PIDController PIDcontroller;

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
	 * @param PIDsettings
	 *            {@link PIDSettings} for this command
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			PIDSettings PIDsettings) {
		requires(drivetrain);
		this.drivetrain = drivetrain;
		this.PIDSource = PIDSource;
		this.setpointSupplier = setpointSupplier;
		this.PIDsettings = PIDsettings;
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
	 * @param PIDsettings
	 *            {@link PIDSettings} for this command
	 */
	public OrientWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, double setpoint, PIDSettings PIDsettings) {
		this(drivetrain, PIDSource, () -> setpoint, PIDsettings);
	}

	@Override
	protected void initialize() {
		PIDcontroller = new PIDController(PIDsettings.getKP(), PIDsettings.getKI(), PIDsettings.getKD(), PIDSource,
				(rotate) -> drivetrain.arcadeDrive(0.0, rotate));
		PIDcontroller.setSetpoint(setpointSupplier.get());
		PIDcontroller.setOutputRange(-1.0, 1.0);
		PIDcontroller.setAbsoluteTolerance(PIDsettings.getTolerance());
		PIDcontroller.enable();
	}

	@Override
	protected void execute() {
		double newSetpoint = setpointSupplier.get();
		if (PIDcontroller.getSetpoint() != newSetpoint)
			PIDcontroller.setSetpoint(newSetpoint);
	}

	@Override
	protected boolean isFinished() {
		if (!PIDcontroller.onTarget())
			lastTimeOnTarget = Timer.getFPGATimestamp();

		return Timer.getFPGATimestamp() - lastTimeOnTarget >= PIDsettings.getWaitTime();

	}

	@Override
	protected void end() {
		PIDcontroller.disable();
		drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
