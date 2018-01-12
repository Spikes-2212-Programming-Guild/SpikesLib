package com.spikes2212.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class allows for {@link Supplier}s to be "written" to the {@link SmartDashboard},
 * allowing for changing values to be easily written to the {@link SmartDashboard}.
 *
 * @author Noam "Mantin" Mantin
 * @see SmartDashboard
 * @see Supplier
 */
public class DashBoardController {
    private Map<String, Supplier<String>> stringFields;
    private Map<String, Supplier<Double>> doubleFields;
    private Map<String, Supplier<Boolean>> booleanFields;

    /**
     * Constructs a new {@link DashBoardController}.
     * <p>
     * <p>
     * More than one {@link DashBoardController} can exist at a time,
     * however if a key name is used twice they'll override each other.
     * </p>
     */
    public DashBoardController() {
        stringFields = new HashMap<>();
        doubleFields = new HashMap<>();
        booleanFields = new HashMap<>();
    }

    /**
     * Add a String {@link Supplier} to this {@link DashBoardController}.
     *
     * @param name           The name values from stringSupplier are written under on the {@link SmartDashboard}.
     *                       If another supplier in this object already uses this name, it is removed, leaving only stringSupplier using that name.
     * @param stringSupplier The {@link Supplier} supplying the values that should be written to the {@link SmartDashboard}.
     *                       Values are read from it every time {@link #update()} is run. Cannot be null.
     */
    public void addString(String name, Supplier<String> stringSupplier) {
        remove(name);
        stringFields.put(name, stringSupplier);
    }

    /**
     * Add a Double {@link Supplier} to this {@link DashBoardController}.
     *
     * @param name           The name values from stringSupplier are written under on the {@link SmartDashboard}.
     *                       If another supplier in this object already uses this name, it is removed, leaving only doubleSupplier using that name.
     * @param doubleSupplier The {@link Supplier} supplying the values that should be written to the {@link SmartDashboard}.
     *                       Values are read from it every time {@link #update()} is run. Cannot be null.
     */
    public void addDouble(String name, Supplier<Double> doubleSupplier) {
        remove(name);
        doubleFields.put(name, doubleSupplier);
    }

    /**
     * Add a Boolean {@link Supplier} to this {@link DashBoardController}.
     *
     * @param name            The name values from stringSupplier are written under on the {@link SmartDashboard}.
     *                        If another supplier in this object already uses this name, it is removed, leaving only booleanSupplier using that name.
     * @param booleanSupplier The {@link Supplier} supplying the values that should be written to the {@link SmartDashboard}.
     *                        Values are read from it every time {@link #update()} is run. Cannot be null.
     */
    public void addBoolean(String name, Supplier<Boolean> booleanSupplier) {
        remove(name);
        booleanFields.put(name, booleanSupplier);
    }

    /**
     * Remove the supplier using the given name from this object.
     *
     * @param name The name that supplier is using, e.g. the name under which the values read
     *             from that supplier are written on the {@link SmartDashboard}.
     */
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

    /**
     * Read from each supplier, and update the {@link SmartDashboard} according to the read values.
     * <p>
     * <p>
     * This method evokes the {@link Supplier#get()} method for each supplier added to this object,
     * and than writes that value to the {@link SmartDashboard} under the name given when that supplier was added.
     * </p>
     *
     * @see #addBoolean(String, Supplier)
     * @see #addDouble(String, Supplier)
     * @see #addString(String, Supplier)
     */
    public void update() {
        updateBooleans();
        updateDoubles();
        updateString();
    }
}