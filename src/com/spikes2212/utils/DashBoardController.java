package com.spikes2212.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	public void removeString(String key) {
		stringFields.remove(key);
		updateString();
	}

	public void addString(String name, Supplier<String> value) {
		stringFields.put(name, value);
		stringFields.remove(stringFields, name);
	}

	public void removeSendable(String key) {
		sendableFields.remove(key);
		updateSendables();
	}

	public void addSendable(String name, Supplier<Sendable> value) {
		sendableFields.put(name, value);
	}

	public void removeDouble(String key) {
		doubleFields.remove(key);
		updateDoubles();
	}

	public void addDouble(String name, Supplier<Double> value) {
		doubleFields.put(name, value);
	}

	public void removeBoolean(String key) {
		booleanFields.remove(key);
		updateBooleans();
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