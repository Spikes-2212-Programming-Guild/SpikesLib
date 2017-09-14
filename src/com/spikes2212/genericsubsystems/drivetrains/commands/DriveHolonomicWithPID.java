package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.HolonomicDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveHolonomicWithPID extends Command{
	private HolonomicDrivetrain holonomicDrivetrain;
	private Supplier<Double> XAxisSetpoint;
	private Supplier<Double> YAxisSetpoint;
	private PIDSettings XAxisPIDSettings;
	private PIDSettings YAxisPIDSettings;
	private PIDSource XAxisSource;
	private PIDSource YAxisSource;
	private PIDController XAxisMovmentControl;
	private PIDController YAxisMovmentControl;
	private double lastTimeNotOnTarget;

	/**
	 * Gets the Proportional coefficient of the PID loop for the x axis of this command.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 * @see PIDSettings#getKP()
	 */

	public double getXAxisP() {
		return XAxisPIDSettings.getKP();
	}

	/**
	 * Gets the Integral coefficient of the PID loop for the x axis of this command.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 * @see PIDSettings#getKI()
	 */

	public double getXAxisI() {
		return XAxisPIDSettings.getKI();
	}

	/**
	 * Gets the Differential coefficient of the PID loop for the x axis of this command.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 * @see PIDSettings#getKD()
	 */

	public double getXAxisD() {
		return XAxisPIDSettings.getKD();
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
		return XAxisPIDSettings.getTolerance();
	}

	/**
	 * Gets the time this command will wait while within tolerance of the x axis
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 *
	 * @see PIDSettings#getWaitTime()
	 *
	 * @return the wait time, in seconds. Default is 0.5 seconds.
	 */

	public double getXAxisWaitTime() {
		return XAxisPIDSettings.getWaitTime();
	}

	/**
	 * Gets the Proportional coefficient of the PID loop for the y axis of this command.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 * @see PIDSettings#getKP()
	 */

	public double getYAxisP() {
		return YAxisPIDSettings.getKP();
	}

	/**
	 * Gets the Integral coefficient of the PID loop for the y axis of this command.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 * @see PIDSettings#getKI()
	 */

	public double getYAxisI() {
		return YAxisPIDSettings.getKI();
	}

	/**
	 * Gets the Differential coefficient of the PID loop for the y axis of this command.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 * @see PIDSettings#getKD()
	 */

	public double getYAxisD() {
		return YAxisPIDSettings.getKD();
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
		return YAxisPIDSettings.getTolerance();
	}

	/**
	 * Gets the time this command will wait while within tolerance of the y axis
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 *
	 * @see PIDSettings#getWaitTime()
	 *
	 * @return the wait time, in seconds. Default is 0.5 seconds.
	 */

	public double getYAxisWaitTime() {
		return YAxisPIDSettings.getWaitTime();
	}

	/**
	 * Sets the time this command will wait while within tolerance of the y axis
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

	public void setYAxisWaitTime(double waitTime) {
		YAxisPIDSettings.setWaitTime(waitTime);
		XAxisPIDSettings.setWaitTime(waitTime);
	}
	
	/**
	 * This constructs a new {@link DrivetrainHolonomicWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the x axis.
	 * @param YAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the y axis.
	 * @param XAxisSetpoint
	 *            a supplier supplying the the target point for the x axis of
	 *            the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's x axis until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            be using the same units as XAxisSource.
	 *            </p>
	 * @param YAxisSetpoint
	 *            a supplier supplying the the target point for the y axis
	 *            of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's y axis until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            be using the same units as YAxisSource.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XAxisSource, PIDSource YAxisSource,
			Supplier<Double> XAxisSetpoint, Supplier<Double> YAxisSetpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		requires(drivetrain);
		this.holonomicDrivetrain = drivetrain;
		this.XAxisSource = XAxisSource;
		this.YAxisSource = YAxisSource;
		this.XAxisSetpoint = XAxisSetpoint;
		this.YAxisSetpoint = YAxisSetpoint;
		this.XAxisPIDSettings = XPIDSettings;
		this.YAxisPIDSettings = YPIDSettings;
	}
	
	/**
	 * This constructs a new {@link DriveHolonomicWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the X axis.
	 * @param YAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the Y axis.
	 * @param XAxisSetpoint
	 *            the target point for the X axis of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's X axis until it
	 *            reaches the setpoint. setpoint should be using the same units
	 *            as XAxisSource.
	 *            </p>
	 * @param YAxisSetpoint
	 *            the target point for the Y axis of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's Y axis until it
	 *            reaches the setpoint. setpoint should be using the same units
	 *            as YAxisSource.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XAxisSource, PIDSource YAxisSource, double XAxisSetpoint,
			double YAxisSetpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XAxisSource, YAxisSource, () -> XAxisSetpoint, () -> YAxisSetpoint, XPIDSettings, YPIDSettings);
	}
	
	/**
	 * This constructs a new {@link DriveHolonomicWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the X axis.
	 * @param YAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the Y axis.
	 * @param setPoint
	 *            the target point of this command.
	 *            <p>
	 *            This command will try to move drivetrain until both axes
	 *            reach the setpoint. setpoint should be using the same units
	 *            as drivetrain's {@link PIDSource}s.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XAxisSource, PIDSource YAxisSource, double setpoint,
			PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XAxisSource, YAxisSource, setpoint, setpoint, XPIDSettings, YPIDSettings);
	}
	
	/**
	 * This constructs a new {@link DriveHolonomicWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param XAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the X axis.
	 * @param YAxisSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the Y axis.
	 * @param setPoint
	 *            a supplier supplying the target point of this command.
	 *            <p>
	 *            This command will try to move drivetrain until both axes
	 *            reach the setpoint. setpoint should be using the same units
	 *            as drivetrain's {@link PIDSource}s.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * 
	 * @see PIDController
	 */
	
	public DriveHolonomicWithPID(HolonomicDrivetrain drivetrain, PIDSource XAxisSource, PIDSource YAxisSource,
			Supplier<Double> setpoint, PIDSettings XPIDSettings, PIDSettings YPIDSettings) {
		this(drivetrain, XAxisSource, YAxisSource, setpoint, setpoint, XPIDSettings, YPIDSettings);
	}
	
	// Called just before this Command runs the first time
		protected void initialize() {
			XAxisMovmentControl = new PIDController(XAxisPIDSettings.getKP(), XAxisPIDSettings.getKI(), XAxisPIDSettings.getKD(),
					XAxisSource, holonomicDrivetrain::setX);
			XAxisMovmentControl.setAbsoluteTolerance(XAxisPIDSettings.getTolerance());
			XAxisMovmentControl.setSetpoint(this.XAxisSetpoint.get());
			XAxisMovmentControl.setOutputRange(-1, 1);
			YAxisMovmentControl = new PIDController(YAxisPIDSettings.getKP(), YAxisPIDSettings.getKI(), YAxisPIDSettings.getKD(),
					YAxisSource, holonomicDrivetrain::setY);
			YAxisMovmentControl.setAbsoluteTolerance(YAxisPIDSettings.getTolerance());
			YAxisMovmentControl.setSetpoint(this.YAxisSetpoint.get());
			YAxisMovmentControl.setOutputRange(-1, 1);
			XAxisMovmentControl.enable();
			YAxisMovmentControl.enable();
		}
		
		// Called repeatedly when this Command is scheduled to run
		protected void execute() {
			double newSetPointLeft = XAxisSetpoint.get();
			double newSetPointRight = YAxisSetpoint.get();
			if (newSetPointLeft != XAxisMovmentControl.getSetpoint())
				XAxisMovmentControl.setSetpoint(newSetPointLeft);
			if (newSetPointRight != YAxisMovmentControl.getSetpoint())
				YAxisMovmentControl.setSetpoint(newSetPointRight);
		}
		
		// Make this return true when this Command no longer needs to run execute()
		protected boolean isFinished() {
			if (!XAxisMovmentControl.onTarget() || !YAxisMovmentControl.onTarget()) {
				lastTimeNotOnTarget = Timer.getFPGATimestamp();
			}
			return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= YAxisPIDSettings.getWaitTime();
		}

		// Called once after isFinished returns true
		protected void end() {
			XAxisMovmentControl.disable();
			YAxisMovmentControl.disable();
			holonomicDrivetrain.stop();
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		protected void interrupted() {
			end();
		}


}
