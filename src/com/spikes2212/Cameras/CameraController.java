package com.spikes2212.Cameras;

import java.util.Optional;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.vision.USBCamera;

public class CameraController {
	public static int resolutionWidth = 100, resolutionHeight = 100;
	private Optional<USBCamera> camera;
	private boolean on;

	public CameraController(String port, int fps) {
		try {
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
		if (camera.isPresent()) {
			try {
				camera.get().startCapture();
				on = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		try {
			camera.ifPresent(USBCamera::stopCapture);
		} catch (Exception e) {
			e.printStackTrace();
		}
		on = false;
	}

	public void getImage(Image image) {
		try {
			camera.ifPresent(c -> c.getImage(image));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasCamera() {
		return camera.isPresent();
	}

	public boolean isOn() {
		return on;
	}
}
