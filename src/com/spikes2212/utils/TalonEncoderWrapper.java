package com.spikes2212.utils;

public class TalonEncoderWrapper {
    private int kIdx;
    private int kTimeout;
    private boolean kSensorPhase;

    private double kP;
    private double kI;
    private double kD;

    private double setpoint = 300;

    public TalonEncoderWrapper(double kP, double kI, double kD, double setpoint, int kIdx, int kTimeout, boolean kSensorPhase) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.setpoint = setpoint;
        this.kIdx = kIdx;
        this.kTimeout = kTimeout;
        this.kSensorPhase = kSensorPhase;
    }

    public void doPID(TalonSRX talon) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kIdx, kTimeout);
        talon.setSensorPhase(kSensorPhase);

        talon.configNominalOutputForward(0, kTimeout);
        talon.configNominalOutputReverse(0, kTimeout);

        talon.configPeakOutputForward(0, kTimeout);
        talon.configPeakOutputReverse(0, kTimeout);

        talon.config_kP(kIdx, kP);
        talon.config_kI(kIdx, kI);
        talon.config_kD(kIdx, kD);

        talon.setSelectedSensorPosition(300, kIdx, kTimeout);
    }
}