package com.spikes2212.command.drivetrains;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class EncoderDrivetrain extends TankDrivetrain {

    private Encoder leftEncoder, rightEncoder;

    public EncoderDrivetrain(SpeedController left, SpeedController right, Encoder leftEncoder, Encoder rightEncoder) {
        super(left, right);
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
    }

    public Encoder getLeftEncoder() {
        return leftEncoder;
    }

    public Encoder getRightEncoder() {
        return rightEncoder;
    }
}
