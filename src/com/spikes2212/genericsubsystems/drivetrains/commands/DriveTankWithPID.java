package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link TankDrivetrain} using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>.
 * It also waits a specified amount of time after the error is within the given
 * tolerance before stopping the PID loop to make sure the
 * {@link TankDrivetrain} doesn't go past the setpoint.
 *
 * <br>
 * <br>
 * This command will try to move {@link TankDrivetrain} until it reaches the
 * latest value supplied by the given setpoint. The setpoint should use values
 * using the same units as the <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources</a>.
 *
 * @author Omri "Riki" Cohen
 * @see TankDrivetrain
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 **/
public class DriveTankWithPID extends Command {

	protected final TankDrivetrain tankDrivetrain;
	protected final Supplier<Double> leftSetpoint;
	protected final Supplier<Double> rightSetpoint;
	protected final PIDSettings PIDSettings;
	protected PIDController leftMovmentControl;
	protected PIDController rightMovmentControl;
	protected double lastTimeNotOnTarget;

	protected final PIDSource leftSource;

	protected final PIDSource rightSource;

	/**
	 * This constructs a new {@link DriveTankWithPID} command using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * and {@link Supplier}s for setpoints for each side of the drivetrain, the
	 * {@link PIDSettings} for command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command opperates on
	 * @param leftSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the left side's PID Loop.
	 * @param rightSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the right side's PID Loop.
	 * @param leftSetpoint
	 *            a supplier supplying the target point of this command's left side.
	 *            <p>
 	 *            This command will try to move the drivetrain's left side until it
 	 *            reaches the latest value supplied by setpoint. setpoint should
 	 *            be using the same units as leftSource.
 	 *            </p>
	 * @param rightSetpoint
	 *            a supplier supplying the target point of this command's right
	 *            side.
	 *             <p>
 	 *            This command will try to move the drivetrain's right side until it
 	 *            reaches the latest value supplied by setpoint. setpoint should
 	 *            be using the same units as rightSource.
 	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource,
			Supplier<Double> leftSetpoint, Supplier<Double> rightSetpoint, PIDSettings PIDSettings) {
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.leftSource = leftSource;
		this.rightSource = rightSource;
		this.leftSetpoint = leftSetpoint;
		this.rightSetpoint = rightSetpoint;
		this.PIDSettings = PIDSettings;
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} command using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * and setpoints for each side of the drivetrain, the {@link PIDSettings} for
	 * command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command opperates on
	 * @param leftSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the left side's PID Loop.
	 * @param rightSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the right side's PID Loop.
	 * @param leftSetpoint
	 *            the target point of this command's left side.
	 *            <p>
 	 *            This command will try to move the drivetrain's left side until it
 	 *            reaches the latest value supplied by setpoint. setpoint should
 	 *            be using the same units as leftSource.
 	 *            </p>
	 * @param rightSetpoint
	 *            the target point of this command's right side.
	 *             <p>
 	 *            This command will try to move the drivetrain's right side until it
 	 *            reaches the latest value supplied by setpoint. setpoint should
 	 *            be using the same units as rightSource.
 	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource, double leftSetpoint,
			double rightSetpoint, PIDSettings PIDSettings) {
		this(drivetrain, leftSource, rightSource, () -> leftSetpoint, () -> rightSetpoint, PIDSettings);
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * for each side of the drivetrain, {@link Supplier} for the setpoint, the
	 * {@link PIDSettings} for command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command opperates on
	 * @param leftSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the left side's PID Loop.
	 * @param rightSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the right side's PID Loop.
	 * @param setpoint
	 *            a supplier supplying the target point of this command.
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource, double setpoint,
			PIDSettings PIDSettings) {
		this(drivetrain, leftSource, rightSource, () -> setpoint, () -> setpoint, PIDSettings);
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} command using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * for each side of the drivetrain, a setpoint, the {@link PIDSettings} for
	 * command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link TankDrivetrain} this command opperates on
	 * @param leftSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the left side's PID Loop.
	 * @param rightSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the right side's PID Loop.
	 * @param setpoint
	 *            the target point of this command.
	 *             <p>
 	 *            This command will try to move the drivetrain until it
 	 *            reaches the latest value supplied by setpoint. setpoint should
 	 *            be using the same units as rightSource/LeftSource.
 	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource,
			Supplier<Double> setpoint, PIDSettings PIDSettings) {
		this(drivetrain, leftSource, rightSource, setpoint, setpoint, PIDSettings);
	}

	/**
	 * Gets the {@link PIDSettings} the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 * uses for this command.
	 * 
	 * @return The PIDSetting object
	 */
	public PIDSettings getPIDSetting() {
		return PIDSettings;
	}

	/**
	 * Sets the tolerance for error of this PID loop.
	 * 
	 * <br>
	 * <br>
	 * This tolerance defines when this PID loop ends: this command will end after
	 * the difference between the setpoint and the current position is within the
	 * tolerance for the amount of time specified by {@link #setWaitTime(double)}.
	 * 
	 * <br>
	 * <br>
	 *
	 * @param tolerance
	 *            the tolerance in the same units as the {@link #source}.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public void setTolerance(double tolerance) {
		PIDSettings.setTolerance(tolerance);
	}

	/**
	 * Sets the time this command will wait while within tolerance of the setpoint
	 * before ending.
	 * 
	 * <br>
	 * <br>
	 * The PID control of the subsystem continues while waiting. <br/>
	 * If wait time is set to 0, the command will not wait after reaching the
	 * setpoint.
	 *
	 * @param waitTime
	 *            the new wait time, in seconds. Positive values only.
	 *
	 * @see PIDSettings#getWaitTime()
	 */
	public void setWaitTime(double waitTime) {
		PIDSettings.setWaitTime(waitTime);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		leftMovmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(),
				leftSource, tankDrivetrain::setLeft);
		leftMovmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		leftMovmentControl.setSetpoint(this.leftSetpoint.get());
		leftMovmentControl.setOutputRange(-1, 1);
		rightMovmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(),
				rightSource, tankDrivetrain::setRight);
		rightMovmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		rightMovmentControl.setSetpoint(this.rightSetpoint.get());
		rightMovmentControl.setOutputRange(-1, 1);
		leftMovmentControl.enable();
		rightMovmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double newSetPointLeft = leftSetpoint.get();
		double newSetPointRight = rightSetpoint.get();
		if (newSetPointLeft != leftMovmentControl.getSetpoint())
			leftMovmentControl.setSetpoint(newSetPointLeft);
		if (newSetPointRight != rightMovmentControl.getSetpoint())
			rightMovmentControl.setSetpoint(newSetPointRight);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!leftMovmentControl.onTarget() || !rightMovmentControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= PIDSettings.getWaitTime();
	}

	// Called once after isFinished returns true
	protected void end() {
		leftMovmentControl.disable();
		rightMovmentControl.disable();
		tankDrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
