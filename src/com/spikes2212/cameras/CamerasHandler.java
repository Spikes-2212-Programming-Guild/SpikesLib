package com.spikes2212.cameras;

import java.util.HashMap;
import java.util.Map;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CamerasHandler extends Subsystem {
    private Map<String, CameraController> cameras;
    private CameraController currentCamera = null;
    private Image image;
    public int fps = 30;

    public CamerasHandler() {
        cameras = new HashMap<>();
        this.image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        new Thread(new VisionRunnable(this)).start();

    }

    public CamerasHandler(String... ports) {
        this();
        for (String p : ports) {
            addCamera(p);
        }
    }

    public CamerasHandler(int fps) {
        this();
        this.fps = fps;
    }

    public void addCamera(String port) {
        cameras.put(port, new CameraController(port, fps));
    }

    public synchronized void stop() {
        cameras.values().forEach(CameraController::stop);
        currentCamera = null;
    }


    public synchronized void start(String port) {
        try {
            if (currentCamera != cameras.get(port)) {
                stop();
                cameras.get(port).start();
                currentCamera = cameras.get(port);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void stream() {
    	if (currentCamera != null){
    		currentCamera.getImage(image);
        CameraServer.getInstance().setImage(image);
    	}
    }

    public boolean isOn() {
        return currentCamera != null;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub

    }
}
