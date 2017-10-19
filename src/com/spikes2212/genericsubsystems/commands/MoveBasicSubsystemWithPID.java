package com.spikes2212.genericsubsystems.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.BasicSubsystem;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link BasicSubsystem} using wpilib's
 * {@link PIDController}. It also waits a specified amount of time after the
 * error is within the given tolerance before stopping the PID Loop to make sure
 * the subsystem doesn't go past and remain beyond the setpoint.
 *
 * @author Omri "Riki" and Itamar
 * @see BasicSubsystem
 * @see PIDController
 * @see PIDSettings
 */
public class MoveBasicSubsystemWithPID extends Command {

	private BasicSubsystem basicSubsystem;
	protected PIDSettings PIDSettings;
	protected Supplier<Double> setpoint;
	protected PIDSource source;
	protected PIDController movmentControl;
	protected double lastTimeNotOnTarget;

	/**
	 * Gets the time this command will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 * 
	 * * @see PIDSettings#getWaitTime()
	 *
	 * @return the wait time, in seconds. Default is 0.5 seconds.
	 */
	public double getWaitTime() {
		return PIDSettings.getWaitTime();
	}

	/**
	 * Sets the time this command will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting. <br/>
	 * If wait time is set to 0, the command won't wait.
	 * </p>
	 * 
	 * * @see PIDSettings#getWaitTime()
	 *
	 * @param waitTime
	 *            the new wait time, in seconds.
	 */
	public void setWaitTime(double waitTime) {
		PIDSettings.setWaitTime(waitTime);
	}

	/**
	 * Gets the Proportional coefficient of the PID loop of this command.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 * @see PIDSettings#getKP()
	 */
	public double getP() {
		return PIDSettings.getKP();
	}

	/**
	 * Gets the Integral coefficient of the PID loop of this command.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 * @see PIDSettings#getKI()
	 */
	public double getI() {
		return PIDSettings.getKI();
	}

	/**
	 * Gets the Differential coefficient of the PID loop of this command.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 * @see PIDSettings#getKD()
	 */
	public double getD() {
		return PIDSettings.getKD();
	}

	/**
	 * Gets the tolerance for error of this command.
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
	public double getTolerance() {
		return PIDSettings.getTolerance();
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPID} using a
	 * {@link PIDSource}, a setpoint, the PID coefficients this command's PID
	 * loop should have, and the tolerance for error.
	 *
	 * @param basicSubsystem
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param source
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop.
	 * @param setpoint
	 *            a supplier supplying the target point of this command.
	 *            <p>
	 *            This command will try to move basicSubsystem until it reaches
	 *            the latest value supplied by setpoint. setpoint should supply
	 *            values using the same units as source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see PIDController
	 */
	public MoveBasicSubsystemWithPID(BasicSubsystem basicSubsystem, PIDSource source, Supplier<Double> setpoint,
			PIDSettings PIDSettings) {
		requires(basicSubsystem);
		this.basicSubsystem = basicSubsystem;
		this.source = source;
		this.setpoint = setpoint;
		this.PIDSettings = PIDSettings;
	}

	/**
	 * This constructs a new {@link MoveBasicSubsystemWithPID} using a
	 * {@link PIDSource} given by {@link BasicSubsystem#getPIDSource()}, a
	 * setpoint, the PID coefficients this command's PID loop should have, and
	 * the tolerance for error.
	 *
	 * @param BasicSubsystem
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param source
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop.
	 * @param setpoint
	 *            the target point of this command.
	 *            <p>
	 *            This command will try to move basicSubsystem until it reaches
	 *            the setpoint. setpoint should be using the same units as
	 *            source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 * @see PIDController
	 */
	public MoveBasicSubsystemWithPID(BasicSubsystem BasicSubsystem, PIDSource source, double setpoint,
			PIDSettings PIDSettings) {
		this(BasicSubsystem, source, () -> setpoint, PIDSettings);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		movmentControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				basicSubsystem::move);
		movmentControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movmentControl.setSetpoint(this.setpoint.get());
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double newSetPoint = setpoint.get();
		if (movmentControl.getSetpoint() != newSetPoint)
			movmentControl.setSetpoint(newSetPoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!movmentControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= PIDSettings.getWaitTime();
	}

	// Called once after isFinished returns true
	protected void end() {
		movmentControl.disable();
		basicSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
