package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTankWithPID extends Command {
	// TODO Auto-generated constructor stub

	private TankDrivetrain tankDrivetrain;
	private static double KP = 1;
	private static double KI = 1;
	private static double KD = 1;
	private static double waitingTime = 1;
	private static double tolerance = 1;
	private PIDController leftMovmentControl;
	private PIDController rightMovmentControl;
	private double lastFalseTimeLeft = 0;
	private double lastFalseTimeRight = 0;

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

	public static void setWaitingTime(double newWaitingTime) {
		waitingTime = newWaitingTime;
	}

	public static double getWaitingTime() {
		return waitingTime;
	}

	public static void setTolarance(double tolarance) {
		DriveTankWithPID.tolerance = tolarance;
	}

	public DriveTankWithPID(double setPoint, TankDrivetrain drivetrain) {
		requires(drivetrain);
		leftMovmentControl = new PIDController(KP, KI, KD, tankDrivetrain.getLeftPIDSource(), tankDrivetrain::setLeft);
		leftMovmentControl.setAbsoluteTolerance(tolerance);
		leftMovmentControl.setSetpoint(setPoint);
		leftMovmentControl.setOutputRange(-1, 1);
		rightMovmentControl = new PIDController(KP, KI, KD, tankDrivetrain.getRightPIDSource(), tankDrivetrain::setRight);
		rightMovmentControl.setAbsoluteTolerance(tolerance);
		rightMovmentControl.setSetpoint(setPoint);
		rightMovmentControl.setOutputRange(-1, 1);

	}

	// requires(drivetrain);
	// this.tankDrivetrain = drivetrain;
	// this.setpoint = setpoint;
	// movmentControl = new PIDController(KP, KI, KD, PIDSource, PIDOutput);

	// Called just before this Command runs the first time
	protected void initialize() {
		leftMovmentControl.enable();
		rightMovmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double currentTime = Timer.getFPGATimestamp();
		if (!leftMovmentControl.onTarget()) {
			lastFalseTimeLeft = currentTime;
		}
		if (!rightMovmentControl.onTarget()) {
			lastFalseTimeRight = currentTime;
		}
		if (currentTime - lastFalseTimeLeft > waitingTime && currentTime - lastFalseTimeRight > waitingTime) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		leftMovmentControl.disable();
		rightMovmentControl.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
