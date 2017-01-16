package com.spikes2212.utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * a Command running something on a thread as long as the thread lives
 * useful for treating java code as command, combining it with other normal commands, and adding code to be operated by normal triggers(e.g. buttons, smartdashboard's sendable etc)
 */
public class RunnableCommand extends Command {

    private Runnable runnable;
    private Thread t;

    /**
     *
     * @param runnable a runnable to be used in the thread
     */
    public RunnableCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        t = new Thread(runnable);
        t.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !t.isAlive();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
