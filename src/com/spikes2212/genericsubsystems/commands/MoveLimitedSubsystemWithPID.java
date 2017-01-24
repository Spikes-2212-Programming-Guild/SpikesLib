package com.spikes2212.genericsubsystems.commands;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveLimitedSubsystemWithPID extends Command {
	private LimitedSubsystem limitedSubsystem;
	private double KP = 1;
	private double KI = 1;
	private double KD = 1;
	private double waitingTime = 1;
	private double tolerance = 1;
	private PIDController movmentControl;
	private double lastFalseTime = 0;

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

	public double getTolarance() {
		return tolerance;
	}

	public void setTolarance(double tolarance) {
		tolerance = tolarance;
	}

	public void setWaitingTime(double waitingTime) {
		this.waitingTime = waitingTime;
	}

	public double getWaitingTime() {
		return waitingTime;
	}

	public MoveLimitedSubsystemWithPID(double setPoint) {
		movmentControl = new PIDController(KP, KI, KD, limitedSubsystem.getPIDSource(), limitedSubsystem::tryMove);
		movmentControl.setAbsoluteTolerance(tolerance);
		movmentControl.setSetpoint(setPoint);
		movmentControl.setOutputRange(-1, 1);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		movmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double currentTime = Timer.getFPGATimestamp();
		if (!movmentControl.onTarget()) {
			lastFalseTime = currentTime;
		}
		if (currentTime - lastFalseTime > waitingTime) {
			return true;
		}
		return false;
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
