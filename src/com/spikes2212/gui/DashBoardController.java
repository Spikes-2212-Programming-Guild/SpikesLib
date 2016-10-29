package com.spikes2212.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashBoardController {
	Map<String, Supplier<String>> stringFields;
	Map<String, Supplier<Double>> doubleFields;
	Map<String, Supplier<Boolean>> booleanFields;
	Map<String, Supplier<Sendable>> sendableFields;

	public DashBoardController() {
		stringFields = new HashMap<String, Supplier<String>>();
		doubleFields = new HashMap<String, Supplier<Double>>();
		booleanFields = new HashMap<String, Supplier<Boolean>>();
		sendableFields = new HashMap<String, Supplier<Sendable>>();
	}

	public void addString(String name, Supplier<String> value) {
		stringFields.put(name, value);
	}

	public void addSendable(String name, Supplier<Sendable> value) {
		sendableFields.put(name, value);
	}

	public void addDouble(String name, Supplier<Double> value) {
		doubleFields.put(name, value);
	}

	public void addBoolean(String name, Supplier<Boolean> value) {
		booleanFields.put(name, value);
	}

	private void updateBooleans() {
		for (Map.Entry<String, Supplier<Boolean>> entry : booleanFields.entrySet()) {
			SmartDashboard.putBoolean(entry.getKey(), entry.getValue().get());
		}
	}

	private void updateSendables() {
		for (Map.Entry<String, Supplier<Sendable>> entry : sendableFields.entrySet()) {
			SmartDashboard.putData(entry.getKey(), entry.getValue().get());
		}
	}

	private void updateDoubles() {
		for (Map.Entry<String, Supplier<Double>> entry : doubleFields.entrySet()) {
			SmartDashboard.putNumber(entry.getKey(), entry.getValue().get());
		}
	}

	private void updateString() {
		for (Map.Entry<String, Supplier<String>> entry : stringFields.entrySet()) {
			SmartDashboard.putString(entry.getKey(), entry.getValue().get());
		}
	}

	public void update() {
		updateBooleans();
		updateDoubles();
		updateString();
		updateSendables();
	}
}