package com.spikes2212.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Roeei
 *
 */
public class DashBoardController {
	private Map<String, Supplier<String>> stringFields;
	private Map<String, Supplier<Double>> doubleFields;
	private Map<String, Supplier<Boolean>> booleanFields;
	private Map<String, Supplier<Sendable>> sendableFields;

	public DashBoardController() {
		stringFields = new HashMap<String, Supplier<String>>();
		doubleFields = new HashMap<String, Supplier<Double>>();
		booleanFields = new HashMap<String, Supplier<Boolean>>();
		sendableFields = new HashMap<String, Supplier<Sendable>>();
	}
	
	/**
	 * @param name is the name given to the String value meant to enter the DashBoard.
	 * @param value is the String value meant to enter the DashBoard
	 */
	public void addString(String name, Supplier<String> value) {
		stringFields.put(name, value);
	}
	
	/**
	 * @param name is the name given to the Sendable value meant to enter the DashBoard.
	 * @param value is the Sendable value meant to enter the DashBoard
	 */
	public void addSendable(String name, Supplier<Sendable> value) {
		sendableFields.put(name, value);
	}
	
	/**
	 * @param name is the name given to the Double value meant to enter the DashBoard.
	 * @param value is the Double value meant to enter the DashBoard
	 */
	public void addDouble(String name, Supplier<Double> value) {
		doubleFields.put(name, value);
	}
	
	/**
	 * @param name is the name given to the Boolean value meant to enter the DashBoard.
	 * @param value is the Booleanvalue meant to enter the DashBoard
	 */
	public void addBoolean(String name, Supplier<Boolean> value) {
		booleanFields.put(name, value);
	}
	
	/**
	 *update booleans values
	 */
	private void updateBooleans() {
		for (Map.Entry<String, Supplier<Boolean>> entry : booleanFields.entrySet()) {
			SmartDashboard.putBoolean(entry.getKey(), entry.getValue().get());
		}
	}

	/**
	 * update Sendables 
	 */
	private void updateSendables() {
		for (Map.Entry<String, Supplier<Sendable>> entry : sendableFields.entrySet()) {
			SmartDashboard.putData(entry.getKey(), entry.getValue().get());
		}
	}

	/**
	 * update double values
	 */
	private void updateDoubles() {
		for (Map.Entry<String, Supplier<Double>> entry : doubleFields.entrySet()) {
			SmartDashboard.putNumber(entry.getKey(), entry.getValue().get());
		}
	}

	/**
	 * update strings
	 */
	private void updateString() {
		for (Map.Entry<String, Supplier<String>> entry : stringFields.entrySet()) {
			SmartDashboard.putString(entry.getKey(), entry.getValue().get());
		}
	}
	
	/**
	 * Updates the tables.
	 */
	public void update() {
		updateBooleans();
		updateDoubles();
		updateString();
		updateSendables();
	}
}