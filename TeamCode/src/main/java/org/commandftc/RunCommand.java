package org.commandftc;

/**
 * A {@code RunCommand} is a command that supports lambda expressions
 * to create "quick commands". For example, 
 * {@code new RunCommand(() -> {my code...})}
 * represents executing "my code..."
 */
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
