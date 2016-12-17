package com.spikes2212.cameras;

import java.util.Optional;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.vision.USBCamera;

public class CameraController {
    public final static int resolutionWidth = 320, resolutionHeight = 240;
    private Optional<USBCamera> camera;
    private boolean on;
    private String port;

    public CameraController(String port, int fps) {
        try {
            this.port = port;
            USBCamera camera = new USBCamera(port);
            camera.setFPS(fps);
            camera.setSize(resolutionWidth, resolutionHeight);
            camera.setExposureAuto();
            camera.updateSettings();
            this.camera = Optional.of(camera);
        } catch (Exception e) {
            e.printStackTrace();
            this.camera = Optional.empty();
        }
    }

    public void start() {
        camera.ifPresent(USBCamera::startCapture);
        on = true;
    }

    public void stop() {
        camera.ifPresent(USBCamera::stopCapture);
        on = false;
    }

    public void getImage(Image image) {
        camera.ifPresent(c -> c.getImage(image));
    }

    public boolean hasCamera() {
        return camera.isPresent();
    }

    public boolean isOn() {
        return on;
    }

    public String getPort() {
        return this.port;
    }
}
