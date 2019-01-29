package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;
import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link HolonomicDrivetrain} using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>.
 * It also waits a specified amount of time after the
 * error is within the given tolerance before stopping the PID loop to
 * make sure the {@link HolonomicDrivetrain} doesn't go past the setpoint.
 *
 * <br>
 * <br>
 * This command will try to move the {@link HolonomicDrivetrain} until it reaches the latest value
 * supplied by the given setpoint. The setpoint should use values using the same units as
 * the <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources</a>.
 *
 * @author Omri "Riki" Cohen
 * @see HolonomicDrivetrain
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 */
public class DriveHolonomicWithPID extends Command {
	protected final HolonomicDrivetrain holonomicDrivetrain;
	protected final Supplier<Double> XSetpoint;
	protected final Supplier<Double> YSetpoint;
	protected final PIDSettings XPIDSettings;
	protected final PIDSettings YPIDSettings;
	protected PIDController XMovmentControl;
	protected PIDController YMovmentControl;
	protected double lastTimeNotOnTarget;

	/**
	 * The <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 * the X axis of this subsystem uses, given by
	 * {@link BasicSubsystem#getPIDSource()}.
	 */
	protected final PIDSource XSource;

	/**
	 * The <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 * the Y axis of this subsystem uses, given by
	 * {@link BasicSubsystem#getPIDSource()}.
	 */
	protected final PIDSource YSource;

	/**
	 * This constructs a new {@link HolonomicDrivetrain} using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * and {@link Supplier}s for setpoints for each axis of the drivetrain, the
	 * {@link PIDSettings} for command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link HolonomicDrivetrain} this command opperates on
	 * @param XSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the X axis' PID Loop.
	 * @param YSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the Y axis' PID Loop.
	 * @param XSetpoint
	 *            a {@link Supplier} supplying the target point of this
	 *            command's X axis.
	 * @param YSetpoint
	 *            a {@link Supplier} supplying the target point of this
	 *            command's Y axis
	 * @param XPIDSettings
	 *            the {@link PIDSettings} this command's X axis PIDController
	 *            needs.
	 * @param YPIDSettings
	 *            the {@link PIDSettings} this command's Y axis PIDController
	 *            needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource,
			Supplier<Double> XSetpoint, Supplier<Double> YSetpoint, PIDSettings XPIDSettings,
			PIDSettings YPIDSettings) {
		requires(drivetrain);
		this.holonomicDrivetrain = drivetrain;
		this.XSource = XSource;
		this.YSource = YSource;
		this.XSetpoint = XSetpoint;
		this.YSetpoint = YSetpoint;
		this.XPIDSettings = XPIDSettings;
		this.YPIDSettings = YPIDSettings;
	}

	/**
	 * This constructs a new {@link HolonomicDrivetrain} using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * and setpoints for each axis of the drivetrain, the {@link PIDSettings}
	 * for command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link HolonomicDrivetrain} this command opperates on
	 * @param XSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the X axis' PID Loop.
	 * @param YSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the Y axis' PID Loop.
	 * @param XSetpoint
	 *            the target point of this command's X axis.
	 * @param YSetpoint
	 *            the target point of this command's Y axis
	 * @param XPIDSettings
	 *            the {@link PIDSettings} this command's X axis PIDController
	 *            needs.
	 * @param YPIDSettings
	 *            the {@link PIDSettings} this command's Y axis PIDController
	 *            needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource, double XSetpoint,
			double YSetpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XSource, YSource, () -> XSetpoint, () -> YSetpoint, XPIDSettings, YPIDSettings);
	}

	/**
	 * This constructs a new {@link HolonomicDrivetrain} using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * and {@link Supplier}s for setpoints for each axis of the drivetrain, the
	 * {@link PIDSettings} for command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link HolonomicDrivetrain} this command opperates on
	 * @param XSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the X axis' PID Loop.
	 * @param YSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the Y axis' PID Loop.
	 * @param setpoint
	 *            the target point of this command
	 * @param XPIDSettings
	 *            the {@link PIDSettings} this command's X axis PIDController
	 *            needs.
	 * @param YPIDSettings
	 *            the {@link PIDSettings} this command's Y axis PIDController
	 *            needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource, double setpoint,
			PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XSource, YSource, () -> setpoint, () -> setpoint, XPIDSettings, YPIDSettings);
	}

	/**
	 * This constructs a new {@link HolonomicDrivetrain} using <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSources<a>
	 * and {@link Supplier}s for setpoints for each axis of the drivetrain, the
	 * {@link PIDSettings} for command's PID loop, and it's tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link HolonomicDrivetrain} this command opperates on
	 * @param XSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the X axis' PID Loop.
	 * @param YSource
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the Y axis' PID Loop.
	 * @param setpoint
	 *            a {@link Supplier} supplying the target point of this command
	 * @param XPIDSettings
	 *            the {@link PIDSettings} this command's X axis PIDController
	 *            needs.
	 * @param YPIDSettings
	 *            the {@link PIDSettings} this command's Y axis PIDController
	 *            needs.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource,
			Supplier<Double> setpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XSource, YSource, setpoint, setpoint, XPIDSettings, YPIDSettings);
	}

	/**
	 * Gets the {@link PIDSettings} the X axis <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 * uses for this command.
	 * 
	 * @return The PIDSetting object
	 */
	public PIDSettings getXPIDSetting() {
		return XPIDSettings;
	}

	/**
	 * Gets the {@link PIDSettings} the Y axis <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 * uses for this command.
	 * 
	 * @return The PIDSetting object
	 */
	public PIDSettings getYPIDSetting() {
		return XPIDSettings;
	}

	/**
	 * Sets the tolerance for error of this PID loop.
	 * 
	 * <br>
	 * <br>
	 * This tolerance defines when this PID loop ends: this command will end
	 * after the difference between the setpoint and the current position is
	 * within the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)}.
	 * 
	 * <br>
	 * <br>
	 * <b>Warning:</b> If tolerance is set to 0 and the wait time is not 0, this
	 * PID loop will never end unless you cancel it.
	 *
	 * @param tolerance
	 *            the tolerance in the same units as the {@link #source}.
	 * 
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public void setTolerance(double tolerance) {
		XPIDSettings.setTolerance(tolerance);
		YPIDSettings.setTolerance(tolerance);
	}

	/**
	 * Sets the time this command will wait while within tolerance of the
	 * setpoint before ending.
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
		YPIDSettings.setWaitTime(waitTime);
		XPIDSettings.setWaitTime(waitTime);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		XMovmentControl = new PIDController(XPIDSettings.getKP(), XPIDSettings.getKI(), XPIDSettings.getKD(), XSource,
				holonomicDrivetrain::setX);
		XMovmentControl.setAbsoluteTolerance(XPIDSettings.getTolerance());
		XMovmentControl.setSetpoint(this.XSetpoint.get());
		XMovmentControl.setOutputRange(-1, 1);
		YMovmentControl = new PIDController(YPIDSettings.getKP(), YPIDSettings.getKI(), YPIDSettings.getKD(), YSource,
				holonomicDrivetrain::setY);
		YMovmentControl.setAbsoluteTolerance(YPIDSettings.getTolerance());
		YMovmentControl.setSetpoint(this.YSetpoint.get());
		YMovmentControl.setOutputRange(-1, 1);
		XMovmentControl.enable();
		YMovmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double newSetPointLeft = XSetpoint.get();
		double newSetPointRight = YSetpoint.get();
		if (newSetPointLeft != XMovmentControl.getSetpoint())
			XMovmentControl.setSetpoint(newSetPointLeft);
		if (newSetPointRight != YMovmentControl.getSetpoint())
			YMovmentControl.setSetpoint(newSetPointRight);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!XMovmentControl.onTarget() || !YMovmentControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= Math.max(YPIDSettings.getWaitTime(),
				XPIDSettings.getWaitTime());
	}

	// Called once after isFinished returns true
	protected void end() {
		XMovmentControl.disable();
		YMovmentControl.disable();
		holonomicDrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
