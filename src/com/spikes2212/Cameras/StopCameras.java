package com.spikes2212.Cameras;

import com.spikes2212.utils.RunnableCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopCameras extends RunnableCommand {

	public StopCameras(CamerasHandler handler) {
		super(() -> handler.stop());
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

}
