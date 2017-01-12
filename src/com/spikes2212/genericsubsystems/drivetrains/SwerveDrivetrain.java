package com.spikes2212.genericsubsystems.drivetrains;

import java.util.function.Supplier;

import com.spikes2212.genericsubsystems.LimitedSubsystem;
import com.spikes2212.genericsubsystems.commands.MoveLimitedSubsystemWithPID;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class SwerveDrivetrain extends Subsystem {
	private static double tolerance = 1;
	private SpeedController rotate1, rotate2, rotate3, rotate4, wheel1, wheel2, wheel3, wheel4;
	private AnalogInput angle1, angle2, angle3, angle4;

	public SwerveDrivetrain(SpeedController rotate1, SpeedController rotate2, SpeedController rotate3,
			SpeedController rotate4, SpeedController wheel1, SpeedController wheel2, SpeedController wheel3,
			SpeedController wheel4, AnalogInput angle1, AnalogInput angle2, AnalogInput angle3, AnalogInput angle4) {
		this.rotate1 = rotate1;
		this.rotate2 = rotate2;
		this.rotate3 = rotate3;
		this.rotate4 = rotate4;
		this.wheel1 = wheel1;
		this.wheel2 = wheel2;
		this.wheel3 = wheel3;
		this.wheel4 = wheel4;
		this.angle1 = angle1;
		this.angle2 = angle2;
		this.angle3 = angle3;
		this.angle4 = angle4;

	}

	public abstract void rotateWheel(double speed, int wheelNumber);

	public abstract void setSpeed(double speed);

	public static double getTolerance() {
		return tolerance;
	}

	public static void setTolerance(double tolerance) {
		SwerveDrivetrain.tolerance = tolerance;
	}

	public double getAngle1() {
		return angle1.getValue();
	}

	public double getAngle2() {
		return angle2.getValue();
	}

	public double getAngle3() {
		return angle3.getValue();
	}

	public double getAngle4() {
		return angle4.getValue();
	}

}
