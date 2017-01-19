package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveTankWithPID extends Command {

	private TankDrivetrain tankDrivetrain;
	private double KP;
	private double KI;
	private double KD;
	private double tolerance;
	private double leftSetpoint;
	private double rightSetpoint;
	private PIDSource leftSource;
	private PIDSource rightSource;
	private PIDController leftMovmentControl;
	private PIDController rightMovmentControl;
	private double lastTimeNotOnTarget;
	private static final double WAIT_TIME=5;
		
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

	public DriveTankWithPID(TankDrivetrain drivetrain, PIDSource leftSource, PIDSource rightSource, double leftSetpoint,
			double rightSetpoint, double KP, double KI, double KD, double tolerance) {
		requires(drivetrain);
		this.tankDrivetrain = drivetrain;
		this.leftSource = leftSource;
		this.rightSource = rightSource;
		this.leftSetpoint = leftSetpoint;
		this.rightSetpoint = rightSetpoint;
		this.KD = KD;
		this.KI = KI;
		this.KP = KP;
		this.tolerance = tolerance;
	}

	public DriveTankWithPID(TankDrivetrain drivetrain, double leftSetPoint, double rightSetPoint, double KP, double KI,
			double KD, double tolerance) {
		this(drivetrain, drivetrain.getLeftPIDSource(), drivetrain.getRightPIDSource(), leftSetPoint, rightSetPoint, KP,
				KI, KD, tolerance);
	}

	public DriveTankWithPID(double setPoint, double KP, double KI, double KD, TankDrivetrain drivetrain,
			double tolerance) {
		this(drivetrain, setPoint, setPoint, KP, KI, KD,  tolerance);
	}

	// requires(drivetrain);
	// this.tankDrivetrain = drivetrain;
	// this.setpoint = setpoint;
	// movmentControl = new PIDController(KP, KI, KD, PIDSource, PIDOutput);

	// Called just before this Command runs the first time
	protected void initialize() {
		leftMovmentControl = new PIDController(KP, KI, KD, leftSource, tankDrivetrain::setLeft);
		leftMovmentControl.setAbsoluteTolerance(tolerance);
		leftMovmentControl.setSetpoint(this.leftSetpoint);
		leftMovmentControl.setOutputRange(-1, 1);
		rightMovmentControl = new PIDController(KP, KI, KD, rightSource, tankDrivetrain::setRight);
		rightMovmentControl.setAbsoluteTolerance(tolerance);
		rightMovmentControl.setSetpoint(this.rightSetpoint);
		rightMovmentControl.setOutputRange(-1, 1);
		leftMovmentControl.enable();
		rightMovmentControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(!leftMovmentControl.onTarget()&&!rightMovmentControl.onTarget()){
			lastTimeNotOnTarget=Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp()-lastTimeNotOnTarget==WAIT_TIME;
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
