package com.spikes2212.command.genericsubsystem;

import com.google.inject.Injector;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public abstract class InjectedSubsystem extends GenericSubsystem{
	private Injector injector;
	
	/**
	 * Constructs a new instance of InjectedSubsystem.
	 */
	public InjectedSubsystem() {
		super();
	}
	
	/**
	 * Constructs a new instance of InjectedSubsystem with the given minSpeed and maxSpeed.
	 *
	 * @param minSpeed the minimum speed
	 * @param maxSpeed the maximum speed
	 */
	public InjectedSubsystem(double minSpeed, double maxSpeed) {
		super(minSpeed, maxSpeed);
	}
	
	/**
	 * Constructs a new instance of InjectedSubsystem with the given minSpeed supplier and maxSpeed supplier.
	 *
	 * @param minSpeed the minimum speed
	 * @param maxSpeed the maximum speed
	 */
	public InjectedSubsystem(Supplier<Double> minSpeed, Supplier<Double> maxSpeed) {
		super(minSpeed, maxSpeed);
	}
	
	public void setInjector(Injector injector) {
		this.injector = injector;
	}
	
	public Command injectCommand(Command command) {
		injector.injectMembers(command);
		return command;
	}
}
