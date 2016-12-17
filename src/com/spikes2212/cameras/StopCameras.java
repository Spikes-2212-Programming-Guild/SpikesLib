package com.spikes2212.cameras;

import com.spikes2212.utils.RunnableCommand;

/**
 *
 */
public class StopCameras extends RunnableCommand {

    public StopCameras(CamerasHandler handler) {
        super(() -> handler.stop());
        requires(handler);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

}
