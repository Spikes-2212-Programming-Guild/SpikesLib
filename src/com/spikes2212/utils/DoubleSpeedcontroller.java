package com.spikes2212.utils;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * This class joins two {@link SpeedController} into one, making a speed controller that activates both.
 *
 * @author Uriah "Jhonny" Rokach
 * @see SpeedController
 */
public class DoubleSpeedcontroller implements SpeedController {

    private SpeedController speedControllerRight, speedControllerLeft;

    /**
     * Constructs a speed controller from two other speed controllers that
     * acts like both: all of its functions call the same functions on the two speed controllers.
     * Example: https://www.youtube.com/watch?v=pU-FNy6RG80
     *
     * @param speedcontrollerRight The first speed controller of the two. The "side" holds no meaning.
     * @param speedcontrollerLeft  The second speed controller of the two. The "side" holds no meaning.
     */
    public DoubleSpeedcontroller(SpeedController speedcontrollerRight, SpeedController speedcontrollerLeft) {
        this.speedControllerLeft = speedcontrollerLeft;
        this.speedControllerRight = speedcontrollerRight;
    }

    @Override
    public void pidWrite(double output) {
        this.speedControllerLeft.pidWrite(output);
        this.speedControllerRight.pidWrite(output);

    }

    @Override
    public double get() {
        return this.speedControllerRight.get();
    }


    @Override
    public void set(double speed) {
        speedControllerLeft.set(speed);
        speedControllerRight.set(speed);
    }

    @Override
    public void setInverted(boolean isInverted) {
        speedControllerLeft.setInverted(isInverted);
        speedControllerRight.setInverted(isInverted);

    }

    @Override
    public boolean getInverted() {

        return speedControllerLeft.getInverted();
    }

    @Override
    public void disable() {
        speedControllerLeft.disable();
        speedControllerRight.disable();

    }

    @Override
    public void stopMotor() {
        speedControllerLeft.stopMotor();
        speedControllerRight.stopMotor();

    }
}
