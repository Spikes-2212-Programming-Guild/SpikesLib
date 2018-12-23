package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.commands.DriveArcade;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 * @author Yuval Levy
 */
public class DriveArcadeWithTwoPIDControllers extends Command {
	protected final TankDrivetrain drivetrain;
	protected final PIDSettings forwardPIDSettings;
	protected final Supplier<Double> forwardSetpoint;
	protected final PIDSource forwardSource;
	protected PIDController forwardMovementControl;
	protected final PIDSettings rotationPIDSettings;
	protected final Supplier<Double> rotationSetpoint;
	protected final PIDSource rotationSource;
	protected PIDController rotationMovementControl;
	protected double lastTimeNotOnTarget;
	protected double inputRange;
	protected boolean continuous;
	private double forwardSpeed;
	private double rotationSpeed;

	public DriveArcadeWithTwoPIDControllers(TankDrivetrain drivetrain, PIDSettings forwardPIDSettings,
			Supplier<Double> forwardSetpoint, PIDSource forwardSource, PIDSettings rotationPIDSettings,
			Supplier<Double> rotationSetpoint, PIDSource rotationSource, double lastTimeNotOnTarget, double inputRange,
			boolean continuous) {
		this.drivetrain = drivetrain;
		this.forwardPIDSettings = forwardPIDSettings;
		this.forwardSetpoint = forwardSetpoint;
		this.forwardSource = forwardSource;
		this.rotationPIDSettings = rotationPIDSettings;
		this.rotationSetpoint = rotationSetpoint;
		this.rotationSource = rotationSource;
		this.lastTimeNotOnTarget = lastTimeNotOnTarget;
		this.inputRange = inputRange;
		this.continuous = continuous;

	}

	public DriveArcadeWithTwoPIDControllers(TankDrivetrain drivetrain, PIDSettings forwardPIDSettings,
			double forwardSetpoint, PIDSource forwardSource, PIDSettings rotationPIDSettings, double rotationSetpoint,
			PIDSource rotationSource, double lastTimeNotOnTarget, double inputRange, boolean continuous) {
		this(drivetrain, forwardPIDSettings, () -> forwardSetpoint, forwardSource, rotationPIDSettings,
				() -> rotationSetpoint, rotationSource, lastTimeNotOnTarget, inputRange, continuous);
	}

	public DriveArcadeWithTwoPIDControllers(TankDrivetrain drivetrain, PIDSettings forwardPIDSettings,
			double forwardSetpoint, PIDSource forwardSource, PIDSettings rotationPIDSettings, double rotationSetpoint,
			PIDSource rotationSource, double lastTimeNotOnTarget) {
		this(drivetrain, forwardPIDSettings, () -> forwardSetpoint, forwardSource, rotationPIDSettings,
				() -> rotationSetpoint, rotationSource, lastTimeNotOnTarget, 0, false);
	}

	public DriveArcadeWithTwoPIDControllers(TankDrivetrain drivetrain, PIDSettings forwardPIDSettings,
			Supplier<Double> forwardSetpoint, PIDSource forwardSource, PIDSettings rotationPIDSettings,
			Supplier<Double> rotationSetpoint, PIDSource rotationSource, double lastTimeNotOnTarget) {
		this(drivetrain, forwardPIDSettings, forwardSetpoint, forwardSource, rotationPIDSettings, rotationSetpoint,
				rotationSource, lastTimeNotOnTarget, 0, false);
	}

	@Override
	protected void initialize() {
		forwardMovementControl = new PIDController(forwardPIDSettings.getKP(), forwardPIDSettings.getKI(),
				forwardPIDSettings.getKD(), forwardSource, (PIDOutput) -> forwardSpeed = PIDOutput);
		forwardMovementControl.setAbsoluteTolerance(forwardPIDSettings.getTolerance());
		forwardMovementControl.setSetpoint(this.forwardSetpoint.get());
		forwardMovementControl.setOutputRange(-1, 1);
		forwardMovementControl.enable();
		rotationMovementControl = new PIDController(rotationPIDSettings.getKP(), rotationPIDSettings.getKI(),
				rotationPIDSettings.getKD(), rotationSource, (PIDOutput1) -> rotationSpeed = PIDOutput1);
		rotationMovementControl.setAbsoluteTolerance(rotationPIDSettings.getTolerance());
		rotationMovementControl.setSetpoint(this.rotationSetpoint.get());
		rotationMovementControl.setOutputRange(-1, 1);
		rotationMovementControl.setInputRange(-inputRange / 2, inputRange / 2);
		rotationMovementControl.setContinuous(this.continuous);
		rotationMovementControl.enable();

	}

	@Override
	protected void execute() {

		drivetrain.arcadeDrive(forwardSpeed, rotationSpeed);
		double newSetPointLeft = forwardSetpoint.get();
		double newSetPointRight = rotationSetpoint.get();
		if (newSetPointLeft != forwardMovementControl.getSetpoint())
			forwardMovementControl.setSetpoint(newSetPointLeft);
		if (newSetPointRight != rotationMovementControl.getSetpoint())
			rotationMovementControl.setSetpoint(newSetPointRight);
	}

	@Override
	protected boolean isFinished() {
		if (!forwardMovementControl.onTarget() || !rotationMovementControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();

		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= rotationPIDSettings.getWaitTime();
	}

	protected void end() {
		rotationMovementControl.disable();
		forwardMovementControl.disable();
		drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}
}
