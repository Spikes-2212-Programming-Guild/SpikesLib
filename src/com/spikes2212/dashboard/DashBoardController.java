package com.spikes2212.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class displays {@link Supplier}s to the <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>,
 * allowing better tracking and control of changing values.
 * 
 * @author Noam "Mantin" Mantin
 * @see SmartDashboard
 * @see Supplier
 */
public class DashBoardController {
	
	/**
	 * A {@link Map} to contain all {@link String} values to be displayed on the
	 * {@code SmartDashBoard}. This map gives a String to each Supplier as a key to identify it
	 */
    private Map<String, Supplier<String>> stringFields;
    
    /**
	 * A {@link Map} to contain all {@link String} values to be displayed on the
	 * {@code SmartDashBoard}. This map gives a String to each Supplier as a key to identify it
	 */
    private Map<String, Supplier<Double>> doubleFields;
    
    /**
	 * A {@link Map} to contain all {@link String} values to be displayed on the
	 * {@code SmartDashBoard}. This map gives a String to each Supplier as a key to identify it
	 */
    private Map<String, Supplier<Boolean>> booleanFields;

    /**
	 * Constructs a new {@link DashBoardController}.
	 * <br><br>
	 * 
	 * More than one {@link DashBoardController} can exist at a time. However, if a
	 * key name is used more than once they'll override each other.
	 */
    public DashBoardController() {
        stringFields = new HashMap<>();
        doubleFields = new HashMap<>();
        booleanFields = new HashMap<>();
    }

    /**
	 * Adds a String {@link Supplier} to this {@link DashBoardController}.
	 *
	 * @param name
	 *            The name of the field where the {@code stringSupplier} will be
	 *            displayed. Overrides values if the field name is already used.
	 * @param stringSupplier
	 *            The {@link Supplier} giving the values that are written to the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>.
	 *            Cannot be null.
	 */
    public void addString(String name, Supplier<String> stringSupplier) {
        remove(name);
        stringFields.put(name, stringSupplier);
    }

    /**
	 * Adds a Double {@link Supplier} to this {@link DashBoardController}.
	 *
	 * @param name
	 *            The name of the field where the {@code stringSupplier} will be
	 *            displayed. Overrides values if the field name is already used.
	 * @param doubleSupplier
	 *            The {@link Supplier} giving the values that are written to the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>.
	 *            Cannot be null.
	 */
    public void addDouble(String name, Supplier<Double> doubleSupplier) {
        remove(name);
        doubleFields.put(name, doubleSupplier);
    }

    /**
	 * Adds a Boolean {@link Supplier} to this {@link DashBoardController}.
	 *
	 * @param name
	 *            The name of the field where the {@code stringSupplier} will be
	 *            displayed. Overrides values if the field name is already used.
	 * @param booleanSupplier
	 *            The {@link Supplier} giving the values that are written to the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>.
	 *            Cannot be null.
	 */
    public void addBoolean(String name, Supplier<Boolean> booleanSupplier) {
        remove(name);
        booleanFields.put(name, booleanSupplier);
    }

    /**
	 * Removes the {@link Supplier} using the given name from this
	 * {@link DashBoardController}.
	 *
	 * @param name
	 *            The name of the field the supplier is using, i.e. the name under
	 *            which the values read from that supplier are written on the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>.
	 */
    public void remove(String name) {
        stringFields.remove(name);
        doubleFields.remove(name);
        booleanFields.remove(name);
    }

    /**
	 * Updates the Boolean {@link Supplier}s within the {@link SmartDashboard}. 
	 */
    private void updateBooleans() {
        for (Map.Entry<String, Supplier<Boolean>> entry : booleanFields.entrySet()) {
            SmartDashboard.putBoolean(entry.getKey(), entry.getValue().get());
        }
    }

    /**
	 * Updates the Double {@link Supplier}s within the {@link SmartDashboard}. 
	 */
    private void updateDoubles() {
        for (Map.Entry<String, Supplier<Double>> entry : doubleFields.entrySet()) {
            SmartDashboard.putNumber(entry.getKey(), entry.getValue().get());
        }
    }

    /**
	 * Updates the String {@link Supplier}s within the {@link SmartDashboard}. 
	 */
    private void updateString() {
        for (Map.Entry<String, Supplier<String>> entry : stringFields.entrySet()) {
            SmartDashboard.putString(entry.getKey(), entry.getValue().get());
        }
    }

    /**
	 * Read from each supplier, and update the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>
	 * according to the read values.
	 * <br><br>
	 * 
	 * This method evokes the {@link Supplier#get()} method for each supplier added
	 * to this DashboardController instance, and then writes that value to the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>
	 * under the name given when that supplier was added.
	 */
    public void update() {
        updateBooleans();
        updateDoubles();
        updateString();
    }
}