package com.spikes2212.genericsubsystems.commands;

import com.spikes2212.genericsubsystems.LimitedSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link LimitedSubsystem} using wpilib's {@link PIDController}.
 * It also waits a specified amount of time after the error is within the given tolerance
 * before stopping the PID Loop to make sure the subsystem doesn't go past and remain beyond the setpoint.
 *
 * @author Omri "Riki" Cohen
 * @see LimitedSubsystem
 * @see PIDController
 * @see #setWaitTime(double)
 */
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

    /**
     * Gets the time this command will wait while within tolerance of the setpoint before ending.
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
     * Sets the time this command will wait while within tolerance of the setpoint before ending.
     * <p>
     * The PID control of the subsystem continues while waiting.
     * <br/>
     * If wait time is set to 0, the command won't wait.
     * </p>
     *
     * @param waitTime the new wait time, in seconds.
     */
    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * Sets the Proportional coefficient of the PID loop of this command.
     *
     * @param P the new Proportional coefficient.
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
     * @param I the new Integral coefficient.
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
     * @param D the new Differential coefficient.
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
     * This tolerance defines when this command ends:
     * This command will end after the difference between the
     * setpoint and the current position is within the tolerance for
     * the amount of time specified by {@link #setWaitTime(double)} straight.
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
     * This tolerance defines when this command ends:
     * This command will end after the difference between the
     * setpoint and the current position is within the tolerance for
     * the amount of time specified by {@link #setWaitTime(double)} straight.
     * </p>
     *
     * @param tolerance The new tolerance to set. If 0, this command will never end.
     * @see PIDController#setAbsoluteTolerance(double)
     */
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * This constructs a new {@link MoveLimitedSubsystemWithPID} using a {@link PIDSource}
     * different than the one given by {@link LimitedSubsystem#getPIDSource()},
     * a setpoint, the PID coefficients this command's PID loop should have, and the tolerance for error.
     *
     * @param limitedSubsystem the {@link edu.wpi.first.wpilibj.command.Subsystem} this command requires and moves.
     * @param source           the {@link PIDSource} this command uses to get feedback for the PID Loop.
     * @param setpoint         the target point of this command. <p>
     *                         This command will try to move limitedSubsystem until it reaches the setpoint.
     *                         setpoint should be using the same units as source.
     *                         </p>
     * @param KP               the Proportional coefficient of the PID loop of this command.
     * @param KI               the Integral coefficient of the PID loop of this command.
     * @param KD               the Differential coefficient of the PID loop of this command.
     * @param tolerance        the tolerance for error of this command. See {@link #setTolerance(double)}.
     * @see PIDController
     */
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

    /**
     * This constructs a new {@link MoveLimitedSubsystemWithPID} using a {@link PIDSource} given by {@link LimitedSubsystem#getPIDSource()},
     * a setpoint, the PID coefficients this command's PID loop should have, and the tolerance for error.
     *
     * @param limitedSubsystem the {@link edu.wpi.first.wpilibj.command.Subsystem} this command requires and moves.
     * @param setpoint         the target point of this command. <p>
     *                         This command will try to move limitedSubsystem until it reaches the setpoint.
     *                         setpoint should be using the same units as source.
     *                         </p>
     * @param KP               the Proportional coefficient of the PID loop of this command.
     * @param KI               the Integral coefficient of the PID loop of this command.
     * @param KD               the Differential coefficient of the PID loop of this command.
     * @param tolerance        the tolerance for error of this command. See {@link #setTolerance(double)}.
     * @see PIDController
     */
    public MoveLimitedSubsystemWithPID(LimitedSubsystem limitedSubsystem, double setpoint, double KP, double KI,
                                       double KD, double tolerance) {
        this(limitedSubsystem, limitedSubsystem.getPIDSource(), setpoint, KP, KI, KD, tolerance);
    }

    @Deprecated
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
