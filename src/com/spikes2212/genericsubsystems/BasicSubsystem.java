package com.spikes2212.genericsubsystems;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class represents a subsystem that moves without any limitations.
 *
 * @author Omri "Riki" Cohen and Itamar
 */
public class BasicSubsystem extends Subsystem {

	private Consumer<Double> movingSpeed;

	public BasicSubsystem(Consumer<Double> movingSpeed) {
		this.movingSpeed = movingSpeed;
	}

	/**
	 * Moves this subsystem with the given speed.
	 *
	 * @param speed
	 *            the speed to move with.
	 */

	public void move(double speed) {
		movingSpeed.accept(speed);
	}

	/**
	 * Stops this subsystem's movement.
	 */
	public void stop() {
		move(0);
	}

	/**
	 * Sets the default command. If this is not called or is called with null,
	 * then there will be no default command for the subsystem.
	 * 
	 * @see edu.wpi.first.wpilibj.command.Subsystem#setDefaultCommand(edu.wpi.first.wpilibj.command.Command)
	 */

	public void setDefaultCommand(Command defaultCommand) {
		super.setDefaultCommand(defaultCommand);
	}

	public void initDefaultCommand() {

	}
}
