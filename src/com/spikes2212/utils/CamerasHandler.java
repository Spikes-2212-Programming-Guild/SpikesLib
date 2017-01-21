package com.spikes2212.utils;

import java.util.ArrayList;
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
	private ArrayList<UsbCamera> cameras = new ArrayList<>();
	private ArrayList<CvSink> cvSinks = new ArrayList<>();
	private ArrayList<Integer> ports = new ArrayList<>();

	public CamerasHandler(int[] ports, int width, int height) {
		port = ports[0];
		new Thread(() -> {
			for (int i = 0; i < ports.length; i++) {
				addCamera(ports[i]);
			}
			CvSource outputStream = CameraServer.getInstance().putVideo("CamerasHandler", width, height);
			Mat frame = new Mat();
			while (!Thread.interrupted()) {
				cvSinks.get(port).grabFrame(frame);
				outputStream.putFrame(frame);
			}
		}).start();
	}

	public void switchCamera(int port) {
		if(isThePortInUse(port))
			this.port = port;
	}

	public void addCamera(int port) {
		if (!isThePortInUse(port)) {
			ports.add(port);
			cameras.add(CameraServer.getInstance().startAutomaticCapture(port));
			cvSinks.add(CameraServer.getInstance().getVideo(cameras.get(port)));
		}
	}

	public boolean isThePortInUse(int port) {
		for (int i = 0; i < ports.size(); i++) {
			if (port == ports.get(i))
				return true;
		}
		return false;
	}

}
