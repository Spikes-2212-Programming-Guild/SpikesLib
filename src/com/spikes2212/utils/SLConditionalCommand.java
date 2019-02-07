package com.spikes2212.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class SLConditionalCommand extends ConditionalCommand {

	private Supplier<Boolean> condition;

	public SLConditionalCommand(Command onTrue, Command onFalse, Supplier<Boolean> condition) {
		super(onTrue, onFalse);
		this.condition = condition;
	}

	@Override
	protected boolean condition() {
		return condition.get();
	}

}
