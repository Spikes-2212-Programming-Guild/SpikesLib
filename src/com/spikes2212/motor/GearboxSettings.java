package com.spikes2212.motor;

public class GearboxSettings {
    int maxCurrent, peakCurrentDuration, continuousCurrentLimit;
    boolean CurrentLimit = true;

    public GearboxSettings(int maxCurrent, int peakCurrentDuration, int continuousCurrentLimit) {
        this.maxCurrent = maxCurrent;
        this.peakCurrentDuration = peakCurrentDuration;
        this.continuousCurrentLimit = continuousCurrentLimit;
    }

    public int getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(int maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public int getPeakCurrentDuration() {
        return peakCurrentDuration;
    }

    public void setPeakCurrentDuration(int peakCurrentDuration) {
        this.peakCurrentDuration = peakCurrentDuration;
    }

    public int getContinuousCurrentLimit() {
        return continuousCurrentLimit;
    }

    public void setContinuousCurrentLimit(int continuousCurrentLimit) {
        this.continuousCurrentLimit = continuousCurrentLimit;
    }
}