package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 * This class is a wrapper of WPILib's <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/ConditionalCommand.html">ConditinalCommand</a>.
 * It allows the user to write a conditional command in one line.
 * 
 * @author Uriah "Johnny" Rokach
 */
public class SLConditionalCommand extends ConditionalCommand {

	private Supplier<Boolean> condition;

	/**
	 * Constructs a new {@link SLConditionalCommand} using two commands (on
	 * operates when the condition is true, the other when it is false) and a
	 * boolean supplier of the condition.
	 * 
	 * @param onTrue
	 *            The command that will run if the condition is true.
	 * @param onFalse
	 *            The command that will run if the condition is false.
	 * @param condition
	 *            The supplier of the condition boolean.
	 */
	public SLConditionalCommand(Command onTrue, Command onFalse, Supplier<Boolean> condition) {
		super(onTrue, onFalse);
		this.condition = condition;
	}

	@Override
	protected boolean condition() {
		return condition.get();
	}

}
