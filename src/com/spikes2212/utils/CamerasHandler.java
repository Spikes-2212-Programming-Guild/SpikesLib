//package com.spikes2212.utils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import edu.wpi.cscore.VideoSource;
//import edu.wpi.first.*;
//import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSource;
//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.cameraserver.CameraServer;
//
///**
// * This class handles the cameras used by the drivers, streaming the image from
// * the chosen camera to the camera server under the name "CamerasHandler" and
// * allowing you to switch between any amount of cameras.
// *
// * @author Omri "Riki" Cohen & Ittai "Dafner" Dafner
// */
//public class CamerasHandler {
//    private CvSink sink;
//    private Map<Integer, UsbCamera> cameras = new HashMap<>();
//
//    /**
//     * Constructs a new instance of {@link CamerasHandler} that automatically
//     * starts streaming to the <a href=
//     * "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/CameraServer.html">CameraServer</a>.
//     *
//     * @param width
//     *            the width that the stream from the cameras has, in pixels
//     * @param height
//     *            the height that the stream from the cameras has, in pixels
//     * @param ports
//     *            the ports of the cameras this handler starts with
//     *
//     * @throws IndexOutOfBoundsException
//     *             when no camera ports are given
//     */
//    public CamerasHandler(int width, int height, int... ports) {
//        if (ports.length < 1) {
//            throw new IllegalArgumentException("Cannot construct CamerasHandler without cameras");
//        }
//        for (int i : ports) {
//            addCamera(i, width, height);
//        }
//        switchCamera(ports[0]);
//        new Thread(() -> {
//            CvSource outputStream = CameraServer.getInstance().putVideo("CamerasHandler", width, height);
//            Mat frame = new Mat();
//            while (!Thread.interrupted()) {
//                if (sink.grabFrame(frame) == 0) {
//                    // Send the output the error.
//                    outputStream.notifyError(sink.getError());
//                    // skip the rest of the current iteration
//                    continue;
//                }
//                outputStream.putFrame(frame);
//            }
//        }).start();
//    }
//
//    /**
//     * Switches the stream to the camera with the given port. If there's no
//     * camera with that port in the {@link CamerasHandler}, does nothing.
//     *
//     * @param port
//     *            the port of the camera to switch to
//     */
//    public void switchCamera(int port) {
//        if (cameras.containsKey(port)) {
//            this.sink = CameraServer.getInstance().getVideo(cameras.get(port));
//            for (int k : cameras.keySet()) {
//                if (k != port)
//                    cameras.get(k).setConnectionStrategy(VideoSource.ConnectionStrategy.kForceClose);
//            }
//        }
//    }
//
//    /**
//     * Sets the exposure of the camera in the given port. If there's no camera
//     * with that port in the {@link CamerasHandler}, does nothing.
//     *
//     * @param port
//     *            the port of the wanted camera
//     * @param exposure
//     *            the exposure this camera should have, in percentage
//     *
//     * @see <a href=
//     *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/cscore/VideoCamera.html#setExposureManual-int-">UsbCamera#setExposureManual(int)</a>
//     */
//    public void setExposure(int exposure, int port) {
//        if (cameras.containsKey(port)) {
//            cameras.get(port).setExposureManual(exposure);
//        }
//    }
//
//    /**
//     * Sets the exposure of all of the cameras this {@link CamerasHandler}.
//     *
//     * <br>
//     * <br>
//     * Only affect cameras this handler currently handles. <br>
//     * If a camera is added after running {@link #setExposure(int)}, it will
//     * have the default automatic exposure, meaning you will have to run
//     * {@link #setExposure(int, int)} again if you want it to have
//     * none-automatic exposure.
//     *
//     * @param exposure
//     *            the exposure the cameras should have, in percentage
//     *
//     * @see <a href=
//     *      "http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/cscore/VideoCamera.html#setExposureManual-int-">UsbCamera#setExposureManual(int)</a>
//     */
//    public void setExposure(int exposure) {
//        for (UsbCamera c : cameras.values()) {
//            c.setExposureManual(exposure);
//        }
//    }
//
//    /**
//     * Adds a camera to this handler using the camera's port. If this handler
//     * already has a camera with that port, does nothing.
//     *
//     * @param port
//     *            the port of the new camera
//     */
//    public void addCamera(int port) {
//        if (!cameras.containsKey(port)) {
//            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(port);
//            cameras.put(port, camera);
//        }
//    }
//
//    /**
//     * Adds a camera to this handler using the camera's port. If this handler
//     * already has a camera with that port, does nothing.
//     *
//     * @param port
//     *            the port of the new camera
//     * @param width
//     *            the width that the stream from this cameras has, in pixels
//     * @param height
//     *            the height that the stream from this cameras has, in pixels
//     */
//    public void addCamera(int port, int width, int height) {
//        if (!cameras.containsKey(port)) {
//            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(port);
//            camera.setResolution(width, height);
//            cameras.put(port, camera);
//        }
//    }
//
//}