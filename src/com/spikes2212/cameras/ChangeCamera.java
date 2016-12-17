package com.spikes2212.cameras;

import com.spikes2212.utils.RunnableCommand;


/**
 *
 */
public class ChangeCamera extends RunnableCommand {

	public ChangeCamera(CamerasHandler handler, String port) {

		super(() -> handler.start(port));
		requires(handler);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis); 
	}
}
