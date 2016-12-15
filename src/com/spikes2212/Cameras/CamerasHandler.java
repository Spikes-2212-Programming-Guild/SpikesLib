package com.spikes2212.Cameras;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;

public class CamerasHandler {
	private ArrayList<CameraController> cameras;
	private CameraController currentCamera = null;
	private Image image;
	private int currentIndex;
	public static int fps = 30;

	public CamerasHandler() {
		cameras = new ArrayList<CameraController>();
		this.image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	}

	public CamerasHandler(int fps) {
		this();
		CamerasHandler.fps = fps;
	}

	public void addCamera(String port, int index) {
		this.currentIndex = index;
		cameras.add(index, new CameraController(port, fps));
	}

	public synchronized void stop() {
		cameras.forEach(c -> c.stop());
		currentCamera = null;
	}

	
	public synchronized void start(String port){
		if(!currentCamera.getPort().equals(port)){
			stop();
			for (CameraController c:cameras){
				if( c.getPort().equals(port)){
					currentCamera=c;
					c.start();
				}
			}
		}
	}

	public void getImage(Image image) {
		currentCamera.getImage(image);
	}

	public synchronized void stream() {
		getImage(image);
		CameraServer.getInstance().setImage(image);
	}

	public boolean isOn() {
		return currentCamera != null;
	}
}
