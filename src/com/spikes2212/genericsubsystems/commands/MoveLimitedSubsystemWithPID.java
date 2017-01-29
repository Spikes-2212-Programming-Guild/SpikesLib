package com.spikes2212.genericsubsystems.commands;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveLimitedSubsystemWithPID extends Command {

	private LimitedSubsystem limitedSubsystem;
	private double KP;
	private double KI;
	private double KD;
	private double tolerance;
	private double setpoint;
	private PIDSource source;
	private PIDController movmentControl;
	private double lastTimeNotOnTarget;
	private double waitTime = 0.5;

	public double getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

	public void setP(double P) {
		KP = P;
	}

	public double getP() {
		return KP;
	}

	public void setI(double I) {
		KI = I;
	}

	public double getI() {
		return KI;
	}

	public void setD(double D) {
		KD = D;
	}

	public double getD() {
		return KD;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public MoveLimitedSubsystemWithPID(LimitedSubsystem limitedSubsystem, PIDSource source, double setpoint, double KP,
			double KI, double KD, double tolerance) {
		requires(limitedSubsystem);
		this.limitedSubsystem = limitedSubsystem;
		this.source = source;
		this.setpoint = setpoint;
		this.KD = KD;
		this.KI = KI;
		this.KP = KP;
		this.tolerance = tolerance;
	}

	public MoveLimitedSubsystemWithPID(LimitedSubsystem limitedSubsystem, double setpoint, double KP, double KI,
			double KD, double tolerance) {
		this(limitedSubsystem, limitedSubsystem.getPIDSource(), setpoint, KP, KI, KD, tolerance);
	}

	public MoveLimitedSubsystemWithPID(double setPoint, double KP, double KI, double KD, LimitedSubsystem drivetrain,
			double tolerance) {
		this(drivetrain, setPoint, KP, KI, KD, tolerance);
	}

	// requires(drivetrain);
	// this.tankDrivetrain = drivetrain;
	// this.setpoint = setpoint;
	// movmentControl = new PIDController(KP, KI, KD, PIDSource, PIDOutput);

	// Called just before this Command runs the first time
	protected void initialize() {
		movmentControl = new PIDController(KP, KI, KD, source, limitedSubsystem::tryMove);
		movmentControl.setAbsoluteTolerance(tolerance);
		movmentControl.setSetpoint(this.setpoint);
		movmentControl.setOutputRange(-1, 1);
		movmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!movmentControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= waitTime;
	}

	// Called once after isFinished returns true
	protected void end() {
		movmentControl.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
