package com.spikes2212.utils;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * connection of two speed controllers moving in one direction.
 * can be used for speed controllers working together by physical connection, or controlled to work together only by code.
 * examples for usage- gearboxes, double motored shooting system etc
 */
public class DoubleSpeedcontroller implements SpeedController {

    private SpeedController speedcontrollerRight, speedcontrollerLeft;

    /**
     *
     * @param speedcontrollerRight the first speed controller
     * @param speedcontrollerLeft the second speed controller
     */
    public DoubleSpeedcontroller(SpeedController speedcontrollerRight, SpeedController speedcontrollerLeft) {
        this.speedcontrollerLeft = speedcontrollerLeft;
        this.speedcontrollerRight = speedcontrollerRight;
    }

    /**
     * setting the output value of a pid loop to both speed controllers
     * @param output the output from the pid loop
     */
    @Override
    public void pidWrite(double output) {
        this.speedcontrollerLeft.pidWrite(output);
        this.speedcontrollerRight.pidWrite(output);

    }

    /**
     *
     * @return the value that soposed to be for both speed controllers, practically the right's one
     */
    @Override
    public double get() {
        return this.speedcontrollerRight.get();
    }


    /**
     * sets the SAME value to both speed controllers
     * @param speed the value to be set. must be in the range (-1:1)
     */
    @Override
    public void set(double speed) {
        speedcontrollerLeft.set(speed);
        speedcontrollerRight.set(speed);
    }

    /**
     * changing the mode of the speed controllers to be inverted or not
     * an inverted speed controllers setting the opposite value of what given to them(-1*value)
     * @param isInverted true if the controller is inverted
     */
    @Override
    public void setInverted(boolean isInverted) {
        speedcontrollerLeft.setInverted(isInverted);
        speedcontrollerRight.setInverted(isInverted);

    }

    /**
     * @return true if the controllers are inverted, false otherwise
     */
    @Override
    public boolean getInverted() {

        return speedcontrollerLeft.getInverted();
    }

    /**
     * disabling BOTH the speed controllers
     */
    @Override
    public void disable() {
        speedcontrollerLeft.disable();
        speedcontrollerRight.disable();

    }

    /**
     * stopping the motors of BOTH the speed controllers
     */
    @Override
    public void stopMotor() {
        speedcontrollerLeft.stopMotor();
        speedcontrollerRight.stopMotor();

    }
}
