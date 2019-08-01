package com.spikes2212.command.genericsubsystem.commands;

import java.util.function.Supplier;

import com.spikes2212.command.genericsubsystem.GenericSubsystem;
import com.spikes2212.utils.PIDSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a {@link GenericSubsystem} using wpilib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>.
 * It also waits a specified amount of time after the error is within the given
 * tolerance before stopping the PID loop to make sure the
 * {@link GenericSubsystem} doesn't go past the setpoint.
 *
 * <br>
 * <br>
 * This command will try to move the subsystem until it reaches the latest
 * value supplied by setpoint. The setpoint should use values using the same
 * units as the <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>.
 *
 * @author Omri "Riki" and Itamar Rivkind
 * @see GenericSubsystem
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource</a>
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
 */
public class MoveGenericSubsystemWithPID extends Command {

	protected final GenericSubsystem subsystem;
	protected final PIDSettings PIDSettings;
	protected final Supplier<Double> setpoint;
	protected final PIDSource source;
	protected PIDController movementControl;
	protected double lastTimeNotOnTarget;
	protected double inputRange;
	protected boolean continuous;

	/**
	 * This constructs a new {@link MoveGenericSubsystemWithPID} using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>,
	 * a setpoint, the PID coefficients this command's PID loop should have, and the
	 * tolerance for error.
	 *
	 * @param subsystem
	 *            the {@link GenericSubsystem} this command operates on.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param setpoint
	 *            a supplier supplying the target point of this command.
	 *
	 *            <p>
	 *            This command will try to move subsystem until it reaches the
	 *            latest value supplied by setpoint. setpoint should supply values
	 *            using the same units as source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 *
	 * @param inputRange
	 *            the range of the source's input. For example, gyro's is 360.
	 *            Camera that has 640 px on the wanted axis has output range of 640,
	 *            and one that its values range was scaled between -1 and 1 has
	 *            output range of 2 and so on.
	 * @param continuous
	 *            true to make the PID controller consider the input to be
	 *            continuous, Rather then using the max and min input range as
	 *            constraints, it considers them to be the same point and
	 *            automatically calculates the shortest route to the setpoint.
	 *
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveGenericSubsystemWithPID(GenericSubsystem subsystem, PIDSource source, Supplier<Double> setpoint,
	                                   PIDSettings PIDSettings, double inputRange, boolean continuous) {
		requires(subsystem);
		this.subsystem = subsystem;
		this.source = source;
		this.setpoint = setpoint;
		this.PIDSettings = PIDSettings;
		this.continuous = continuous;
		this.inputRange = inputRange;
	}

	/**
	 * This constructs a new {@link MoveGenericSubsystemWithPID} using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 * given by {@link GenericSubsystem#getPIDSource()}, a setpoint, the PID
	 * coefficients this command's PID loop should have, and the tolerance for
	 * error.
	 *
	 * @param subsystem
	 *            the {@link GenericSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param setpoint
	 *            the target point of this command.
	 *
	 *            <p>
	 *            This command will try to move subsystem until it reaches the
	 *            setpoint. setpoint should be using the same units as source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 *
	 * @param continuous
	 *            a value that is passed to the internal {@link PIDController} which
	 *            makes it handle sensors that give a circular output. A
	 *            {@link edu.wpi.first.wpilibj.interfaces.Gyro} sensor is a good
	 *            example of such a sensor, where x and x+360k degrees are
	 *            essentially the same position
	 *
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveGenericSubsystemWithPID(GenericSubsystem subsystem, PIDSource source, double setpoint,
	                                   PIDSettings PIDSettings, double inputRange, boolean continuous) {
		this(subsystem, source, () -> setpoint, PIDSettings, inputRange, continuous);
	}

	/**
	 * This constructs a new {@link MoveGenericSubsystemWithPID} using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>,
	 * a setpoint, the PID coefficients this command's PID loop should have, and the
	 * tolerance for error. this constructor sets
	 * {@link MoveGenericSubsystemWithPID#continuous} to false automatically
	 *
	 * @param subsystem
	 *            the {@link GenericSubsystem} this command operates on.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param setpoint
	 *            a supplier supplying the target point of this command.
	 *
	 *            <p>
	 *            This command will try to move subsystem until it reaches the
	 *            latest value supplied by setpoint. setpoint should supply values
	 *            using the same units as source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 *
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveGenericSubsystemWithPID(GenericSubsystem subsystem, PIDSource source, Supplier<Double> setpoint,
	                                   PIDSettings PIDSettings) {
		this(subsystem, source, setpoint, PIDSettings, 0, false);
	}

	/**
	 * This constructs a new {@link MoveGenericSubsystemWithPID} using a <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 * given by {@link GenericSubsystem#getPIDSource()}, a setpoint, the PID
	 * coefficients this command's PID loop should have, and the tolerance for
	 * error. this constructor sets {@link MoveGenericSubsystemWithPID#continuous} to
	 * false automatically
	 *
	 *
	 * @param subsystem
	 *            the {@link GenericSubsystem} this command requires and moves.
	 * @param source
	 *            the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDSource.html">PIDSource<a>
	 *            this command uses to get feedback for the PID Loop.
	 * @param setpoint
	 *            the target point of this command.
	 *
	 *            <p>
	 *            This command will try to move subsystem until it reaches the
	 *            setpoint. setpoint should be using the same units as source.
	 *            </p>
	 * @param PIDSettings
	 *            the {@link PIDSettings} this command's PIDController needs.
	 *
	 * @see <a href=
	 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/PIDController.html">PIDController</a>
	 */
	public MoveGenericSubsystemWithPID(GenericSubsystem subsystem, PIDSource source, double setpoint,
	                                   PIDSettings PIDSettings) {
		this(subsystem, source, setpoint, PIDSettings, 0, false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		movementControl = new PIDController(PIDSettings.getKP(), PIDSettings.getKI(), PIDSettings.getKD(), source,
				subsystem::move);
		movementControl.setAbsoluteTolerance(PIDSettings.getTolerance());
		movementControl.setSetpoint(this.setpoint.get());
		movementControl.setOutputRange(-1, 1);
		movementControl.setInputRange(-inputRange / 2, inputRange / 2);
		movementControl.setContinuous(this.continuous);
		movementControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double newSetpoint = setpoint.get();
		if (movementControl.getSetpoint() != newSetpoint)
			movementControl.setSetpoint(newSetpoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!movementControl.onTarget()) {
			lastTimeNotOnTarget = Timer.getFPGATimestamp();
		}
		return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= PIDSettings.getWaitTime()
				|| !subsystem.canMove(movementControl.get());
	}

	// Called once after isFinished returns true
	protected void end() {
		movementControl.disable();
		subsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}