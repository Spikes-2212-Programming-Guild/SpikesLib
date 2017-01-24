package com.spikes2212.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.opencv.core.Mat;
import org.opencv.ml.SVM;

import com.spikes2212.dashboard.ConstantHandler;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CamerasHandler {
	private int port;
	private Map cvSinks = new HashMap<Integer, CvSink>();

	public CamerasHandler(int width, int height, int... ports) {
		port = ports[0];
		new Thread(() -> {
			for (int i : ports) {
				addCamera(i);
			}
			CvSource outputStream = CameraServer.getInstance().putVideo("CamerasHandler", width, height);
			Mat frame = new Mat();
			while (!Thread.interrupted()) {
				((CvSink) cvSinks.get(port)).grabFrame(frame); 
				outputStream.putFrame(frame);
			}
		}).start();
	}

	public void switchCamera(int port) {
		if (cvSinks.containsKey(port))
			this.port = port;
	}

	public void addCamera(int port) {
		if (!cvSinks.containsKey(port)) {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(port);
			cvSinks.put(port, CameraServer.getInstance().getVideo(camera));
		}
	}

}
