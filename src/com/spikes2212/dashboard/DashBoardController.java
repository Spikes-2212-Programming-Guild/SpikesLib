package com.spikes2212.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class allows for {@link Supplier}s to be "written" to the {@link SmartDashboard},
 * allowing for changing values to be easily written to the {@link SmartDashboard}.
 *
 * @see SmartDashboard
 * @author Noam "Muntin" Muntin
 */
public class DashBoardController {
    private Map<String, Supplier<String>> stringFields;
    private Map<String, Supplier<Double>> doubleFields;
    private Map<String, Supplier<Boolean>> booleanFields;

    /**
     * Constructs a new {@link DashBoardController}.
     *
     * <p>
     *     More than one {@link DashBoardController} can exist at a time,
     *     however if a key name is used twice they'll override each other.
     * </p>
     */
    public DashBoardController() {
        stringFields = new HashMap<>();
        doubleFields = new HashMap<>();
        booleanFields = new HashMap<>();
    }

    public void addString(String name, Supplier<String> value) {
        remove(name);
        stringFields.put(name, value);
    }

    public void addDouble(String name, Supplier<Double> value) {
        remove(name);
        doubleFields.put(name, value);
    }

    public void addBoolean(String name, Supplier<Boolean> value) {
        remove(name);
        booleanFields.put(name, value);
    }

    public void remove(String name) {
        stringFields.remove(name);
        doubleFields.remove(name);
        booleanFields.remove(name);
    }

    private void updateBooleans() {
        for (Map.Entry<String, Supplier<Boolean>> entry : booleanFields.entrySet()) {
            SmartDashboard.putBoolean(entry.getKey(), entry.getValue().get());
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
    }
}