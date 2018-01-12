package com.spikes2212.utils;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CompassGyro extends GyroBase {

    private GyroBase wrappedGyro;
    private int circleSize;

    public CompassGyro(GyroBase wrappedGyro, int circleSize) {
        this.wrappedGyro = wrappedGyro;
        this.circleSize = circleSize;
    }

    @Override
    public void calibrate() {
        wrappedGyro.calibrate();
    }

    @Override
    public void reset() {
        wrappedGyro.reset();
    }

    @Override
    public double getAngle() {
        return wrappedGyro.getAngle();
    }

    @Override
    public double getRate() {
        return wrappedGyro.getRate();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        wrappedGyro.setPIDSourceType(pidSource);
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return wrappedGyro.getPIDSourceType();
    }

    @Override
    public double pidGet() {
        return wrappedGyro.pidGet() % circleSize;
    }
}
