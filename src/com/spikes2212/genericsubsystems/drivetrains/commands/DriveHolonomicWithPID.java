package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link HolonomicDrivetrain} using wpilib's
 * {@link PIDController}. It also waits a specified amount of time after the
 * error is within the given tolerance before stopping the PID Loop to make sure
 * the drivetrain doesn't go past and remain beyond the setpoint.
 *
 * @author Omri "Riki" Cohen
 * @see HolonomicDrivetrain
 * @see PIDController
 * @see PIDSettings
 */
public class DriveHolonomicWithPID extends Command {
	protected final HolonomicDrivetrain holonomicDrivetrain;
	protected final Supplier<Double> XSetpoint;
	protected final Supplier<Double> YSetpoint;
	protected final PIDSettings XPIDSettings;
	protected final PIDSettings YPIDSettings;
	protected final PIDSource XSource;
	protected final PIDSource YSource;
	protected PIDController XMovmentControl;
	protected PIDController YMovmentControl;
	protected double lastTimeNotOnTarget;

	/**
	 * Gets the {@link PIDSettings} the {@link PIDController} uses for the x axis.
	 * 
	 * @return The PIDSetting the PIDController uses
	 * @see PIDSettings
	 * @see PIDController
	 */
	public PIDSettings getXPIDSetting() {
		return XPIDSettings;
	}

	/**
	 * Gets the {@link PIDSettings} the {@link PIDController} uses for the y axis.
	 * 
	 * @return The PIDSetting the PIDController uses
	 * @see PIDSettings
	 * @see PIDController
	 */
	public PIDSettings getYPIDSetting() {
		return XPIDSettings;
	}

	/**
	 * Sets the tolerance for error of this PID loop.
	 * <p>
	 * This tolerance defines when this PID loop ends: This command will end
	 * after the difference between the setpoint and the current position is
	 * within the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)} straight.
	 * </p>
	 *
	 * @param tolerance
	 *            The new tolerance to set. If 0, this PID loop will never end.
	 * @see PIDController#setAbsoluteTolerance(double)
	 * @see PIDSettings#getTolerance
	 */
	public void setTolerance(double tolerance) {
		XPIDSettings.setTolerance(tolerance);
		YPIDSettings.setTolerance(tolerance);
	}

	/**
	 * Sets the time this command will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting. <br/>
	 * If wait time is set to 0, the command won't wait.
	 * </p>
	 * 
	 * @see PIDSettings#setWaitTime(double)
	 *
	 * @param waitTime
	 *            the new wait time, in seconds.
	 */
	public void setWaitTime(double waitTime) {
		YPIDSettings.setWaitTime(waitTime);
		XPIDSettings.setWaitTime(waitTime);
	}

	/**
	 * This constructs a new {@link DrivetrainHolonomicWithPID} using
	 * {@link PIDSource}s the setpoints for each side, the PID coefficients this
	 * command's PID loop should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the x axis.
	 * @param YSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the y axis.
	 * @param XSetpoint
	 *            a supplier supplying the the target point for the x axis of
	 *            the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's x axis until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            be using the same units as XSource.
	 *            </p>
	 * @param YSetpoint
	 *            a supplier supplying the the target point for the y axis of
	 *            the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's y axis until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            be using the same units as YSource.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
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
	 * This constructs a new {@link DriveHolonomicWithPID} using
	 * {@link PIDSource}s the setpoints for each side, the PID coefficients this
	 * command's PID loop should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the X axis.
	 * @param YSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the Y axis.
	 * @param XSetpoint
	 *            the target point for the X axis of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's X axis until it
	 *            reaches the setpoint. setpoint should be using the same units
	 *            as XSource.
	 *            </p>
	 * @param YSetpoint
	 *            the target point for the Y axis of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's Y axis until it
	 *            reaches the setpoint. setpoint should be using the same units
	 *            as YSource.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource, double XSetpoint,
			double YSetpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XSource, YSource, () -> XSetpoint, () -> YSetpoint, XPIDSettings, YPIDSettings);
	}

	/**
	 * This constructs a new {@link DriveHolonomicWithPID} using
	 * {@link PIDSource}s the setpoints for each side, the PID coefficients this
	 * command's PID loop should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the X axis.
	 * @param YSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the Y axis.
	 * @param setPoint
	 *            the target point of this command.
	 *            <p>
	 *            This command will try to move drivetrain until both axes reach
	 *            the setpoint. setpoint should be using the same units as
	 *            drivetrain's {@link PIDSource}s.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource, double setpoint,
			PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XSource, YSource, () -> setpoint, () -> setpoint, XPIDSettings, YPIDSettings);
	}

	/**
	 * This constructs a new {@link DriveHolonomicWithPID} using
	 * {@link PIDSource}s the setpoints for each side, the PID coefficients this
	 * command's PID loop should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the X axis.
	 * @param YSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the Y axis.
	 * @param setPoint
	 *            a supplier supplying the target point of this command.
	 *            <p>
	 *            This command will try to move drivetrain until both axes reach
	 *            the setpoint. setpoint should be using the same units as
	 *            drivetrain's {@link PIDSource}s.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XSource, PIDSource YSource,
			Supplier<Double> setpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XSource, YSource, setpoint, setpoint, XPIDSettings, YPIDSettings);
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
