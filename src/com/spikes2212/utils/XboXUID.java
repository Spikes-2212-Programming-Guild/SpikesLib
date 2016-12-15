package com.spikes2212.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboXUID extends Joystick {

    static final int X_RIGHT = 2;
    static final int X_LEFT = 0;
    static final int Y_RIGHT = 3;
    static final int Y_LEFT = 1;

    public XboXUID(int port) {
        super(port);
    }

    public Button getGreenButton() {
        return new JoystickButton(this, 2);
    }

    public Button getBlueButton() {
        return new JoystickButton(this, 1);
    }

    public Button getRedButton() {
        return new JoystickButton(this, 3);
    }

    public Button getYellowButton() {
        return new JoystickButton(this, 4);
    }

    public Button getRtButton() {
        return new JoystickButton(this, 8);
    }

    public Button getRbButton() {
        return new JoystickButton(this, 6);
    }

    public Button getLtButton() {
        return new JoystickButton(this, 7);
    }

    public Button getLbButton() {

        return new JoystickButton(this, 5);
    }

    public double getRightX() {
        return getRawAxis(X_RIGHT)
    }

    public double getRightY() {
        return getRawAxis(Y_RIGHT);
    }

    public double getLeftX() {
        return getRawAxis(X_LEFT);
    }

    public double getLeftY() {
        return getY(); // Do not edit this to "getRawAxis(Y_LEFT)", it somehow works this way
    }

    public Button getUpButton() {
        return new JoystickButton(this, 14);
    }

    public Button getDownButton() {
        return new JoystickButton(this, 15);
    }

    public Button getLeftButton() {
        return new JoystickButton(this, 16);
    }

    public Button getRightButton() {
        return new JoystickButton(this, 17);
    }
}