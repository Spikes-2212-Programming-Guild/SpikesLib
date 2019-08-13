package com.spikes2212.utils;

import java.util.function.Consumer;

import com.spikes2212.command.genericsubsystem.GenericSubsystem;

/**
 * This class is a {@link Consumer} of Double. This is used for the constructor
 * of {@link GenericSubsystem} for a motor that needs to be inverted. It gets a
 * {@link Consumer} in its constructor and becomes an inverted version of it.
 * 
 * @author Omri "Riki" Cohen
 */
public class InvertedConsumer implements Consumer<Double> {

	protected final Consumer<Double> baseConsumer;

	/**
	 * Constructs a new {@link InvertedConsumer} using a base {@link Consumer}
	 * that it inverts.
	 * 
	 * @param baseConsumer
	 *            the consumer it is the inverted version of.
	 */
	public InvertedConsumer(Consumer<Double> baseConsumer) {
		this.baseConsumer = baseConsumer;
	}

	/**
	 * Gets a double and give an opposite value to the basConsumer.
	 * 
	 * @param speed
	 *            the opposite speed the baseConsumer is getting.
	 */
	@Override
	public void accept(Double speed) {
		baseConsumer.accept(-speed);
	}
}
