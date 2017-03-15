package com.spikes2212.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.opencv.core.Mat;

import com.spikes2212.dashboard.ConstantHandler;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * This class handles the cameras used by the drivers, streaming the image from
 * the chosen camera to the camera server under the name "CamerasHandler" and
 * allowing you to switch between any amount of cameras.
 *
 * @author Omri "Riki" Cohen & Ittai "Dafner" Dafner
 */
public class CamerasHandler {
    private CvSink sink;
    private Map<Integer, UsbCamera> cameras = new HashMap<>();

    /**
     * Constructs a new instance of {@link CamerasHandler} that automatically
     * starts streaming to the {@link CameraServer}
     *
     * @param width  The width the stream from the cameras should have, in pixels.
     * @param height The height the stream from the cameras should have, in pixels.
     * @param ports  The ports of the cameras this handler should start with.
     */
    public CamerasHandler(int width, int height, int... ports) {
        if (ports.length < 1) {
            throw new IllegalArgumentException("Cannot construct CamerasHandler without cameras");
        }
        for (int i : ports) {
            addCamera(i, width, height);
        }
        switchCamera(ports[0]);
        new Thread(() -> {
            CvSource outputStream = CameraServer.getInstance().putVideo("CamerasHandler", width, height);
            Mat frame = new Mat();
            while (!Thread.interrupted()) {
                if (sink.grabFrame(frame) == 0) {
                    // Send the output the error.
                    outputStream.notifyError(sink.getError());
                    // skip the rest of the current iteration
                    continue;
                }
                outputStream.putFrame(frame);
            }
        }).start();
    }

    /**
     * Switches the stream to the stream from the camera in the given port, if
     * that camera has been added to this handler. If no camera was added with
     * that port, does nothing.
     *
     * @param port The port of the camera to switch the stream to
     */
    public void switchCamera(int port) {
        if (cameras.containsKey(port)) {
            this.sink = CameraServer.getInstance().getVideo(cameras.get(port));
        }
    }

    /**
     * Sets the exposure of the camera in the given port, if that camera has
     * been added to this handler. If no camera was added with that port, does
     * nothing.
     *
     * @param port     The port of the camera to change the exposure for.
     * @param exposure The exposure the camera should have, in percentage.
     * @see UsbCamera#setExposureManual(int)
     */

    public void setExposure(int exposure, int port) {
        if (cameras.containsKey(port)) {
            cameras.get(port).setExposureManual(exposure);
        }
    }

    /**
     * Sets the exposure of all of the cameras this handler handles.
     * <p>
     * Only affect cameras this handler currently handles. <br/>
     * If a camera is added after running {@link #setExposure(int)}, it will have the default automatic exposure,
     * meaning you will have to run {@link #setExposure(int, int)} again if you want it to have a none-automatic exposure.
     * </p>
     *
     * @param exposure The exposure the cameras should have, in percentage.
     * @see UsbCamera#setExposureManual(int)
     */
    public void setExposure(int exposure) {
        for (UsbCamera c : cameras.values()) {
            c.setExposureManual(exposure);
        }
    }

    /**
     * Adds a camera to this handler using the camera's port. If this handler
     * already has a camera with that port, does nothing.
     *
     * @param port The port of the new camera to add.
     */
    public void addCamera(int port) {
        if (!cameras.containsKey(port)) {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(port);
            cameras.put(port, camera);
        }
    }

    /**
     * Adds a camera to this handler using the camera's port. If this handler
     * already has a camera with that port, does nothing.
     *
     * @param port The port of the new camera to add.
     */
    public void addCamera(int port, int width, int height) {
        if (!cameras.containsKey(port)) {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(port);
            camera.setResolution(width, height);
            cameras.put(port, camera);
        }
    }

}