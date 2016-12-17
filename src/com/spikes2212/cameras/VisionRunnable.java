package com.spikes2212.cameras;

import edu.wpi.first.wpilibj.Timer;

public class VisionRunnable implements Runnable{
	
	CamerasHandler camerasHandler;
	public VisionRunnable(CamerasHandler camerasHandler){
		this.camerasHandler=camerasHandler;
	}

	@Override
	public void run() {
		while (true){
			camerasHandler.stream();
			Timer.delay(0.005);
		}
		
	}

}
