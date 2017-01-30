package com.spikes2212.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * This class enables writing constants to the {@link SmartDashboard}, allowing them to be changed.
 * <p>
 * This class is meant to be used instead of writing final and static constants into the code;
 * which are problematic because they require you to re-deploy every time you want to change a constant.
 * </p>
 *
 * @author Omri "Riki" Cohen
 * @see SmartDashboard
 */
public class ConstantHandler {
	
	private static Map<String, Double> doubleMap = new HashMap<String, Double>();
	private static Map<String, Integer> intMap = new HashMap<String, Integer>();
	private static Map<String, String> stringMap = new HashMap<String, String>();;
	
    /**
     * This function writes a constant to the {@link Preferences}, allowing to user to change it without redeploying the code.
     *
     *  @param name  The name to give the constant on the {@link Preferences}.
     *              Names must be unique as they cannot be shared by two or more fields on the {@link Preferences}.
     *              For example, if you gave a constant the name "distance", but also gave the distance as calculated by the robot the name "distance",
     *              those two would override each other.
     * @param value The default value the constant starts with. The constant will always have this value, unless it is manually changed on the {@link Preferences}.
     * @return a {@link Supplier} which supplies the current value of the constant.
     * This will be either the value got in the function call, or the value given by the user on the {@link Preferences}.
     * If it cannot communicate a value from the {@link Preferences}, it will return a 0.
     */
	
    public static Supplier<Double> addConstantDouble(String name, double value) {
        if (!Preferences.getInstance().containsKey(name)) {
        	//FIXME putDouble might throw an exception when there is already a value under the name 'name'.
        	Preferences.getInstance().putDouble(name, value);
        }
        doubleMap.put(name, value);
        return () -> Preferences.getInstance().getDouble(name, value);
    }
    
    /**
     * This function writes a constant to the {@link Preferences}, allowing to user to change it without redeploying the code.
     *
     *  @param name  The name to give the constant on the {@link Preferences}.
     *              Names must be unique as they cannot be shared by two or more fields on the {@link Preferences}.
     *              For example, if you gave a constant the name "distance", but also gave the distance as calculated by the robot the name "distance",
     *              those two would override each other.
     * @param value The default value the constant starts with. The constant will always have this value, unless it is manually changed on the {@link Preferences}.
     * @return a {@link Supplier} which supplies the current value of the constant.
     * This will be either the value got in the function call, or the value given by the user on the {@link Preferences}.
     * If it cannot communicate a value from the {@link Preferences}, it will return a 0.
     */
    public static Supplier<Integer> addConstantInt(String name, int value) {
    	if (!Preferences.getInstance().containsKey(name)) {
    		//FIXME putInt might throw an exception when there is already a value under the name 'name'.
    		Preferences.getInstance().putInt(name, value);
    	}
    	intMap.put(name, value);
    	return () -> (int) Preferences.getInstance().getInt(name, value);
    }

    /**
     * This function writes a constant to the {@link Preferences}, allowing to user to change it without redeploying the code.
     *
     *  @param name  The name to give the constant on the {@link Preferences}.
     *              Names must be unique as they cannot be shared by two or more fields on the {@link Preferences}.
     *              For example, if you gave a constant the name "distance", but also gave the distance as calculated by the robot the name "distance",
     *              those two would override each other.
     * @param value The default value the constant starts with. The constant will always have this value, unless it is manually changed on the {@link Preferences}.
     * @return a {@link Supplier} which supplies the current value of the constant.
     * This will be either the value got in the function call, or the value given by the user on the {@link Preferences}.
     * If it cannot communicate a value from the {@link Preferences}, it will return an empty string.
     */
    public static Supplier<String> addConstantString(String name, String value) {
    	if (!Preferences.getInstance().containsKey(name)) {
    		//FIXME putString might throw an exception when there is already a value under the name 'name'.
    		Preferences.getInstance().putString(name, value);
    	}
    	stringMap.put(name, value);
    	return () -> Preferences.getInstance().getString(name, value);
    }
    
    /**
     * This function updates the Preferences' values with the ones on local Maps.
     */
    public static void reset() {
    	for (String key : doubleMap.keySet()) {
    		Preferences.getInstance().putDouble(key, doubleMap.get(key));
    	}
    	for (String key : intMap.keySet()) {
    		Preferences.getInstance().putInt(key, intMap.get(key));
    	}
    	for (String key : stringMap.keySet()) {
    		Preferences.getInstance().putString(key, stringMap.get(key));
    	}
    }

}
