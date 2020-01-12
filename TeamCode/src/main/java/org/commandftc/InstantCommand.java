package org.commandftc;

public class InstantCommand extends Command {
    private Runnable toRun;

    public InstantCommand(Runnable toRun, Subsystem ... subsystems) {
        this.toRun = toRun;
        addRequirements(subsystems);
    }

    @Override
    public void init() {
        toRun.run();
    }

    @Override
    public void execute() {}

    @Override
    public void end() {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
