package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.commandftc.Command;

public class WaitCommand extends Command {
    private ElapsedTime timer;
    private double targetSeconds;

    public WaitCommand(double targetSeconds) {
        timer = new ElapsedTime();
        this.targetSeconds = targetSeconds;
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        return timer.seconds() >= targetSeconds;
    }
}
