package org.commandftc.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.commandftc.Command;
import org.commandftc.CommandScheduler;
import org.commandftc.Subsystem;

import java.util.HashSet;
import java.util.Set;

public abstract class CommandBasedAuto extends OpMode {

    private Set<Subsystem> subsystems = new HashSet<>();
    private Command autoCmd;

    protected final void addSubsystems(Subsystem ... sss) {
        for(Subsystem ss : sss) {
            subsystems.add(ss);
        }
    }

    protected final void setAutoCmd(Command command) {
        this.autoCmd = command;
    }

    private void registerSubsystems() {
        for(Subsystem ss : subsystems) {
            CommandScheduler.registerSubsystem(ss);
        }
    }

    /**
     * DON'T OVERRIDE THIS! IT CALLS init_impl() (WHICH YOU SHOULD INSTEAD OVERRIDE)
     * AND DOES SOMETHING ELSE!!!!!
     */
    @Override
    public final void init() {
        plan();
        registerSubsystems();
        CommandScheduler.scheduleCommand(autoCmd);
    }

    public abstract void plan();

    @Override
    public final void loop() {
        CommandScheduler.runOnce();
    }

    @Override
    public final void stop() {
        CommandScheduler.unscheduleAll();
        CommandScheduler.unregisterAllButtons();
        CommandScheduler.unregisterAllSubsystems();
    }
}
