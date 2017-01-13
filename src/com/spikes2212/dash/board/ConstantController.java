package com.spikes2212.dash.board;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ConstantController {
	public static Supplier<Double> addConstantDouble(String name, double value) {
		SmartDashboard.putNumber(name, value);
		return () -> SmartDashboard.getNumber(name);
	}

}
