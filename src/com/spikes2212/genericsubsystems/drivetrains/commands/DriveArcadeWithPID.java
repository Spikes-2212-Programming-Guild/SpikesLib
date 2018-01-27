package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command turns {@link TankDrivetrain} instance with wpilib's
 * {@link PIDController} using the output from {@link PIDSource} and moves it
 * forward using {@link Supplier} and {@link TankDrivetrain#arcadeDrive}. This
 * class can be used to force the instance of {@link TankDrivetrain} move
 * straight by giving a setpoint of 0.
 *
 * @see PIDController
 * @see TankDrivetrain
 * @author Simon "C" Kharmatsky
 */
public class DriveArcadeWithPID extends Command {

	protected TankDrivetrain drivetrain;
	protected PIDSource PIDSource;
	protected PIDSettings PIDSettings;
	protected final Supplier<Double> setpointSupplier;
	protected final Supplier<Double> speedSupplier;
	protected final Supplier<Boolean> isFinishedSupplier;

	protected PIDController rotationController;

	/**
	 * This constructs a new {@link DriveArcadeWithPID} using {@link PIDSource},
	 * {@link Supplier<Double>}s for the setpoint and the speed, and the
	 * {@link PIDSettings} for this command
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 * @param PIDSource
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID}'s position
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the position the robot has to be at
	 * @param speedSupplier
	 *            {@link Supplier<Double>} supplier of the speed for
	 *            {@link TankDrivetrain#arcadeDrive}
	 * @param isFinishedSupplier
	 *            {@link Supplier<Boolean>} that checks if the command is
	 *            finished
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			Supplier<Double> speedSupplier, Supplier<Boolean> isFinishedSupplier, PIDSettings PIDSettings) {
		requires(drivetrain);
		this.drivetrain = drivetrain;
		this.PIDSource = PIDSource;
		this.PIDSettings = PIDSettings;
		this.setpointSupplier = setpointSupplier;
		this.speedSupplier = speedSupplier;
		this.isFinishedSupplier = isFinishedSupplier;
	}

	/**
	 * This constructs a new {@link DriveArcadeWithPID} using static values for
	 * {@link DriveArcadeWithPID#setpointSupplier} and
	 * {@link DriveArcadeWithPID#speedSupplier} instead of
	 * {@link Supplier<Double>}s
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 * @param PIDSource
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID#drivetrain}'s position
	 * @param setpoint
	 *            static value for {@link DriveArcadeWithPID#setpointSupplier}
	 * @param speed
	 *            static value for {@link DriveArcadeWithPID#speedSupplier}
	 * @param isFinishedSupplier
	 *            {@link Supplier<Boolean>} that checks if the command is
	 *            finished
	 * @param PIDSettings
	 * @link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, double setpoint, double speed,
			Supplier<Boolean> isFinishedSupplier, PIDSettings PIDSettings) {
		this(drivetrain, PIDSource, () -> setpoint, () -> speed, isFinishedSupplier, PIDSettings);
	}

	/**
	 * This constructs a new {@link DriveArcadeWithPID} ignoring the
	 * {@link DriveArcadeWithPID#isFinishedSupplier}
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 *
	 * @param PIDSource
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID#drivetrain}'s position
	 * @param setpointSupplier
	 *            {@link Supplier<Double>} for the position the robot has to be at
	 * @param speedSupplier
	 *            supplier of the speed for {@link TankDrivetrain#arcadeDrive}
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, Supplier<Double> setpointSupplier,
			Supplier<Double> speedSupplier, PIDSettings PIDSettings) {
		this(drivetrain, PIDSource, setpointSupplier, speedSupplier, () -> false, PIDSettings);

	}

	/**
	 * This constructs a new {@link DriveArcadeWithPID} ignoring the
	 * {@link DriveArcadeWithPID#isFinishedSupplier} and uses constant values
	 * for {@link DriveArcadeWithPID#setpointSupplier} and
	 * {@link DriveArcadeWithPID#speedSupplier}
	 * 
	 * @param drivetrain
	 *            the {@link DriveArcadeWithPID} this command requires and moves
	 * @param PIDSource
	 *            the {@link PIDSource} that this command uses to get feedback
	 *            about the {@link DriveArcadeWithPID#drivetrain}'s position
	 * @param setpoint
	 *            constant value for {@link DriveArcadeWithPID#setpointSupplier}
	 * @param speed
	 *            constant value for {@link DriveArcadeWithPID#speedSupplier}
	 * @param PIDSettings
	 *            {@link PIDSettings} for this command
	 */
	public DriveArcadeWithPID(TankDrivetrain drivetrain, PIDSource PIDSource, double setpoint, double speed,
			PIDSettings PIDSettings) {
		this(drivetrain, PIDSource, () -> setpoint, () -> speed, PIDSettings);
	}

	@Override
	protected void initialize() {
		this.rotationController = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(),
				PIDSource, (rotate) -> drivetrain.arcadeDrive(speedSupplier.get(), rotate));
		rotationController.setAbsoluteTolerance(PIDSettings.getTolerance());
		rotationController.setSetpoint(setpointSupplier.get());
		rotationController.setOutputRange(-1.0, 1.0);
		rotationController.enable();
	}

	@Override
	protected void execute() {
		double newSetpoint = setpointSupplier.get();
		if (rotationController.getSetpoint() != newSetpoint)
			rotationController.setSetpoint(newSetpoint);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut() || isFinishedSupplier.get();
	}

	@Override
	protected void end() {
		rotationController.disable();
		drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}