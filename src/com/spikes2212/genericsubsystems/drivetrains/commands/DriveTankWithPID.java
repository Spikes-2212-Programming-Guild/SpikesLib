package com.spikes2212.genericsubsystems.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveTankWithPID extends Command {

	private TankDrivetrain TankDrivetrain;
	private double KP;
	private double KI;
	private double KD;
	private double tolerance;
	private Supplier<Double> leftSetpoint;
	private Supplier<Double> rightSetpoint;
	private PIDSource leftSource;
	private PIDSource rightSource;
	private PIDController leftMovmentControl;
	private PIDController rightMovmentControl;
	private double lastTimeNotOnTarget;
	private double waitTime = 0.5;

	/**
	 * Sets the Proportional coefficient of the PID loop of this command.
	 *
	 * @param P
	 *            the new Proportional coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */
	public void setP(double P) {
		KP = P;
	}

	/**
	 * Gets the Proportional coefficient of the PID loop of this command.
	 *
	 * @return the current Proportional coefficient.
	 * @see PIDController#getP()
	 */
	public double getP() {
		return KP;
	}

	/**
	 * Sets the Integral coefficients of the PID loop of this command.
	 *
	 * @param I
	 *            the new Integral coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */
	public void setI(double I) {
		KI = I;
	}

	/**
	 * Gets the Integral coefficient of the PID loop of this command.
	 *
	 * @return the current Integral coefficient.
	 * @see PIDController#getI()
	 */
	public double getI() {
		return KI;
	}

	/**
	 * Sets the Differential coefficient of the PID loop of this command.
	 *
	 * @param D
	 *            the new Differential coefficient.
	 * @see PIDController#setPID(double, double, double)
	 */
	public void setD(double D) {
		KD = D;
	}

	/**
	 * Gets the Differential coefficient of the PID loop of this command.
	 *
	 * @return the current Differential coefficient.
	 * @see PIDController#getD()
	 */
	public double getD() {
		return KD;
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
	 */
	public double getTolerance() {
		return tolerance;
	}

	/**
	 * Sets the tolerance for error of this command.
	 * <p>
	 * This tolerance defines when this command ends: This command will end
	 * after the difference between the setpoint and the current position is
	 * within the tolerance for the amount of time specified by
	 * {@link #setWaitTime(double)} straight.
	 * </p>
	 *
	 * @param tolerance
	 *            The new tolerance to set. If 0, this command will never end.
	 * @see PIDController#setAbsoluteTolerance(double)
	 */
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	/**
	 * Gets the time this command will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting
	 * </p>
	 *
	 * @return the wait time, in seconds. Default is 0.5 seconds.
	 */
	public double getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the time this command will wait while within tolerance of the
	 * setpoint before ending.
	 * <p>
	 * The PID control of the subsystem continues while waiting. <br/>
	 * If wait time is set to 0, the command won't wait.
	 * </p>
	 *
	 * @param waitTime
	 *            the new wait time, in seconds.
	 */
	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param leftSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the left side.
	 * @param rightSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the right side.
	 * @param leftSetpoint
	 *            a supplier supplying the the target point for the left side of
	 *            the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's left side until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            be using the same units as leftSource.
	 *            </p>
	 * @param rightSetpoint
	 *            a supplier supplying the the target point for the right side
	 *            of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's right side until it
	 *            reaches the latest value supplied by setpoint. setpoint should
	 *            be using the same units as rightSource.
	 *            </p>
	 * @param KP
	 *            the Proportional coefficient of the PID loop of this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop of this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop of this command.
	 * @param tolerance
	 *            the tolerance for error of this command. See
	 *            {@link #setTolerance(double)}.
	 * @see PIDController
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource,
			Supplier<Double> leftSetpoint, Supplier<Double> rightSetpoint, double KP, double KI, double KD,
			double tolerance) {
		requires(drivetrain);
		this.TankDrivetrain = drivetrain;
		this.leftSource = leftSource;
		this.rightSource = rightSource;
		this.leftSetpoint = leftSetpoint;
		this.rightSetpoint = rightSetpoint;
		this.KD = KD;
		this.KI = KI;
		this.KP = KP;
		this.tolerance = tolerance;
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param leftSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the left side.
	 * @param rightSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the right side.
	 * @param leftSetpoint
	 *            the target point for the left side of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's left side until it
	 *            reaches the setpoint. setpoint should be using the same units
	 *            as leftSource.
	 *            </p>
	 * @param rightSetpoint
	 *            the target point for the right side of the drivetrain.
	 *            <p>
	 *            This command will try to move drivetrain's right side until it
	 *            reaches the setpoint. setpoint should be using the same units
	 *            as rightSource.
	 *            </p>
	 * @param KP
	 *            the Proportional coefficient of the PID loop of this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop of this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop of this command.
	 * @param tolerance
	 *            the tolerance for error of this command. See
	 *            {@link #setTolerance(double)}.
	 * @see PIDController
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource, double leftSetpoint,
			double rightSetpoint, double KP, double KI, double KD, double tolerance) {
		this(drivetrain, leftSource, rightSource, () -> leftSetpoint, () -> rightSetpoint, KP, KI, KD, tolerance);
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param leftSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the left side.
	 * @param rightSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the right side.
	 * @param setPoint
	 *            the target point of this command.
	 *            <p>
	 *            This command will try to move drivetrain until both sides
	 *            reache the setpoint. setpoint should be using the same units
	 *            as drivetrain's {@link PIDSource}s.
	 *            </p>
	 * @param KP
	 *            the Proportional coefficient of the PID loop of this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop of this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop of this command.
	 * @param tolerance
	 *            the tolerance for error of this command. See
	 *            {@link #setTolerance(double)}.
	 * @see PIDController
	 */
	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource, double setpoint,
			double KP, double KI, double KD, double tolerance) {
		this(drivetrain, leftSource, rightSource, setpoint, setpoint, KP, KI, KD, tolerance);
	}

	/**
	 * This constructs a new {@link DriveTankWithPID} using {@link PIDSource}s
	 * the setpoints for each side, the PID coefficients this command's PID loop
	 * should have, and the tolerance for error.
	 *
	 * @param drivetrain
	 *            the {@link edu.wpi.first.wpilibj.command.Subsystem} this
	 *            command requires and moves.
	 * @param leftSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the left side.
	 * @param rightSource
	 *            the {@link PIDSource} this command uses to get feedback for
	 *            the PID Loop for the right side.
	 * @param setPoint
	 *            a supplier supplying the target point of this command.
	 *            <p>
	 *            This command will try to move drivetrain until both sides
	 *            reache the setpoint. setpoint should be using the same units
	 *            as drivetrain's {@link PIDSource}s.
	 *            </p>
	 * @param KP
	 *            the Proportional coefficient of the PID loop of this command.
	 * @param KI
	 *            the Integral coefficient of the PID loop of this command.
	 * @param KD
	 *            the Differential coefficient of the PID loop of this command.
	 * @param tolerance
	 *            the tolerance for error of this command. See
	 *            {@link #setTolerance(double)}.
	 * @see PIDController
	 */

	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource,
			Supplier<Double> setpoint, double KP, double KI, double KD, double tolerance) {
		this(drivetrain, leftSource, rightSource, setpoint, setpoint, KP, KI, KD, tolerance);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		leftMovmentControl = new PIDController(KP, KI, KD, leftSource, TankDrivetrain::setLeft);
		leftMovmentControl.setAbsoluteTolerance(tolerance);
		leftMovmentControl.setSetpoint(this.leftSetpoint.get());
		leftMovmentControl.setOutputRange(-1, 1);
		rightMovmentControl = new PIDController(KP, KI, KD, rightSource, TankDrivetrain::setRight);
		rightMovmentControl.setAbsoluteTolerance(tolerance);
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
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= waitTime;
	}

	// Called once after isFinished returns true
	protected void end() {
		leftMovmentControl.disable();
		rightMovmentControl.disable();
		TankDrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
