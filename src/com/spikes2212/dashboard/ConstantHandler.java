package com.spikes2212.dashboard;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class enables writing constants to the {@link SmartDashboard}, allowing the to be changed.
 * <p>
 * This class is meant to be used instead of writing final and static constant into the code;
 * those are problematic because they require you to re-deploy every time you want to change a constant.
 * </p>
 *
 * @author Omri "Riki" Cohen
 * @see SmartDashboard
 */
public class ConstantHandler {

    /**
     * This function writes a constant to the {@link SmartDashboard}, allowing to user to change it without redeploying the code.
     *
     *  @param name  The name to give the constant on the {@link SmartDashboard}.
     *              Names must be unique as they cannot be shared by two or more fields on the {@link SmartDashboard}.
     *              For example, if you gave a constant the name "distance", but also gave the distance as calculated by the robot the name "distance",
     *              those two would override each other.
     * @param value The default value the constant starts with. The constant will always have this value, unless it is manually changed on the {@link SmartDashboard}.
     * @return a {@link Supplier} which supplies the current value of the constant.
     * This will be either the value got in the function call, or the value given by the user on the {@link SmartDashboard}.
     * If it cannot communicate a value from the {@link SmartDashboard}, it will return a 0.
     */
    public static Supplier<Double> addConstantDouble(String name, double value) {
        SmartDashboard.putNumber(name, value);
        return () -> SmartDashboard.getNumber(name, value);
    }

    /**
     * This function writes a constant to the {@link SmartDashboard}, allowing to user to change it without redeploying the code.
     *
     *  @param name  The name to give the constant on the {@link SmartDashboard}.
     *              Names must be unique as they cannot be shared by two or more fields on the {@link SmartDashboard}.
     *              For example, if you gave a constant the name "distance", but also gave the distance as calculated by the robot the name "distance",
     *              those two would override each other.
     * @param value The default value the constant starts with. The constant will always have this value, unless it is manually changed on the {@link SmartDashboard}.
     * @return a {@link Supplier} which supplies the current value of the constant.
     * This will be either the value got in the function call, or the value given by the user on the {@link SmartDashboard}.
     * If it cannot communicate a value from the {@link SmartDashboard}, it will return a 0.
     */
    public static Supplier<Integer> addConstantInt(String name, int value) {
        SmartDashboard.putNumber(name, value);
        return () -> (int) SmartDashboard.getNumber(name, value);
    }

    /**
     * This function writes a constant to the {@link SmartDashboard}, allowing to user to change it without redeploying the code.
     *
     *  @param name  The name to give the constant on the {@link SmartDashboard}.
     *              Names must be unique as they cannot be shared by two or more fields on the {@link SmartDashboard}.
     *              For example, if you gave a constant the name "distance", but also gave the distance as calculated by the robot the name "distance",
     *              those two would override each other.
     * @param value The default value the constant starts with. The constant will always have this value, unless it is manually changed on the {@link SmartDashboard}.
     * @return a {@link Supplier} which supplies the current value of the constant.
     * This will be either the value got in the function call, or the value given by the user on the {@link SmartDashboard}.
     * If it cannot communicate a value from the {@link SmartDashboard}, it will return an empty string.
     */
    public static Supplier<String> addConstantString(String name, String value) {
        SmartDashboard.putString(name, value);
        return () -> SmartDashboard.getString(name, value);
    }

}
