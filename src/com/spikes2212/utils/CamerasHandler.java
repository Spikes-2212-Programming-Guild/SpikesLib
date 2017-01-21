package com.spikes2212.utils;

import java.util.function.Supplier;

import org.opencv.core.Mat;
import org.opencv.ml.SVM;

import com.spikes2212.dashboard.ConstantHandler;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CamerasHandler {
	private UsbCamera[] cameras;
	private CvSink[] cvSinks;
	private Supplier<Integer> width = ConstantHandler.addConstantInt("width", 420);
	private Supplier<Integer> height = ConstantHandler.addConstantInt("height", 340);
	private int port = 0;

	public CamerasHandler(int[] ports) {
		new Thread(() -> {
			for (int i = 0; i < ports.length; i++) {
				cameras[i] = CameraServer.getInstance().startAutomaticCapture(ports[i]);
				cvSinks[i] = CameraServer.getInstance().getVideo(cameras[i]);
			}
			CvSource outputStream = CameraServer.getInstance().putVideo("CamerasHandler", width.get(), height.get());
			Mat frame = new Mat();
			while (!Thread.interrupted()) {
				cvSinks[port].grabFrame(frame);
				outputStream.putFrame(frame);
			}
		}).start();
	}

	public void switchCamera(int port) {
		this.port = port;
	}

}
