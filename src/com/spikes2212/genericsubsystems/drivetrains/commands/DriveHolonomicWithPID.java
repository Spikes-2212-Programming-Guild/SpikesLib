package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveHolonomicWithPID extends Command {
	private HolonomicDrivetrain holonomicDrivetrain;
	private Supplier<Double> XSetpoint;
	private Supplier<Double> YSetpoint;
	private PIDSettings XPIDSettings;
	private PIDSettings YPIDSettings;
	private PIDSource XSource;
	private PIDSource YSource;
	private PIDController XMovmentControl;
	private PIDController YMovmentControl;
	private double lastTimeNotOnTarget;

	/**
	 * Gets the Proportional coefficient of the PID loop for the x axis of this
	 * command.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 * @see PIDSettings#getKP()
	 */

	public double getXAxisP() {
		return XPIDSettings.getKP();
	}

	/**
	 * Gets the Integral coefficient of the PID loop for the x axis of this
	 * command.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 * @see PIDSettings#getKI()
	 */

	public double getXAxisI() {
		return XPIDSettings.getKI();
	}

	/**
	 * Gets the Differential coefficient of the PID loop for the x axis of this
	 * command.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 * @see PIDSettings#getKD()
	 */

	public double getXAxisD() {
		return XPIDSettings.getKD();
	}

	/**
	 * Gets the tolerance for error for the x axis of this command.
	 * <p>
	 * This tolerance defines when this command ends: This command will end
	 * after the difference between the setpoint and the current position is
	 * within the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)} straight.
	 * </p>
	 *
	 * @return The current tolerance. If 0, this command will never end.
	 * @see PIDController#setAbsoluteTolerance(double)
	 * @see PIDSettings#getTolerance()
	 */

	public double getXAxisTolerance() {
		return XPIDSettings.getTolerance();
	}

	/**
	 * Gets the Proportional coefficient of the PID loop for the y axis of this
	 * command.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 * @see PIDSettings#getKP()
	 */

	public double getYAxisP() {
		return YPIDSettings.getKP();
	}

	/**
	 * Gets the Integral coefficient of the PID loop for the y axis of this
	 * command.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 * @see PIDSettings#getKI()
	 */

	public double getYAxisI() {
		return YPIDSettings.getKI();
	}

	/**
	 * Gets the Differential coefficient of the PID loop for the y axis of this
	 * command.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 * @see PIDSettings#getKD()
	 */

	public double getYAxisD() {
		return YPIDSettings.getKD();
	}

	/**
	 * Gets the tolerance for error for the y axis of this command.
	 * <p>
	 * This tolerance defines when this command ends: This command will end
	 * after the difference between the setpoint and the current position is
	 * within the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)} straight.
	 * </p>
	 *
	 * @return The current tolerance. If 0, this command will never end.
	 * @see PIDController#setAbsoluteTolerance(double)
	 * @see PIDSettings#getTolerance()
	 */

	public double getYAxisTolerance() {
		return YPIDSettings.getTolerance();
	}

	/**
	 * Gets the time this command will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 *
	 * @see PIDSettings#getWaitTime()
	 *
	 * @return the wait time, in seconds.
	 */

	public double getWaitTime() {
		return Math.max(YPIDSettings.getWaitTime(), XPIDSettings.getWaitTime());
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
