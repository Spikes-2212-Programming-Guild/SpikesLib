package com.spikes2212.genericsubsystems.drivetrains.commands;

import com.spikes2212.dashboard.ConstantHandler;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;
/**
 * @author Simon "C" Kharmatsky
 */
public class GyroDrive extends Command {

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
