package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTankWithPID extends Command {
	// TODO Auto-generated constructor stub

	private TankDrivetrain tankDrivetrain;
	private double KP = 1;
	private double KI = 1;
	private double KD = 1;
	private double waitingTime = 1;
	private double tolerance = 1;
	private PIDController leftMovmentControl;
	private PIDController rightMovmentControl;
	private double lastFalseTimeLeft = 0;
	private double lastFalseTimeRight = 0;

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

	public void setWaitingTime(double waitingTime) {
		this.waitingTime = waitingTime;
	}

	public double getWaitingTime() {
		return waitingTime;
	}

	public void setTolarance(double tolarance) {
		this.tolerance = tolarance;
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
