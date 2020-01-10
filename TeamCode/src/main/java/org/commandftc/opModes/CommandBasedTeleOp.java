package org.commandftc.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.commandftc.CommandScheduler;
import org.commandftc.Subsystem;

import java.util.HashSet;
import java.util.Set;

public abstract class CommandBasedTeleOp extends OpMode {

    private Set<Subsystem> subsystems = new HashSet<>();

    protected final void addSubsystems(Subsystem ... sss) {
        for(Subsystem ss : sss) {
            subsystems.add(ss);
        }
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
        assign();
        registerSubsystems();
    }

    public abstract void assign();

    @Override
    public final void loop() {
        CommandScheduler.runOnce();
    }

    @Override
    public final void stop() {
        CommandScheduler.unscheduleAll();
    }
}
