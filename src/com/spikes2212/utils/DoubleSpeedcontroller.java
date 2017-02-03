package com.spikes2212.utils;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * This class joins two {@link SpeedController} into one, making a speed controller that activates both.
 *
 * @author Uriah "Jhonny" Rokach
 * @see SpeedController
 */
public class DoubleSpeedcontroller implements SpeedController {

    private SpeedController speedcontrollerRight, speedcontrollerLeft;

    /**
     * Constructs a speed controller from two other speed controllers that
     * acts like both: all of its functions call the same functions on the two speed controllers.
     * Example: https://www.youtube.com/watch?v=pU-FNy6RG80
     *
     * @param speedcontrollerRight The first speed controller of the two. The "side" holds no meaning.
     * @param speedcontrollerLeft  The second speed controller of the two. The "side" holds no meaning.
     */
    public DoubleSpeedcontroller(SpeedController speedcontrollerRight, SpeedController speedcontrollerLeft) {
        this.speedcontrollerLeft = speedcontrollerLeft;
        this.speedcontrollerRight = speedcontrollerRight;
    }

    @Override
    public void pidWrite(double output) {
        this.speedcontrollerLeft.pidWrite(output);
        this.speedcontrollerRight.pidWrite(output);

    }

    @Override
    public double get() {
        return this.speedcontrollerRight.get();
    }


    @Override
    public void set(double speed) {
        speedcontrollerLeft.set(speed);
        speedcontrollerRight.set(speed);
    }

    @Override
    public void setInverted(boolean isInverted) {
        speedcontrollerLeft.setInverted(isInverted);
        speedcontrollerRight.setInverted(isInverted);

    }

    @Override
    public boolean getInverted() {

        return speedcontrollerLeft.getInverted();
    }

    @Override
    public void disable() {
        speedcontrollerLeft.disable();
        speedcontrollerRight.disable();

    }

    @Override
    public void stopMotor() {
        speedcontrollerLeft.stopMotor();
        speedcontrollerRight.stopMotor();

    }
}
