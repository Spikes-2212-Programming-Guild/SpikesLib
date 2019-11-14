package com.spikes2212.utils;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;

public class CamerasHandler {
    int[] ports;

    public CamerasHandler(int height, int width, int... ports) {
        this.ports = ports;
        for(int i : ports) {
            UsbCamera usbCamera = new UsbCamera("camera"+i,i);
            usbCamera.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);
        }
    }
}