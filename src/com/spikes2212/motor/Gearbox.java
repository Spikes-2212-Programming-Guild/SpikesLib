package com.spikes2212.motor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

import java.util.List;

public class Gearbox extends WPI_TalonSRX{

    private List<SpeedController> victorSPs;
    private int port;

    public Gearbox(int port) {
        super(port);
        this.port = port;
    }

    @Override
    public void set(double speed) {
        this.set(speed);
    }

    @Override
    public double get() {
        return this.get();
    }

    @Override
    public boolean getInverted() {
        return this.getInverted();
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.setInverted(isInverted);
    }

    @Override
    public void disable() {
        this.disable();
    }

    @Override
    public void stopMotor() {
        this.stopMotor();
    }

    @Override
    public void pidWrite(double output) {
        this.pidWrite(output);
    }

    public void enslaveTalonSRX(WPI_TalonSRX... slaves) {
        for(WPI_TalonSRX slave : slaves) {
            slave.follow(this);
        }
    }

    public void enslaveVictorSPX(WPI_VictorSPX... slaves) {
        for(WPI_VictorSPX slave : slaves) {
            slave.follow(this);
        }
    }

    public void enslaveVictorSP(VictorSP... slaves) {
        for(VictorSP slave : slaves) {
            victorSPs.add(slave);
        }
    }

}