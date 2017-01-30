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

/**
 * This class handles the cameras used by the drivers,
 * streaming the image from the chosen camera to the camera server under the name "CamerasHandler"
 * and allowing you to switch between any amount of cameras.
 *
 * @author Omri "Riki" Cohen & Ittai "Dafner" Dafner
 */
public class CamerasHandler {
    private int port;
    private Map<Integer, CvSink> cvSinks = new HashMap<>();

    /**
     * Constructs a new instance of {@link CamerasHandler} that automatically starts streaming to the {@link CameraServer}
     *
     * @param width  The width the stream from the cameras should have, in pixels.
     * @param height The height the stream from the cameras should have, in pixels.
     * @param ports  The ports of the cameras this handler should start with.
     */
    public CamerasHandler(int width, int height, int... ports) {
        port = ports[0];
        new Thread(() -> {
            for (int i : ports) {
                addCamera(i);
            }
            CvSource outputStream = CameraServer.getInstance().putVideo("CamerasHandler", width, height);
            Mat frame = new Mat();
            while (!Thread.interrupted()) {
                cvSinks.get(port).grabFrame(frame);
                outputStream.putFrame(frame);
            }
        }).start();
    }

    /**
     * Switches the stream to the stream from the camera in the given port,
     * if that camera has been added to this handler.
     * If no camera was added with that port, does nothing.
     *
     * @param port The port of the camera to switch the stream to
     */
    public void switchCamera(int port) {
        if (cvSinks.containsKey(port))
            this.port = port;
    }

    /**
     * Adds a camera to this handler using the camera's port.
     * If this handler already has a camera with that port, does nothing.
     *
     * @param port The port of the new camera to add.
     */
    public void addCamera(int port) {
        if (!cvSinks.containsKey(port)) {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(port);
            cvSinks.put(port, CameraServer.getInstance().getVideo(camera));
        }
    }

}
