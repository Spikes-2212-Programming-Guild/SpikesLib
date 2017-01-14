package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveTankWithPID extends Command {

	private TankDrivetrain tankDrivetrain;
	private double KP;
	private double KI;
	private double KD;
	private double tolerance = 1;
	private PIDController leftMovmentControl;
	private PIDController rightMovmentControl;

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
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

	public double getTolarance() {
		return tolerance;
	}

	public void setTolarance(double tolarance) {
		this.tolerance = tolarance;
	}

	public DriveTankWithPID(double leftSetPoint, double rightSetPoint, double KP, double KI, double KD,
			TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource) {
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.KD = KD;
		this.KI = KI;
		this.KP = KP;
		leftMovmentControl = new PIDController(KP, KI, KD, leftSource, tankDrivetrain::setLeft);
		leftMovmentControl.setAbsoluteTolerance(tolerance);
		leftMovmentControl.setSetpoint(leftSetPoint);
		leftMovmentControl.setOutputRange(-1, 1);
		rightMovmentControl = new PIDController(KP, KI, KD, rightSource, tankDrivetrain::setRight);
		rightMovmentControl.setAbsoluteTolerance(tolerance);
		rightMovmentControl.setSetpoint(rightSetPoint);
		rightMovmentControl.setOutputRange(-1, 1);
	}

	public DriveTankWithPID(double leftSetPoint, double rightSetPoint, double KP, double KI, double KD,
			TankDrivetrain drivetrain) {
		this(leftSetPoint, rightSetPoint, KP, KI, KD, drivetrain, drivetrain.getLeftPIDSource(),
				drivetrain.getRightPIDSource());
	}

	public DriveTankWithPID(double setPoint, double KP, double KI, double KD, TankDrivetrain drivetrain) {
		this(setPoint, setPoint, KP, KI, KD, drivetrain);
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
		return leftMovmentControl.onTarget() && rightMovmentControl.onTarget();
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
