package com.spikes2212.motor;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

import java.util.List;

public class Gearbox extends WPI_TalonSRX {

    private List<SpeedController> victorSPs;

    public Gearbox(int port, BaseMotorController... slaves) {
        super(port);
        enslaveBaseMotorController(slaves);
    }

    public Gearbox(int port){
        super(port);
    }

    public void enslaveBaseMotorController(BaseMotorController... slaves) {
        for(BaseMotorController slave : slaves) {
            slave.follow(this);
        }
    }

    public void enslaveVictorSP(VictorSP... slaves) {
        for(VictorSP slave : slaves) {
            victorSPs.add(slave);
        }
    }

}