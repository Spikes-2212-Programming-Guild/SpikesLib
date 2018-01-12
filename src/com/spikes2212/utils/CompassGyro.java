package com.spikes2212.utils;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * This class is a wrapper for any {@link GyroBase} instance,
 * that makes the gyro act as a compass - after 360 degrees will come 0.
 * In addition, this wrapper overrides the {@link PIDSource} methods from {@link GyroBase}
 * and makes them act like a compass too.
 *
 * @see GyroBase
 * @author Simon "C" Kharmatsky
 */
public class CompassGyro extends GyroBase {

    private GyroBase wrappedGyro;

    /**
     * This method constructs a new {@link CompassGyro} instance using a given {@link GyroBase} instance
     * @param wrappedGyro {@link GyroBase} instance wrapped with the {@link CompassGyro} instance
     */
    public CompassGyro(GyroBase wrappedGyro) {
        this.wrappedGyro = wrappedGyro;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void calibrate() {
        wrappedGyro.calibrate();
    }

    /**
     * @inhericDoc
     */
    @Override
    public void reset() {
        wrappedGyro.reset();
    }

    /**
     * This method returns the value of {@link CompassGyro#wrappedGyro#getAngle()} modulo 360
     * @return {@link CompassGyro#wrappedGyro#getAngle()} modulo 360
     */
    @Override
    public double getAngle() {
        return wrappedGyro.getAngle() % 360;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getRate() {
        return wrappedGyro.getRate();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        wrappedGyro.setPIDSourceType(pidSource);
    }

    /**
     * @inheritDoc
     */
    @Override
    public PIDSourceType getPIDSourceType() {
        return wrappedGyro.getPIDSourceType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public double pidGet() {
        if (getPIDSourceType() == PIDSourceType.kDisplacement) {
            return wrappedGyro.pidGet() % 360;
        }
        return wrappedGyro.pidGet();
    }
}