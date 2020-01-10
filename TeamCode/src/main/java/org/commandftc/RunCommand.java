package org.commandftc;

public class RunCommand extends Command {
    private Runnable toRun;

    public RunCommand(Runnable toRun, Subsystem ... requirements) {
        this.toRun = toRun;
        addRequirements(requirements);
    }

    @Override
    public void execute() {
        toRun.run();
    }
}
