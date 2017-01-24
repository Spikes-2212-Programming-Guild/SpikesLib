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
	private static double KP = 1;
	private static double KI = 1;
	private static double KD = 1;
	private static double waitingTime = 1;
	private static double tolerance = 1;
	private PIDController movmentControl;
	private double lastFalseTime = 0;

	public static void setP(double P) {
		KP = P;
	}

	public static double getP() {
		return KP;
	}

	public static void setI(double I) {
		KI = I;
	}

	public static double getI() {
		return KI;
	}

	public static void setD(double D) {
		KD = D;
	}

	public static double getD() {
		return KD;
	}

	public static double getTolarance() {
		return tolerance;
	}

	public static void setTolarance(double tolarance) {
		tolerance = tolarance;
	}

	public static void setWaitingTime(double newWaitingTime) {
		waitingTime = newWaitingTime;
	}

	public static double getWaitingTime() {
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
