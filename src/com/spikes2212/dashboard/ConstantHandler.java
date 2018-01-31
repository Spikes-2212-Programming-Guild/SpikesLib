package com.spikes2212.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * This class enables writing constants to the <a href=
 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashboard</a>,
 * allowing them to be changed.
 * 
 * <br><br>
 * This class is meant to be used instead of writing final and static constants
 * into the code; which are problematic because they require you to re-deploy
 * every time you want to change a constant.
 * 
 * @author Omri "Riki" Cohen
 * @see <a href=
 *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html">SmartDashBoard</a>
 */
public class ConstantHandler {

	/**
	 * A {@link HashMap} containing all constant {@link double} values.
	 */
	private static Map<String, Double> doubleMap = new HashMap<String, Double>();
	
	/**
	 * A {@link HashMap} containing all constant {@link int} values.
	 */
	private static Map<String, Integer> intMap = new HashMap<String, Integer>();

	/**
	 * A {@link HashMap} containing all constant {@link String} values.
	 */
	private static Map<String, String> stringMap = new HashMap<String, String>();;

	/**
	 * This function writes a {@link double} constant to the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>,
	 * allowing the user to change it without redeploying the code.
	 *
	 * @param name
	 *            The name to give the constant on the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 *            Names must be unique as they cannot be shared by two or more
	 *            fields on the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 * @param value
	 *            The default value the constant starts with. The constant will
	 *            always have this value, unless it is manually changed on the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 * @return A {@link Supplier} which supplies the current value of the constant.
	 *         If it cannot communicate with the field {@code name} from the
	 *         <a href=
	 *         "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>,
	 *         it will return {@code value}.
	 */
	public static Supplier<Double> addConstantDouble(String name, double value) {
		if (!Preferences.getInstance().containsKey(name)) {
			// FIXME putDouble might throw an exception when there is already a value under
			// the name 'name'.
			Preferences.getInstance().putDouble(name, value);
		}
		doubleMap.put(name, value);
		return () -> Preferences.getInstance().getDouble(name, value);
	}

	/**
	 * This function writes a {@link int} constant to the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>,
	 * allowing the user to change it without redeploying the code.
	 *
	 * @param name
	 *            The name to give the constant on the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 *            Names must be unique as they cannot be shared by two or more
	 *            fields on the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 * @param value
	 *            The default value the constant starts with. The constant will
	 *            always have this value, unless it is manually changed on the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 * @return A {@link Supplier} which supplies the current value of the constant.
	 *         If it cannot communicate with the field {@code name} from the
	 *         <a href=
	 *         "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>,
	 *         it will return {@code value}.
	 */
	public static Supplier<Integer> addConstantInt(String name, int value) {
		if (!Preferences.getInstance().containsKey(name)) {
			// FIXME putInt might throw an exception when there is already a value under the
			// name 'name'.
			Preferences.getInstance().putInt(name, value);
		}
		intMap.put(name, value);
		return () -> (int) Preferences.getInstance().getInt(name, value);
	}

	/**
	 * This function writes a {@link string} constant to the <a href=
	 * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>,
	 * allowing the user to change it without redeploying the code.
	 *
	 * @param name
	 *            The name to give the constant on the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 *            Names must be unique as they cannot be shared by two or more
	 *            fields on the <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 * @param value
	 *            The default value the constant starts with. The constant will
	 *            always have this value, unless it is manually changed on the
	 *            <a href=
	 *            "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>.
	 * @return A {@link Supplier} which supplies the current value of the constant.
	 *         If it cannot communicate with the field {@code name} from the
	 *         <a href=
	 *         "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Preferences.html">Prefrences</a>,
	 *         it will return {@code value}.
	 */
	public static Supplier<String> addConstantString(String name, String value) {
		if (!Preferences.getInstance().containsKey(name)) {
			// FIXME putString might throw an exception when there is already a value under
			// the name 'name'.
			Preferences.getInstance().putString(name, value);
		}
		stringMap.put(name, value);
		return () -> Preferences.getInstance().getString(name, value);
	}

	/**
	 * This function sets the Preferences' values with the original ones stored in the Maps.
	 */
	public static void reset() {
		// Resetting doubles
		for (String key : doubleMap.keySet()) {
			Preferences.getInstance().putDouble(key, doubleMap.get(key));
		}
		
		// Resetting ints
		for (String key : intMap.keySet()) {
			Preferences.getInstance().putInt(key, intMap.get(key));
		}
		
		// Resetting strings
		for (String key : stringMap.keySet()) {
			Preferences.getInstance().putString(key, stringMap.get(key));
		}
	}

}
