package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.CompassGyro;
import com.spikes2212.utils.PIDSettings;
import edu.wpi.first.wpilibj.PIDController;
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
	private CompassGyro compassGyro;
	private Supplier<Double> setpointSupplier;
	private PIDSettings settings;

	private PIDController controller;

	private double lastTimeOnTarget = 0;

	/**
	 * This constructs new {@link OrientWithPID} using {@link PIDSource},
	 * {@link Supplier<Double>} for the setpoint and the {@link PIDSettings} for the
	 * command
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command requires and moves
	 * @param compassGyro
	 *            the {@link PIDSource} that is used by the {@link PIDController} to
	 *            get feedback about the robot's position
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the setpoint of the
	 *            {@link PIDController}
	 * @param settings
	 *            {@link PIDSettings} for this command
	 */
	public OrientWithPID(TankDrivetrain drivetrain, CompassGyro compassGyro,
			Supplier<Double> setpointSupplier, PIDSettings settings) {
		requires(drivetrain);
		this.drivetrain = drivetrain;
		this.compassGyro = compassGyro;
		this.setpointSupplier = setpointSupplier;
		this.settings = settings;
	}

	/**
	 * This constructs new {@link OrientWithPID} with constant value for
	 * {@link OrientWithPID#setpointSupplier} using
	 * {@link PIDController}, {@link Double} for the setpoint and
	 * {@link PIDController} for the command
	 * 
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command requires and moves
	 * @param compassGyro
	 *            the {@link PIDSource} that is used by the {@link PIDController} to
	 *            get feedback about the robot's position
	 * @param setpoint
	 *            constant value for
	 *            {@link OrientWithPID#setpointSupplier}
	 * @param settings
	 *            {@link PIDSettings} for this command
	 */
	public OrientWithPID(TankDrivetrain drivetrain, CompassGyro compassGyro, double setpoint,
			PIDSettings settings) {
		this(drivetrain, compassGyro, () -> setpoint, settings);
	}

	@Override
	protected void initialize() {
		controller = new PIDController(settings.getKP(), settings.getKI(), settings.getKD(), compassGyro,
				(rotate) -> drivetrain.arcadeDrive(0.0, rotate));
		controller.setSetpoint(getSetpoint());
		controller.setOutputRange(-1.0, 1.0);
		controller.setAbsoluteTolerance(settings.getTolerance());
		controller.enable();
	}

	@Override
	protected void execute() {
		double newSetpoint = getSetpoint();
		if (controller.getSetpoint() != newSetpoint)
			controller.setSetpoint(newSetpoint);
	}

	@Override
	protected boolean isFinished() {
		if (!controller.onTarget())
			lastTimeOnTarget = Timer.getFPGATimestamp();

		return Timer.getFPGATimestamp() - lastTimeOnTarget >= settings.getWaitTime();

	}

	@Override
	protected void end() {
		controller.disable();
		drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
	private double getSetpoint(){
		double setpoint = setpointSupplier.get();
		setpoint = setpoint % 360;
		if (Math.abs(setpoint - compassGyro.pidGet()) > 180)
			setpoint -= 360;
		return setpoint;
	}
}
